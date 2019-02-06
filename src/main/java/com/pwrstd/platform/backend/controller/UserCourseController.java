package com.pwrstd.platform.backend.controller;

import com.pwrstd.platform.backend.controller.errors.UserNotFoundException;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.service.GitService;
import com.pwrstd.platform.backend.service.UserService;
import org.kohsuke.github.GHRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserCourseController {


    private final UserService userService;

    public UserCourseController(UserService userService, GitService service) {
        this.userService = userService;
        this.service = service;
    }

    @PostMapping("/user/course")
    public ResponseEntity takeCourse() {
        User user = userService.getCurrentUser()
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok().build();
    }



    private final GitService service;
    @GetMapping("dick")
    public String test() throws IOException {
        List<GHRepository> allRepositories = service.getAllRepositories();
        service.addNewRepository("test");
        return "";
    }
}
