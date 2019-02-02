package com.pwrstd.platform.backend.security;

import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(final String email) throws UsernameNotFoundException, UserNotActivatedException {
        log.debug("Authenticating {}", email);
        if (new EmailValidator().isValid(email, null)) {
            return userRepository.findOneByEmailIgnoreCase(email)
                    .map(MyUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found in the database"));
        }
        throw new RuntimeException("Email invalid: " + email);
    }




    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
//        if (!user.isConfirmed()) {
//            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//        }
//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//                .collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}
