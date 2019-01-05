package com.pwrstd.platform.backend.controller;

import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.controller.errors.EmailAlreadyUsedException;
import com.pwrstd.platform.backend.controller.errors.InternalServerErrorException;
import com.pwrstd.platform.backend.controller.util.HeaderUtil;
import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.service.MailService;
import com.pwrstd.platform.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final static Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    public AccountController(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    public User getAccount() {
        return userService.getCurrentUser()
                .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        }

        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = userService.removeNonConfirmedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = userService.registerUser(userDTO);
        mailService.sendActivationEmail(newUser);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.getEmail()))
                .body(newUser);

    }

}
