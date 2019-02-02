package com.pwrstd.platform.backend.model;



import com.pwrstd.platform.backend.model.enums.CourseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    private Long id;

    private String name;

    private String description;

    @Column(name = "course_type")
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    private BigDecimal price; //todo refactoring in separated table

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_categories_link",
            joinColumns = { @JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_category_id")}
    )
    private List<CourseCategory> courseCategories;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserCourse> userCourses = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private Set<UserCourseStep> userCourseSteps;
}
