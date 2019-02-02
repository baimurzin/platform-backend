package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "plans")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "month_price")
    private BigDecimal monthPrice;

    @Column(name = "year_price")
    private BigDecimal yearPrice;

    @Column(name = "course_count")
    private Integer courseCount;

    @OneToOne
    @JsonIgnore
    private User user;

    private Date startPlan;

    private Date endPlan;
}
