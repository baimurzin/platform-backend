package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.UserConnection;
import com.pwrstd.platform.backend.model.key.UserConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserSocialConnectionRepository extends JpaRepository<UserConnection, UserConnectionId> {

    Set<UserConnection> findByUserid(String userId);

    Set<UserConnection> findByUserConnectionIdAndUserid(UserConnectionId userConnectionId, String userId);
}
