package com.pwrstd.platform.backend.model.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPlanId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plan_id")
    private Long planId;


    private UserPlanId() {
    }

    public UserPlanId(Long userId, Long planId) {
        this.userId = userId;
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlanId that = (UserPlanId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(planId, that.planId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, planId);
    }
}
