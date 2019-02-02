package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.repository.CourseCategoryRepository;
import com.pwrstd.platform.backend.repository.CourseRepository;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.service.CourseService;
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

    public CourseController(UserRepository userRepository, CourseCategoryRepository courseCategoryRepository, CourseRepository courseRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String coursePage(Model model) {
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
        return "course";
    }
}
