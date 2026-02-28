package com.adminremit.operations.dto;

import java.math.BigDecimal;

public class MatchExceptionDTO {

    String email;
    String refId;
    String fromCurrencyCode;
    BigDecimal transferAmount;
    String beneficiaryName;
    String bankRefNo;
    String toCurrencyCode;
    BigDecimal amountReceived;
    int ageing;
    String status;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public int getAgeing() {
        return ageing;
    }

    public void setAgeing(int ageing) {
        this.ageing = ageing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
