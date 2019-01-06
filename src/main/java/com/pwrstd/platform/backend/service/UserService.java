package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.Role;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.security.SecurityUtils;
import com.pwrstd.platform.backend.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setConfirmed(true);
                    user.setActivationKey(null);
                    user.setRole(Role.USER);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setLang(userDTO.getLangKey());
        user.setBalance(BigDecimal.valueOf(0));
        user.setConfirmed(false);
        user.setCreatedDate(Instant.now());
        user.setActivationKey(RandomUtil.generateActivationKey());
        //todo currency
        user.setCurrency("RUR");
        user.setRole(Role.UNCONFIRMED);
        user.setTimezone(userDTO.getTimezone());
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotConfirmedUsers() {
        userRepository
                .findAllByConfirmedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getEmail());
                    userRepository.delete(user);
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String login) {
        return userRepository.findOneByEmailIgnoreCase(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return Optional.of(userRepository.getOne(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByEmailIgnoreCase);
    }


    public boolean removeNonConfirmedUser(User existingUser){
        if(existingUser.isConfirmed()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}
