package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
