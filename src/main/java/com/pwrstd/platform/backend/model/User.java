package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private boolean confirmed = false;

    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    //todo https://github.com/JavaMoney/jsr354-api
    //https://stackoverflow.com/questions/1359817/using-bigdecimal-to-work-with-currencies
    private BigDecimal balance;

    private String currency;

    private String lang;

    private String timezone;

    @OneToOne
    private Plan plan;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<UserCourse> userCourses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PaymentTransaction> paymentTransactions;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<UserCourseStep> userCourseSteps;
}

