package com.pwrstd.platform.backend.service.impl;

import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.model.PaymentTransaction;
import com.pwrstd.platform.backend.model.Plan;
import com.pwrstd.platform.backend.model.Subscription;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.enums.PaymentTransactionType;
import com.pwrstd.platform.backend.model.key.UserPlanId;
import com.pwrstd.platform.backend.repository.PaymentRepository;
import com.pwrstd.platform.backend.repository.PlanRepository;
import com.pwrstd.platform.backend.repository.SubscriptionRepository;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.service.PlanService;
import com.pwrstd.platform.backend.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Service
public class SubscriptionPlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    private final UserService userService;


    private final SubscriptionRepository subscriptionRepository;

    private final PaymentRepository paymentRepository;

    public SubscriptionPlanServiceImpl(PlanRepository planRepository,
                                       UserService userService,
                                       SubscriptionRepository subscriptionRepository,
                                       PaymentRepository paymentRepository) {
        this.planRepository = planRepository;
        this.userService = userService;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void buyPlan(Long planId, Integer duration) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BadRequestAlertException("Plan not found", "plan", "plannotfound"));

        User user = userService.getCurrentUser()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BigDecimal totalSumToTakeFromUser = (duration == 12) ? plan.getYearPrice()
                : plan.getMonthPrice().multiply(new BigDecimal(duration));

        if (user.getBalance().compareTo(totalSumToTakeFromUser) < 0) {
            throw new BadRequestAlertException("No enough money", "money", "nomoney");
        }

        user.setBalance(user.getBalance().subtract(plan.getMonthPrice()));

        Subscription subscription = new Subscription();
        UserPlanId userPlanId = new UserPlanId(user.getId(), plan.getId());
        subscription.setId(userPlanId);
        subscription.setPlan(plan);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setAmount(totalSumToTakeFromUser);
        paymentTransaction.setDate(Date.from(Instant.now()));
        paymentTransaction.setPaymentTransactionType(PaymentTransactionType.BUY_PLAN);
        paymentTransaction.setPaymentVendorType(null);
        paymentTransaction.setUser(user);
        paymentTransaction.setTransactionID(null);
        paymentRepository.save(paymentTransaction);
    }
}
