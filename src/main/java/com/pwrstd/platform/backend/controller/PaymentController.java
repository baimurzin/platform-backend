package com.pwrstd.platform.backend.controller;


import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class PaymentController {


    private final BraintreeGateway braintreeGateway;

    public PaymentController(BraintreeGateway braintreeGateway) {
        this.braintreeGateway = braintreeGateway;
    }

    @RequestMapping("/braintree/token")
    public ResponseEntity<String> clientToken() {
        return ResponseEntity.ok(braintreeGateway.clientToken().generate());
    }

    @RequestMapping("/test/user")
    public String testUser() {
        return SecurityUtils.getCurrentUserLogin().get();
    }


    @RequestMapping("/test/unconfirmed")
    public String testUnconfirmed() {
        return SecurityUtils.getCurrentUserLogin().get();
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(@RequestParam("amount") String amount,
                                   @RequestParam("payment_method_nonce") String nonce) {
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
            
        }
        return ResponseEntity.ok(result);
    }
}
