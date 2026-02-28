package com.adminremit.operations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfered_acc_details")
public class TransferAccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="value_date")
    Date valueDate;

    @Column(name="transaction_type")
    String transactionType;

    @Column(name="currency_code")
    String currencyCode;

    @Column(name="amt_received")
    BigDecimal amountReceived;

    @Column(name="transaction_refno")
    String transactionRefNo;

    @Column(name="bank_ref")
    String bankRef;

    @Column(name="sender_name")
    String senderName;

    @Column(name = "bsb_number")
   private BigDecimal bsbNumber;

    @Column(name="account_number", nullable=true)
    private BigDecimal accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileinfo_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileInfo fileInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getTransactionRefNo() {
        return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
        this.transactionRefNo = transactionRefNo;
    }

    public String getBankRef() {
        return bankRef;
    }

    public void setBankRef(String bankRef) {
        this.bankRef = bankRef;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @JsonIgnore
    public FileInfo getFileInfo() {
        return fileInfo;
    }

    @JsonIgnore
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public BigDecimal getBsbNumber() {
        return bsbNumber;
    }

    public void setBsbNumber(BigDecimal bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public BigDecimal getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigDecimal accountNumber) {
        this.accountNumber = accountNumber;
    }
}
