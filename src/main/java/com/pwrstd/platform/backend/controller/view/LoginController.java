package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.controller.errors.InternalServerErrorException;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.security.SecurityUtils;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String login(
            @RequestParam(value = "error", required = false) String error,
            ModelMap map) {
        if(error != null) {
            map.addAttribute("error", "Invalid username or password!");
        }
        boolean userLoggedIn = userService.getCurrentUser().isPresent();
        if(userLoggedIn)
            return "redirect:/";
        else
            return "loginView";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
