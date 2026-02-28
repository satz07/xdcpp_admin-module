package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.PaymentModeType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "payment_code_master")
@Audited
public class PaymentCodeMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_code")
    @NotEmpty(message = "Payment code is mandatory")
    private String paymentCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_code_type")
    private PaymentModeType paymentModeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public PaymentModeType getPaymentModeType() {
        return paymentModeType;
    }

    public void setPaymentModeType(PaymentModeType paymentModeType) {
        this.paymentModeType = paymentModeType;
    }
}
