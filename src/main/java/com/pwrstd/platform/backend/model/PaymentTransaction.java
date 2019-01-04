package com.pwrstd.platform.backend.model;



import com.pwrstd.platform.backend.model.enums.PaymentTransactionType;
import com.pwrstd.platform.backend.model.enums.PaymentVendorType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentTransactionType paymentTransactionType;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentVendorType paymentVendorType;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public void setPaymentTransactionType(PaymentTransactionType paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentVendorType getPaymentVendorType() {
        return paymentVendorType;
    }

    public void setPaymentVendorType(PaymentVendorType paymentVendorType) {
        this.paymentVendorType = paymentVendorType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
