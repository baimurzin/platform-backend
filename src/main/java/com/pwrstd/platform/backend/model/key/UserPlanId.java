package com.pwrstd.platform.backend.model.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPlanId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plan_id")
    private Long planId;

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
