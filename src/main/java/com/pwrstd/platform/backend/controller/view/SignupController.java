package com.pwrstd.platform.backend.controller.view;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.UserConnection;
import com.pwrstd.platform.backend.model.key.UserConnectionId;
import com.pwrstd.platform.backend.repository.UserSocialConnectionRepository;
import com.pwrstd.platform.backend.security.MyUserDetails;
import com.pwrstd.platform.backend.service.UserService;
import org.aspectj.lang.annotation.Before;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SignupController {

//    private final ProviderSignInUtils providerSignInUtils;
//    private final UserService userService;
//    private final UserSocialConnectionRepository usersSocialConnectionRepository;
//    private final UsersConnectionRepository usersConnectionRepository;
//
//    public SignupController(ProviderSignInUtils providerSignInUtils, UserService userService,
//                            UserSocialConnectionRepository usersSocialConnectionRepository,
//                            UsersConnectionRepository usersConnectionRepository) {
//        this.providerSignInUtils = providerSignInUtils;
//        this.userService = userService;
//        this.usersSocialConnectionRepository = usersSocialConnectionRepository;
//        this.usersConnectionRepository = usersConnectionRepository;
//    }
//
//    @RequestMapping(value = "/signup", method = RequestMethod.GET)
//    public String redirectRequestToRegistrationPage(WebRequest request, ModelMap modelMap) {
//        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
//        UserProfile userProfile = connection.fetchUserProfile();
//
//        UserDTO userDTO = UserDTO.fromSocialUserProfile(userProfile);
//
//        modelMap.put("user", userDTO);
//
//        return "signup";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/signup", method = RequestMethod.POST)
//    public String registrationUser(@ModelAttribute UserDTO userDTO, WebRequest request) throws Exception {
//
//        String email = userDTO.getEmail();
//        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(request);
//
//        providerSignInUtils.doPostSignUp(email, request);
//        Set<UserConnection> userConnection = usersSocialConnectionRepository.findByUserConnectionIdAndUserid(
//                new UserConnectionId(connectionFromSession.getKey()), email);
//
//        User user = userService.registerUser(userDTO);
//
//        usersSocialConnectionRepository.saveAll(userConnection.stream().peek(c -> c.setUser(user)).collect(Collectors.toList()));
//
//        MyUserDetails myUserDetails = new MyUserDetails(user);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "redirect:/";
//
//    }
}
