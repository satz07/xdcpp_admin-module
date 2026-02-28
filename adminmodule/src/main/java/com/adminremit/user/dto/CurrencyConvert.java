package com.adminremit.user.dto;

import java.math.BigDecimal;

public class CurrencyConvert {

    String fromCountry;
    String toCountry;
    Long fromCountryId;
    Long toCountryId;
    String currencyFrom;
    String currencyTo;
    BigDecimal amt;
    Long transferMethod;
    String uid;


    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Long getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(Long transferMethod) {
        this.transferMethod = transferMethod;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getFromCountryId() {
    	return fromCountryId == null ? Long.decode(this.fromCountry) : fromCountryId;
    }

    public void setFromCountryId(Long fromCountryId) {
        this.fromCountryId = fromCountryId;
    }

    public Long getToCountryId() {
    	return toCountryId == null ? Long.decode(this.toCountry) : toCountryId;
    }

    public void setToCountryId(Long toCountryId) {
        this.toCountryId = toCountryId;
    }
}
