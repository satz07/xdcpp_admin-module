package com.adminremit.backoffice.dto;

import java.math.BigDecimal;

public class DisbursementSearchDTO {

    private String userType;
    private String email;
    private String transactionRef;
    private String  paymentMode;
    private String receiveMode;
    private String currency;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(String receiveMode) {
        this.receiveMode = receiveMode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}    
}
