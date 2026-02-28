package com.adminremit.user.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.adminremit.operations.model.UserType;



public class CurrencyCalculatorModel {

    String fromCountryCode;
    String fromCurrencyCode;
    BigDecimal fromCurrencyValue;
    String toCountryCode;
    String toCurrencyCode;
    Long fromCountryCodeId;
    Long toCountryCodeId;
    BigDecimal toCurrencyValue;
    BigDecimal transferAmount;
    BigDecimal transactionFee;
    BigDecimal totalConvertedValue;
    Long paymentCodeid;
    String paymentType; // Receive or Payment

    Long receiveCodeid;
    String receiveType; // Receive or Payment

    // Display Value
    String strFromCurrencyValue;
    String strToCurrencyValue;
    String strTransferAmount;
    String strTransactionFee;
    String strTotalConvertedValue;
    Map<Long,String> paymentReceiveModes = new HashMap<Long,String>();

    BigDecimal fromRange;
    BigDecimal toRange;
    int tat;
    BigDecimal customerRate;

    UserType userAccountType;
    
    private String fxBaseRate;
    private String marginSpread;

    public String getFromCountryCode() {
        return fromCountryCode;
    }

    public void setFromCountryCode(String fromCountryCode) {
        this.fromCountryCode = fromCountryCode;
    }

    public String getToCountryCode() {
        return toCountryCode;
    }

    public void setToCountryCode(String toCountryCode) {
        this.toCountryCode = toCountryCode;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getFromCurrencyValue() {
        return fromCurrencyValue;
    }

    public void setFromCurrencyValue(BigDecimal fromCurrencyValue) {
        this.fromCurrencyValue = fromCurrencyValue;
    }

    public BigDecimal getToCurrencyValue() {
        return toCurrencyValue;
    }

    public void setToCurrencyValue(BigDecimal toCurrencyValue) {
        this.toCurrencyValue = toCurrencyValue;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }


    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTotalConvertedValue() {
        return totalConvertedValue;
    }

    public void setTotalConvertedValue(BigDecimal totalConvertedValue) {
        this.totalConvertedValue = totalConvertedValue;
    }

    public String getStrFromCurrencyValue() {
        return strFromCurrencyValue;
    }

    public void setStrFromCurrencyValue(String strFromCurrencyValue) {
        this.strFromCurrencyValue = strFromCurrencyValue;
    }

    public String getStrToCurrencyValue() {
        return strToCurrencyValue;
    }

    public void setStrToCurrencyValue(String strToCurrencyValue) {
        this.strToCurrencyValue = strToCurrencyValue;
    }

    public String getStrTransferAmount() {
        return strTransferAmount;
    }

    public void setStrTransferAmount(String strTransferAmount) {
        this.strTransferAmount = strTransferAmount;
    }

    public String getStrTransactionFee() {
        return strTransactionFee;
    }

    public void setStrTransactionFee(String strTransactionFee) {
        this.strTransactionFee = strTransactionFee;
    }

    public String getStrTotalConvertedValue() {
        return strTotalConvertedValue;
    }

    public void setStrTotalConvertedValue(String strTotalConvertedValue) {
        this.strTotalConvertedValue = strTotalConvertedValue;
    }

    public Map<Long, String> getPaymentReceiveModes() {
        return paymentReceiveModes;
    }

    public void setPaymentReceiveModes(Map<Long, String> paymentReceiveModes) {
        this.paymentReceiveModes = paymentReceiveModes;
    }

    public BigDecimal getFromRange() {
        return fromRange;
    }

    public void setFromRange(BigDecimal fromRange) {
        this.fromRange = fromRange;
    }

    public BigDecimal getToRange() {
        return toRange;
    }

    public void setToRange(BigDecimal toRange) {
        this.toRange = toRange;
    }

    public int getTat() {
        return tat;
    }

    public void setTat(int tat) {
        this.tat = tat;
    }

    public BigDecimal getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(BigDecimal customerRate) {
        this.customerRate = customerRate;
    }

    public Long getPaymentCodeid() {
        return paymentCodeid;
    }

    public void setPaymentCodeid(Long paymentCodeid) {
        this.paymentCodeid = paymentCodeid;
    }

    public Long getReceiveCodeid() {
        return receiveCodeid;
    }

    public void setReceiveCodeid(Long receiveCodeid) {
        this.receiveCodeid = receiveCodeid;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public Long getFromCountryCodeId() {
        return fromCountryCodeId;
    }

    public void setFromCountryCodeId(Long fromCountryCodeId) {
        this.fromCountryCodeId = fromCountryCodeId;
    }

    public Long getToCountryCodeId() {
        return toCountryCodeId;
    }

    public void setToCountryCodeId(Long toCountryCodeId) {
        this.toCountryCodeId = toCountryCodeId;
    }

    public UserType getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(UserType userAccountType) {
        this.userAccountType = userAccountType;
    }

	public String getFxBaseRate() {
		return fxBaseRate;
	}

	public void setFxBaseRate(String fxBaseRate) {
		this.fxBaseRate = fxBaseRate;
	}

	public String getMarginSpread() {
		return marginSpread;
	}

	public void setMarginSpread(String marginSpread) {
		this.marginSpread = marginSpread;
	}
}
