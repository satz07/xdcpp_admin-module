package com.adminremit.masters.dto;

public class PurposeListDTO {

    private String userType;

    private Long countryCode;

    private Long currencyCode;

    private Long receiveCode;

    private String purposeCode;

    private String purpose;

    private String purposeType;

    private Long receiverTypes;



    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Long countryCode) {
        this.countryCode = countryCode;
    }

    public Long getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Long currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(Long receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public Long getReceiverTypes() {
        return receiverTypes;
    }

    public void setReceiverTypes(Long receiverTypes) {
        this.receiverTypes = receiverTypes;
    }
}
