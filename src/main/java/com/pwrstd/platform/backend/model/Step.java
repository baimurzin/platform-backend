package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "steps")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Step {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @Column
    private String content;

    @OneToMany(mappedBy = "step")
    private Set<UserCourseStep> userCourseSteps;

    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;

    @OneToOne
    private Step next;

    @OneToOne
    private Step prev;

    public Step switchToNext() {
        Step next = this.next;
        this.prev = next;
        this.next = next.getNext();
        return next;
    }

    public Step switchToPrev() {
        Step prev = this.prev;
        this.next = prev;
        this.prev = prev.getPrev();
        return prev;
    }
}
