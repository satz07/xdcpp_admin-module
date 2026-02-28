package com.adminremit.backoffice.dto;

import java.math.BigDecimal;

import com.adminremit.backoffice.model.TransactionType;
import com.adminremit.backoffice.model.TransferCurrency;
import com.adminremit.backoffice.model.TransferType;
import com.adminremit.personaldetails.model.DocumentTypes;

public class RemittanceDTO {

    private String version;

    private String uniqueRequestNo;

    private String partnerCode;

    private TransactionType remitterType;

    private String remitterFullName;

    private String remitterAddress;

    private String remitterZipCode;

    private String remitterCity;

    private String remitterProvince;

    private String remitterPhone;

    private String remitterEmail;

    private String remitterCountry;

    private DocumentTypes remitterIdType;
    
    private String remitterDocumentType;

    private String remitterIdNumber;

    private TransactionType beneficiaryType;

    private String beneFullName;

    private String beneficiaryAddress;

    private String beneficiaryZipCode;

    private String beneficiaryCity;

    private String beneficiaryProvince;

    private String beneficiaryCountry;

    private String beneficiaryMobile;

    private String beneficiaryEmail;

    private String beneficiaryAccountNo;

    private String beneficiaryIFSC;

    private TransferType transferType;

    private TransferCurrency transferCurrencyCode;

    private BigDecimal transferAmount;

    private String remitterToBeneficiaryInfo;

    private String purposeCode;

    private Long beneficiaryId;

    private String uid;

    private String refId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUniqueRequestNo() {
        return uniqueRequestNo;
    }

    public void setUniqueRequestNo(String uniqueRequestNo) {
        this.uniqueRequestNo = uniqueRequestNo;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public TransactionType getRemitterType() {
        return remitterType;
    }

    public void setRemitterType(TransactionType remitterType) {
        this.remitterType = remitterType;
    }

    public String getRemitterFullName() {
        return remitterFullName;
    }

    public void setRemitterFullName(String remitterFullName) {
        this.remitterFullName = remitterFullName;
    }

    public String getRemitterAddress() {
        return remitterAddress;
    }

    public void setRemitterAddress(String remitterAddress) {
        this.remitterAddress = remitterAddress;
    }

    public String getRemitterCountry() {
        return remitterCountry;
    }

    public void setRemitterCountry(String remitterCountry) {
        this.remitterCountry = remitterCountry;
    }

    public DocumentTypes getRemitterIdType() {
        return remitterIdType;
    }

    public void setRemitterIdType(DocumentTypes remitterIdType) {
        this.remitterIdType = remitterIdType;
    }

    public String getRemitterIdNumber() {
        return remitterIdNumber;
    }

    public void setRemitterIdNumber(String remitterIdNumber) {
        this.remitterIdNumber = remitterIdNumber;
    }

    public TransactionType getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(TransactionType beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getBeneFullName() {
        return beneFullName;
    }

    public void setBeneFullName(String beneFullName) {
        this.beneFullName = beneFullName;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryIFSC() {
        return beneficiaryIFSC;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
        this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransferCurrency getTransferCurrencyCode() {
        return transferCurrencyCode;
    }

    public void setTransferCurrencyCode(TransferCurrency transferCurrencyCode) {
        this.transferCurrencyCode = transferCurrencyCode;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getRemitterToBeneficiaryInfo() {
        return remitterToBeneficiaryInfo;
    }

    public void setRemitterToBeneficiaryInfo(String remitterToBeneficiaryInfo) {
        this.remitterToBeneficiaryInfo = remitterToBeneficiaryInfo;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRemitterZipCode() {
        return remitterZipCode;
    }

    public void setRemitterZipCode(String remitterZipCode) {
        this.remitterZipCode = remitterZipCode;
    }

    public String getRemitterCity() {
        return remitterCity;
    }

    public void setRemitterCity(String remitterCity) {
        this.remitterCity = remitterCity;
    }

    public String getRemitterProvince() {
        return remitterProvince;
    }

    public void setRemitterProvince(String remitterProvince) {
        this.remitterProvince = remitterProvince;
    }

    public String getBeneficiaryZipCode() {
        return beneficiaryZipCode;
    }

    public void setBeneficiaryZipCode(String beneficiaryZipCode) {
        this.beneficiaryZipCode = beneficiaryZipCode;
    }

    public String getBeneficiaryCity() {
        return beneficiaryCity;
    }

    public void setBeneficiaryCity(String beneficiaryCity) {
        this.beneficiaryCity = beneficiaryCity;
    }

    public String getBeneficiaryProvince() {
        return beneficiaryProvince;
    }

    public void setBeneficiaryProvince(String beneficiaryProvince) {
        this.beneficiaryProvince = beneficiaryProvince;
    }

    public String getBeneficiaryCountry() {
        return beneficiaryCountry;
    }

    public void setBeneficiaryCountry(String beneficiaryCountry) {
        this.beneficiaryCountry = beneficiaryCountry;
    }

    public String getRemitterPhone() {
        return remitterPhone;
    }

    public void setRemitterPhone(String remitterPhone) {
        this.remitterPhone = remitterPhone;
    }

    public String getRemitterEmail() {
        return remitterEmail;
    }

    public void setRemitterEmail(String remitterEmail) {
        this.remitterEmail = remitterEmail;
    }

    public String getBeneficiaryMobile() {
        return beneficiaryMobile;
    }

    public void setBeneficiaryMobile(String beneficiaryMobile) {
        this.beneficiaryMobile = beneficiaryMobile;
    }

    public String getBeneficiaryEmail() {
        return beneficiaryEmail;
    }

    public void setBeneficiaryEmail(String beneficiaryEmail) {
        this.beneficiaryEmail = beneficiaryEmail;
    }

	public String getRemitterDocumentType() {
		return remitterDocumentType;
	}

	public void setRemitterDocumentType(String remitterDocumentType) {
		this.remitterDocumentType = remitterDocumentType;
	}
}
