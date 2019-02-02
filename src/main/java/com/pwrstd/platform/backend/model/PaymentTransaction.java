package com.pwrstd.platform.backend.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwrstd.platform.backend.model.enums.PaymentTransactionType;
import com.pwrstd.platform.backend.model.enums.PaymentVendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payment_transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Column(name = "transaction_id")
    private String transactionID;

    @Enumerated(EnumType.STRING)
    private PaymentTransactionType paymentTransactionType;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentVendorType paymentVendorType;

    private Date date;

}
