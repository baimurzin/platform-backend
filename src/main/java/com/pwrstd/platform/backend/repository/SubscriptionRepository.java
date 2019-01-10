package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
