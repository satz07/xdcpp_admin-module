package com.adminremit.masters.dto;

public class PaymentReceiveModeDTO {

    private Long id;

    private String userType;

    private String userCategory;

    private String countries;

    private String currencies;

    private String paymentCodeMaster;

    private String description;

    private String comments;

    private String remarks;

    private String paymentModeType;

    private Boolean publish;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public String getPaymentCodeMaster() {
        return paymentCodeMaster;
    }

    public void setPaymentCodeMaster(String paymentCodeMaster) {
        this.paymentCodeMaster = paymentCodeMaster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPaymentModeType() {
        return paymentModeType;
    }

    public void setPaymentModeType(String paymentModeType) {
        this.paymentModeType = paymentModeType;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}
