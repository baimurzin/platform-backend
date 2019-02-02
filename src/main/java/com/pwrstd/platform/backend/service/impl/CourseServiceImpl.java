package com.pwrstd.platform.backend.service.impl;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.UserCourseStep;
import com.pwrstd.platform.backend.repository.StepRepository;
import com.pwrstd.platform.backend.repository.UserCourseStepRepository;
import com.pwrstd.platform.backend.service.CourseService;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final UserCourseStepRepository userCourseStepRepository;
    private final StepRepository stepRepository;

    @Autowired
    public CourseServiceImpl(UserService userService, UserCourseStepRepository userCourseStepRepository, StepRepository stepRepository) {
        this.userService = userService;
        this.userCourseStepRepository = userCourseStepRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    public Step getCurrentStepForCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));
        UserCourseStep currentStep = userCourseStepRepository.findByUserAndCourse(user, course);
        if (currentStep == null) {
            return null;
        }
        return currentStep.getStep();
    }

    @Override
    public Step goNextStepForCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));
        UserCourseStep currentStep = userCourseStepRepository.findByUserAndCourse(user, course);
        if (currentStep == null) {
            return initializeCourse(course);
        }

        Step next = currentStep.getStep().switchToNext();
        currentStep.setStep(next);
        userCourseStepRepository.save(currentStep);
        return next;
    }

    @Override
    public Step initializeCourse(Course course) {
        User user = userService.getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not logged in"));

        Step firstStep = stepRepository.findFirstStep(course);
        UserCourseStep ucs = UserCourseStep.builder()
                .course(course)
                .user(user)
                .step(firstStep)
                .build();
        userCourseStepRepository.save(ucs);

        return firstStep;
    }

}
