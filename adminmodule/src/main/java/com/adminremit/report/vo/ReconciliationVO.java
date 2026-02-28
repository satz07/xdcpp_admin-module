package com.adminremit.report.vo;

import java.util.Date;

public class ReconciliationVO {
    private Date valueDate;
    private String currency;
    private String transactionType;
    private Double amount;
    private String transactionRef;
    private String bankRef;
    private String accountName;
    private Date updatedOn;
    private String updatedBy;
    private String bsbNumber;
    private String accountNumber;
    private Date entryDate;
    private String noMatch;
    
    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getBankRef() {
        return bankRef;
    }

    public void setBankRef(String bankRef) {
        this.bankRef = bankRef;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

	public String getBsbNumber() {
		return bsbNumber;
	}

	public void setBsbNumber(String bsbNumber) {
		this.bsbNumber = bsbNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getNoMatch() {
		return noMatch;
	}

	public void setNoMatch(String noMatch) {
		this.noMatch = noMatch;
	}
    
    
}
