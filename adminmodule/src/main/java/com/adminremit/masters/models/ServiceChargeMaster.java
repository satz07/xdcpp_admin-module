package com.adminremit.masters.models;

import org.hibernate.envers.Audited;

import com.adminremit.auth.models.Partner;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_charge_master")
@Audited
public class ServiceChargeMaster extends FeeSettingBaseModel {


    @ManyToOne
    @JoinColumn(name = "payment_code")
    private PaymentReceiveMode paymentCode;

    @ManyToOne
    @JoinColumn(name = "receive_code")
    private PaymentReceiveMode receiveCode;

    @Column(name = "range_from",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal rangeFrom;

    @Column(name = "range_to",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal rangeTo;

    @Column(name = "service_charge",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal serviceCharge;

    @Column(name = "is_service_charge_applicable")
    private boolean isServiceChargeApplicable;

    @Column(name = "flat_perc_flag")
    private boolean flatPercFlag;
    
    @ManyToOne
    @JoinColumn(name = "partner_name")
    private Partner partnerName;


    public Partner getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(Partner partnerName) {
		this.partnerName = partnerName;
	}

	public PaymentReceiveMode getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(PaymentReceiveMode paymentCode) {
        this.paymentCode = paymentCode;
    }

    public PaymentReceiveMode getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(PaymentReceiveMode receiveCode) {
        this.receiveCode = receiveCode;
    }

    public BigDecimal getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(BigDecimal rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public BigDecimal getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(BigDecimal rangeTo) {
        this.rangeTo = rangeTo;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public boolean isServiceChargeApplicable() {
        return isServiceChargeApplicable;
    }

    public boolean getIsServiceChargeApplicable() {
        return isServiceChargeApplicable;
    }

    public void setIsServiceChargeApplicable(boolean serviceChargeApplicable) {
        isServiceChargeApplicable = serviceChargeApplicable;
    }

    public boolean isFlatPercFlag() {
        return flatPercFlag;
    }

    public void setFlatPercFlag(boolean flatPercFlag) {
        this.flatPercFlag = flatPercFlag;
    }

    public boolean getIsFlatPercFlag() {
        return flatPercFlag;
    }
}
