package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Plan;
import com.pwrstd.platform.backend.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository extends JpaRepository<Script, Long> {
}
