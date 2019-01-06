package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {
}
