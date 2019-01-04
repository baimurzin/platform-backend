package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    public User createUser(UserDTO userDTO) {
        return null;
    }
}
