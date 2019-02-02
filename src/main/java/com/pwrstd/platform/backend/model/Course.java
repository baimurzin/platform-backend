package com.pwrstd.platform.backend.model;



import com.pwrstd.platform.backend.model.enums.CourseType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
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

    public List<CourseCategory> getCourseCategories() {
        return courseCategories;
    }

    public void setCourseCategories(List<CourseCategory> courseCategories) {
        this.courseCategories = courseCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(List<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }
}
