package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
