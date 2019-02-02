package com.pwrstd.platform.backend.service.impl;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.repository.StepRepository;
import com.pwrstd.platform.backend.service.CourseService;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final StepRepository stepRepository;

    public CourseServiceImpl(UserService userService, StepRepository stepRepository) {
        this.userService = userService;
        this.stepRepository = stepRepository;
    }

    @Override
    public Step getCurrentStepForCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));
        Step currentStep = stepRepository.findStepByCourseAndUsersOnThisStep(course, user);
        return currentStep;
    }

    @Override
    public Step goNextStepForCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));
        Step currentStep = stepRepository.findStepByCourseAndUsersOnThisStep(course, user);
        if (currentStep == null) {
            return initializeCourse(course);
        }
        Long nextStep = currentStep.getCurrentStep() + 1;
        currentStep.setCurrentStep(nextStep);
        stepRepository.save(currentStep);
        return currentStep;
    }

    @Override
    public Step initializeCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));
        Step currentStep = stepRepository.findStepByCourseAndUsersOnThisStep(course, user);
        if (currentStep != null) {
            throw new RuntimeException("Course already initialized");
        }
        Step step = new Step();
        step.setCurrentStep(1L);
        step.setCourse(course);
        step.addUserToThisStep(user);
        stepRepository.save(step);
        return null;
    }

}
