package com.pwrstd.platform.backend.controller;

import com.pwrstd.platform.backend.controller.errors.UserNotFoundException;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserCourseController {


    private final UserService userService;

    public UserCourseController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/course")
    public ResponseEntity takeCourse() {
        User user = userService.getCurrentUser()
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok().build();
    }
}
