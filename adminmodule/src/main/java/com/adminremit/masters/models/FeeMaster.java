package com.adminremit.masters.models;

import com.adminremit.auth.models.Partner;
import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fee_master")
@Audited
public class FeeMaster extends FeeSettingBaseModel {


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

    @Column(name = "transaction_fee",columnDefinition="Decimal(10,5) default '0.00'")
    private BigDecimal transactionFee;

    @Column(name = "is_trans_fee_applicable")
    private boolean isTransactionFeeApplicable;

    @Column(name = "vendor_trans_fee",columnDefinition="Decimal(10,5) default '0.00'")
    private BigDecimal vendorTransactionFee;

    @Column(name = "is_vendor_fee_applicable")
    private boolean isVendorFeeApplicable;

    @Column(name = "flat_perc_flag")
    private String flatPercFlag;

    @Column(name = "paymentmode_tat",columnDefinition = "integer default 0")
    private int paymentmodeTAT=0;

    @Column(name = "receivemode_tat",columnDefinition = "integer default 0")
    private int receivemodeTAT=0;
    
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

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public boolean isTransactionFeeApplicable() {
        return isTransactionFeeApplicable;
    }

    public boolean getIsTransactionFeeApplicable() {
        return isTransactionFeeApplicable;
    }

    public void setIsTransactionFeeApplicable(boolean transactionFeeApplicable) {
        isTransactionFeeApplicable = transactionFeeApplicable;
    }

    public BigDecimal getVendorTransactionFee() {
        return vendorTransactionFee;
    }

    public void setVendorTransactionFee(BigDecimal vendorTransactionFee) {
        this.vendorTransactionFee = vendorTransactionFee;
    }

    public boolean isVendorFeeApplicable() {
        return isVendorFeeApplicable;
    }

    public boolean getIsVendorFeeApplicable() {
        return isVendorFeeApplicable;
    }

    public void setIsVendorFeeApplicable(boolean vendorFeeApplicable) {
        isVendorFeeApplicable = vendorFeeApplicable;
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

    public int getPaymentmodeTAT() {
        return paymentmodeTAT;
    }

    public void setPaymentmodeTAT(int paymentmodeTAT) {
        this.paymentmodeTAT = paymentmodeTAT;
    }

    public int getReceivemodeTAT() {
        return receivemodeTAT;
    }

    public void setReceivemodeTAT(int receivemodeTAT) {
        this.receivemodeTAT = receivemodeTAT;
    }	
}
