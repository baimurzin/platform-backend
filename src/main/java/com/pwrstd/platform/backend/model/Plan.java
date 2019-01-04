package com.pwrstd.platform.backend.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "plans")
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

    @OneToMany(
            mappedBy = "plan",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Subscription> subscriptions;

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

    public BigDecimal getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(BigDecimal monthPrice) {
        this.monthPrice = monthPrice;
    }

    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
