package com.pwrstd.platform.backend.service;

public interface PaymentService {

    void checkout(String amount, String nonce);
}
