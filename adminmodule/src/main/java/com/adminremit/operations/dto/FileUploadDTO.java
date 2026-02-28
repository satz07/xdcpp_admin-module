package com.adminremit.operations.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {

    private Long id;
    private String uploadType;
    private String accountType;
    private String statementType;
    private Long currencyCode;
    private Long bankDetails;
    private String accountNumber;
    private MultipartFile[] interimFile;
    private boolean isApproved=true;

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public Long getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Long currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(Long bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public MultipartFile[] getInterimFile() {
        return interimFile;
    }

    public void setInterimFile(MultipartFile[] interimFile) {
        this.interimFile = interimFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean approved) {
        isApproved = approved;
    }
}
