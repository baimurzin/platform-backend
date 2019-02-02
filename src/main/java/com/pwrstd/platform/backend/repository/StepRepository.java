package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {

    Step findStepByCourseAndUsersOnThisStep(Course course, User user);
}
