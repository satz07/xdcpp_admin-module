package com.adminremit.beneficiary.dto;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.enums.Gender;

public class BeneficiaryDTO {

    private Long id;

    private String fullName;

    private String mobileNumber;

    private Gender gender;

    private String email;

    private String nickName;

    private Long beneficiaryRelationship;

    private boolean publish;

    private String unit;

    private String address;

    private String pincode;

    private Long country;

    private Long user;

    private Long receiverType;

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private AccountType accountType;

    private String branchName;

    private Long beneficiaryAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    public void setBeneficiaryRelationship(Long beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Long receiverType) {
        this.receiverType = receiverType;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }
}
