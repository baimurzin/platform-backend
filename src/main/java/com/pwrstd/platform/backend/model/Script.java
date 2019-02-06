package com.pwrstd.platform.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String script;

    @Column
    private String methodName;

    @OneToMany(mappedBy = "script")
    private Set<Step> steps;
}
