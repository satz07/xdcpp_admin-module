package com.adminremit.masters.models;

import org.hibernate.envers.Audited;

import com.adminremit.auth.models.Partner;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "margin_master")
@Audited
public class MarginMaster extends FeeSettingBaseModel {

    @Column(name = "range_from",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal rangeFrom;

    @Column(name = "range_to",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal rangeTo;

    @ManyToOne
    @JoinColumn(name = "payment_code")
    private PaymentReceiveMode paymentCode;

    @ManyToOne
    @JoinColumn(name = "receive_code")
    private PaymentReceiveMode receiveCode;

    @Column(name = "txn_spread",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal txnSpread;

    @Column(name = "is_txn_spread_applicable")
    private boolean isTxnSpreadApplicable;

    @Column(name = "vendor_spread",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal vendorSpread;

    @Column(name = "is_vendor_spread_applicable")
    private boolean isVendorSpreadApplicable;

    @Column(name = "total_spread",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal totalSpread;

    @Column(name = "flat_perc_flag")
    private String flatPercFlag;
    
    @ManyToOne
    @JoinColumn(name = "partner_name")
    private Partner partnerName;


    public Partner getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(Partner partnerName) {
		this.partnerName = partnerName;
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

    public BigDecimal getTxnSpread() {
        return txnSpread;
    }

    public void setTxnSpread(BigDecimal txnSpread) {
        this.txnSpread = txnSpread;
    }

    public boolean isTxnSpreadApplicable() {
        return isTxnSpreadApplicable;
    }

    public void setIsTxnSpreadApplicable(boolean txnSpreadApplicable) {
        isTxnSpreadApplicable = txnSpreadApplicable;
    }

    public boolean getIsTxnSpreadApplicable() {
        return isTxnSpreadApplicable;
    }

    public BigDecimal getVendorSpread() {
        return vendorSpread;
    }

    public void setVendorSpread(BigDecimal vendorSpread) {
        this.vendorSpread = vendorSpread;
    }

    public boolean isVendorSpreadApplicable() {
        return isVendorSpreadApplicable;
    }

    public boolean getIsVendorSpreadApplicable() {
        return isVendorSpreadApplicable;
    }

    public void setIsVendorSpreadApplicable(boolean vendorSpreadApplicable) {
        isVendorSpreadApplicable = vendorSpreadApplicable;
    }

    public BigDecimal getTotalSpread() {
        return totalSpread;
    }

    public void setTotalSpread(BigDecimal totalSpread) {
        this.totalSpread = totalSpread;
    }

    public String getFlatPercFlag() {
        return flatPercFlag;
    }

    public void setFlatPercFlag(String flatPercFlag) {
        this.flatPercFlag = flatPercFlag;
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
}
