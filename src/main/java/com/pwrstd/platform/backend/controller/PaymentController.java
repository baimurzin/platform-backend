package com.pwrstd.platform.backend.controller;


import com.pwrstd.platform.backend.security.SecurityUtils;
import com.pwrstd.platform.backend.service.impl.BraintreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {


    private final BraintreeService braintreeService;

    public PaymentController(BraintreeService braintreeService) {
        this.braintreeService = braintreeService;
    }

    @RequestMapping("/braintree/token")
    public ResponseEntity<String> clientToken() {
        return ResponseEntity.ok(braintreeService.getBraintreeGateway().clientToken().generate());
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

        braintreeService.checkout(amount, nonce);
        return ResponseEntity.ok("");
    }
}
