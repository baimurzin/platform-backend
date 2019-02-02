package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.UserCourseStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseStepRepository extends JpaRepository<UserCourseStep, Long> {

    UserCourseStep findByUserAndCourseAndStep(User user, Course course, Step step);

    UserCourseStep findByUserAndCourse(User user, Course course);

    List<UserCourseStep> findAllByUser(User user);

    List<UserCourseStep> findAllByUserAndCourse(User user, Course course);

    List<UserCourseStep> findAllByUserAndStep(User user, Step step);
}
