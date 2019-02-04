package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.security.MyUserDetails;
import com.pwrstd.platform.backend.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignupController {

    private final static Logger log = LoggerFactory.getLogger(SignupController.class);

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

    @Transactional
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registrationUser(@ModelAttribute UserDTO userDTO, WebRequest request) throws Exception {
        User user = userService.registerUser(userDTO);
        providerSignInUtils.doPostSignUp(user.getEmail(), request);

        MyUserDetails myUserDetails = new MyUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("http://localhost:8080/api/activate?key=" + user.getActivationKey());
        return "redirect:/";

    }
}
