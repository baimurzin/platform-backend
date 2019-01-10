package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.model.Plan;

public interface PlanService {

    void buyPlan(Long planId, Integer duration);

}
