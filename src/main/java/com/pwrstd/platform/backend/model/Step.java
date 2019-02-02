package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue
    private Long id;

    //not generated
    private Long currentStep;

    @ManyToOne
    @JsonIgnore
    private Course course;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_course_steps",
            joinColumns = { @JoinColumn(name = "step_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> usersOnThisStep;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Long currentStep) {
        this.currentStep = currentStep;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsersOnThisStep() {
        return usersOnThisStep;
    }

    public void addUserToThisStep(User user) {
        if (this.usersOnThisStep == null) {
            this.usersOnThisStep = new HashSet<>();
        }
        this.usersOnThisStep.add(user);
    }

    public void setUsersOnThisStep(Set<User> usersOnThisStep) {
        this.usersOnThisStep = usersOnThisStep;
    }
}
