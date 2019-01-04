package com.pwrstd.platform.backend.model;


import com.pwrstd.platform.backend.model.key.UserPlanId;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private UserPlanId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("planId")
    private Plan plan;

    public UserPlanId getId() {
        return id;
    }

    public void setId(UserPlanId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
