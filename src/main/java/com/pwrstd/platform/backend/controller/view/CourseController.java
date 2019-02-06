package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.repository.*;
import com.pwrstd.platform.backend.service.CourseService;
import com.pwrstd.platform.backend.service.groovy.GroovyService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CourseController {

    private final UserRepository userRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final StepRepository stepRepository;
    private final UserCourseStepRepository userCourseStepRepository;
    private final GroovyService groovyService;

    public CourseController(UserRepository userRepository, CourseCategoryRepository courseCategoryRepository, CourseRepository courseRepository, CourseService courseService, StepRepository stepRepository, UserCourseStepRepository userCourseStepRepository, GroovyService groovyService) {
        this.userRepository = userRepository;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.stepRepository = stepRepository;
        this.userCourseStepRepository = userCourseStepRepository;
        this.groovyService = groovyService;
    }

    @GetMapping("/courses")
    public String coursesPage(Model model) {
//        User currentUser = SecurityUtils.getCurrentUserLogin()
//                .flatMap(userRepository::findOneByEmailIgnoreCase)
//                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Course> courseS = courseRepository.findAll();
        model.addAttribute("courses", courseS);
        return "courses";
    }

    @GetMapping("/course/{id}")
    @Transactional
    public String joinCourse(@PathVariable Long id, Model model) {
        Course course = courseRepository.getOne(id);
        Step step = courseService.getCurrentStepForCourse(course);
        if (step == null) {
            step = courseService.initializeCourse(course);
        }
        model.addAttribute("step", step);
//        model.addAttribute("steps", allSteps);
        return "course/java/" + step.getType().name().toLowerCase();
    }

    @GetMapping("/course/{id}/next")
    public String next(@PathVariable Long id, Model model){
        Course course = courseRepository.getOne(id);
        Step step = courseService.getCurrentStepForCourse(course);
        if (step == null) {
            step = courseService.initializeCourse(course);
        }
        Object result = groovyService.execute(step.getScript());
        System.out.println(result);

        return "";
    }
}
