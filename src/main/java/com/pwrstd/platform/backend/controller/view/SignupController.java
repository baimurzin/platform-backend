package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.security.MyUserDetails;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignupController {

    private final ProviderSignInUtils providerSignInUtils;
    private final UserService userService;
    private final UsersConnectionRepository usersConnectionRepository;

    public SignupController(ProviderSignInUtils providerSignInUtils, UserService userService, UsersConnectionRepository usersConnectionRepository) {
        this.providerSignInUtils = providerSignInUtils;
        this.userService = userService;
        this.usersConnectionRepository = usersConnectionRepository;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage(WebRequest request, ModelMap modelMap) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        UserProfile userProfile = connection.fetchUserProfile();

        UserDTO userDTO = UserDTO.fromSocialUserProfile(userProfile);

        modelMap.put("user", userDTO);

        return "signup";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registrationUser(@ModelAttribute UserDTO userDTO, WebRequest request) throws Exception {
        User user = userService.registerUser(userDTO);
        providerSignInUtils.doPostSignUp(user.getEmail(), request);

        MyUserDetails myUserDetails = new MyUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";

    }
}