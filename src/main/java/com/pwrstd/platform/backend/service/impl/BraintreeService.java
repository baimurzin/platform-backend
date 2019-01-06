package com.pwrstd.platform.backend.service.impl;

import com.braintreegateway.*;
import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.model.PaymentTransaction;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.enums.PaymentTransactionType;
import com.pwrstd.platform.backend.model.enums.PaymentVendorType;
import com.pwrstd.platform.backend.repository.PaymentRepository;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.security.SecurityUtils;
import com.pwrstd.platform.backend.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class BraintreeService implements PaymentService {

    private final static Logger log = LoggerFactory.getLogger(BraintreeService.class);

    private final PaymentRepository paymentRepository;

    private final BraintreeGateway braintreeGateway;

    private final UserRepository userRepository;

    public BraintreeService(PaymentRepository paymentRepository, BraintreeGateway braintreeGateway, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.braintreeGateway = braintreeGateway;
        this.userRepository = userRepository;
    }

    @Override
    public void checkout(String amount, String nonce) {
        User currentUser = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .orElseThrow(() -> new BadRequestAlertException("User not found", "user", "user_payment_error"));

        BigDecimal decimalAmount;
        try {
            decimalAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("Amount is an invalid format.", "payments", "amountinvalid");
        }

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = braintreeGateway.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            CreditCard creditCard = transaction.getCreditCard();
            String transactionId = transaction.getId();
            Calendar transactionCreatedAt = transaction.getCreatedAt();

            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setAmount(transaction.getAmount());
            paymentTransaction.setDate(transactionCreatedAt.getTime());
            paymentTransaction.setPaymentTransactionType(PaymentTransactionType.BRAINTREE);
            paymentTransaction.setPaymentVendorType(PaymentVendorType.BRAINTREE);
            paymentTransaction.setUser(currentUser);
            paymentTransaction.setTransactionID(transactionId);
            paymentRepository.save(paymentTransaction);
            currentUser.setBalance(currentUser.getBalance().add(transaction.getAmount()));
            //todo save other fields
            userRepository.save(currentUser);
        } else {
            //todo
            log.error("Payment was unsuccessful!");
        }

    }

    public BraintreeGateway getBraintreeGateway() {
        return braintreeGateway;
    }
}
