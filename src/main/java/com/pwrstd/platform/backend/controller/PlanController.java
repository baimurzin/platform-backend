package com.pwrstd.platform.backend.controller;

import com.pwrstd.platform.backend.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping(value = "/plan/{id}/buy")
    public ResponseEntity buyPlan(@PathVariable Long id, @RequestParam Integer monthDuration) {
        planService.buyPlan(id, monthDuration);
        return ResponseEntity.ok().build();
    }
}
