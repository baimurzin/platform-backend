package com.pwrstd.platform.backend.controller;

import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.controller.errors.EmailAlreadyUsedException;
import com.pwrstd.platform.backend.controller.errors.LoginAlreadyUsedException;
import com.pwrstd.platform.backend.controller.util.HeaderUtil;
import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.security.AuthoritiesConstants;
import com.pwrstd.platform.backend.service.MailService;
import com.pwrstd.platform.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final static Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    public RegistrationController(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()) != null) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
                    .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getEmail()))
                    .body(newUser);
        }
    }
    
}
