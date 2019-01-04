package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    User findOneByActivationKey(String activationKey);

    User findAllByConfirmedIsFalseAndCreatedDateBefore(Instant dateTime);

    User findOneByEmailIgnoreCase(String email);

}
