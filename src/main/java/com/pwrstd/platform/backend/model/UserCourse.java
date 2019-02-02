package com.pwrstd.platform.backend.model;


import com.pwrstd.platform.backend.model.enums.CourseStatus;
import com.pwrstd.platform.backend.model.key.UserCourseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_courses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
}
