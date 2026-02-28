package com.adminremit.operations.model;

import com.adminremit.common.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "operation_file_upload")
public class OperationFileUpload extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="upload_type", nullable=false)
    private String uploadType;

    @Column(name="account_type", nullable=false)
    private String accountType;

    @Column(name="statement_type", nullable=false)
    private String statementType;

    @Column(name="currency_code", nullable=false)
    private Long currencyCode;

    @Column(name="currency_name", nullable=false)
    private String currencyName;

    @Column(name="bank_details", nullable=false)
    private Long bankDetails;

    @Column(name="bank_name", nullable=false)
    private String bankName;

    @Column(name="account_number", nullable=false)
    private String accountNumber;

    @Column(name="file_count")
    private int fileCount;

    @Column(name="status")
    private String status;

    @Column(name="logged_user")
    private String loginUser;


    @OneToMany(mappedBy = "operationFile", fetch = FetchType.LAZY)
    private Set<FileInfo> fileInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Set<FileInfo> getFileInfo() {
        return fileInfo;
    }
    public void setFileInfo(Set<FileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }
}
