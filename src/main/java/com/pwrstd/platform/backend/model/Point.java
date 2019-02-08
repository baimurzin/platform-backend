package com.pwrstd.platform.backend.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "question")
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @Column(nullable = false)
    private Boolean isRight = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
