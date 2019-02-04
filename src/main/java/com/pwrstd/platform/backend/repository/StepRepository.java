package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {

    List<Step> findAllByCourse(Course course);


    @Query("from Step where prev is null and course = ?1")
    Step findFirstStep(Course course);
}
