package com.adminremit.masters.models;

import com.adminremit.auth.models.Partner;
import com.adminremit.common.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "admin_tat")
@Getter
@Setter
public class TAT extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "amount_slab_from",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal amountSlabFrom;

    @Column(name = "range_to",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal amountSlabTo;

    @ManyToOne
    @JoinColumn(name = "originating_country_currency")
    private Currencies originCCM;

    @ManyToOne
    @JoinColumn(name = "disbursement_country_currency")
    private Currencies disburseCCM;

    @ManyToOne
    @JoinColumn(name = "payment_mode_type")
    private PaymentReceiveMode paymentMode;

    @NotNull
    @Column(name = "payment_hrs")
    private int paymentHrs;

    @ManyToOne
    @JoinColumn(name = "receive_mode_type")
    private PaymentReceiveMode receiveMode;

    @NotNull
    @Column(name = "receive_hrs")
    private int receiveHrs;

    @NotNull
    @Column(name = "expiry_time")
    private String expiryTime;

    @NotNull
    @Column(name = "cancel_time")
    private String cancelTime;

    @ManyToOne
    @JoinColumn(name = "partner_name")
    private Partner partnerName;
    
    @Column(name = "user_type")
    private String userType;

	public PaymentReceiveMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentReceiveMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentReceiveMode getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(PaymentReceiveMode receiveMode) {
		this.receiveMode = receiveMode;
	}

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
