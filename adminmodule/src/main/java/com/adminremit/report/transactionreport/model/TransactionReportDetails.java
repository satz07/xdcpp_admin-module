package com.adminremit.report.transactionreport.model;

import java.math.BigDecimal;
import java.util.Date;

import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.operations.model.WorkflowStatus;

public class TransactionReportDetails {
	
	private String partnerId;
	private Date bookingDateTime;
	private String email;
	private String transactionNumber;
	private String originatingCurrency;
	private BigDecimal originatingAmount;
	private String receivingCurrency;
	private BigDecimal totalFees;
	private String fxBaseRate;
	private String marginSpread;
	private BigDecimal exchangeRate;
	private BigDecimal disbursementAmount;
	private String paymentMode;
	private String receiveMode;
	private String remitterName;
	private String receipientName;
	private Long relationshipWithRemitter;
	private String purpose;
	private String bankName;
	private String accountNumber;
	private String vpaHandle;
	private Date disbursementDateAndTime;
	private String disbursementBank;
	private YesBankAPITransactionStatus disbursementBankStatus;
	private String utrNumber;
	private Date utrTime;
	private String bookingDate;
	private String bookingTime;
	private String disbursementBookingDateAndTimeToDisplay;
	private String utrTimeToDisplay;
	private String relationshipWithRemitterToDisplay;
	private String fxBaseRateToDisplay;
	private WorkflowStatus status;
	private String statusToDisplay;
	private String disbursementStatusToDisplay;
	private Long orderSeq;
	
	public TransactionReportDetails(String partnerId, Date bookingDateTime, String email, String transactionNumber,
			String originatingCurrency, BigDecimal originatingAmount, String receivingCurrency, BigDecimal totalFees,
			String fxBaseRate, String marginSpread, BigDecimal exchangeRate, BigDecimal disbursementAmount, String paymentMode,
			String receiveMode, String remitterName, String receipientName, Long relationshipWithRemitter,
			String purpose, String bankName, String accountNumber, String vpaHandle, Date disbursementDateAndTime,
			 YesBankAPITransactionStatus disbursementBankStatus, String utrNumber, Date utrTime, WorkflowStatus status) {
		//String disbursementBank,		 
		super();
		this.partnerId = partnerId;
		this.bookingDateTime = bookingDateTime;
		this.email = email;
		this.transactionNumber = transactionNumber;
		this.originatingCurrency = originatingCurrency;
		this.originatingAmount = originatingAmount;
		this.receivingCurrency = receivingCurrency;
		this.totalFees = totalFees;
		this.fxBaseRate = fxBaseRate;
		this.marginSpread = marginSpread;
		this.exchangeRate = exchangeRate;
		this.disbursementAmount = disbursementAmount;
		this.paymentMode = paymentMode;
		this.receiveMode = receiveMode;
		this.remitterName = remitterName;
		this.receipientName = receipientName;
		this.relationshipWithRemitter = relationshipWithRemitter;
		this.purpose = purpose;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.vpaHandle = vpaHandle;
		this.disbursementDateAndTime = disbursementDateAndTime;
		//this.disbursementBank = disbursementBank;
		this.disbursementBankStatus = disbursementBankStatus;
		this.utrNumber = utrNumber;
		this.utrTime = utrTime;
		this.status = status;
	}	

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}	

	public WorkflowStatus getStatus() {
		return status;
	}

	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Date getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(Date bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getOriginatingCurrency() {
		return originatingCurrency;
	}

	public void setOriginatingCurrency(String originatingCurrency) {
		this.originatingCurrency = originatingCurrency;
	}

	public BigDecimal getOriginatingAmount() {
		return originatingAmount;
	}

	public void setOriginatingAmount(BigDecimal originatingAmount) {
		this.originatingAmount = originatingAmount;
	}

	public String getReceivingCurrency() {
		return receivingCurrency;
	}

	public void setReceivingCurrency(String receivingCurrency) {
		this.receivingCurrency = receivingCurrency;
	}

	public BigDecimal getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(BigDecimal totalFees) {
		this.totalFees = totalFees;
	}

	public String getFxBaseRate() {
		return fxBaseRate;
	}

	public void setFxBaseRate(String fxBaseRate) {
		this.fxBaseRate = fxBaseRate;
	}

	public String getMarginSpread() {
		return marginSpread;
	}

	public void setMarginSpread(String marginSpread) {
		this.marginSpread = marginSpread;
	}

	public BigDecimal getDisbursementAmount() {
		return disbursementAmount;
	}

	public void setDisbursementAmount(BigDecimal disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(String receiveMode) {
		this.receiveMode = receiveMode;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getReceipientName() {
		return receipientName;
	}

	public void setReceipientName(String receipientName) {
		this.receipientName = receipientName;
	}

	public Long getRelationshipWithRemitter() {
		return relationshipWithRemitter;
	}

	public void setRelationshipWithRemitter(Long relationshipWithRemitter) {
		this.relationshipWithRemitter = relationshipWithRemitter;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getVpaHandle() {
		return vpaHandle;
	}

	public void setVpaHandle(String vpaHandle) {
		this.vpaHandle = vpaHandle;
	}

	public Date getDisbursementDateAndTime() {
		return disbursementDateAndTime;
	}

	public void setDisbursementDateAndTime(Date disbursementDateAndTime) {
		this.disbursementDateAndTime = disbursementDateAndTime;
	}

	public String getDisbursementBank() {
		return disbursementBank;
	}

	public void setDisbursementBank(String disbursementBank) {
		this.disbursementBank = disbursementBank;
	}

	public YesBankAPITransactionStatus getDisbursementBankStatus() {
		return disbursementBankStatus;
	}

	public void setDisbursementBankStatus(YesBankAPITransactionStatus disbursementBankStatus) {
		this.disbursementBankStatus = disbursementBankStatus;
	}

	public String getUtrNumber() {
		return utrNumber;
	}

	public void setUtrNumber(String utrNumber) {
		this.utrNumber = utrNumber;
	}

	public Date getUtrTime() {
		return utrTime;
	}

	public void setUtrTime(Date utrTime) {
		this.utrTime = utrTime;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getDisbursementBookingDateAndTimeToDisplay() {
		return disbursementBookingDateAndTimeToDisplay;
	}

	public void setDisbursementBookingDateAndTimeToDisplay(String disbursementBookingDateAndTimeToDisplay) {
		this.disbursementBookingDateAndTimeToDisplay = disbursementBookingDateAndTimeToDisplay;
	}

	public String getUtrTimeToDisplay() {
		return utrTimeToDisplay;
	}

	public void setUtrTimeToDisplay(String utrTimeToDisplay) {
		this.utrTimeToDisplay = utrTimeToDisplay;
	}

	public String getRelationshipWithRemitterToDisplay() {
		return relationshipWithRemitterToDisplay;
	}

	public void setRelationshipWithRemitterToDisplay(String relationshipWithRemitterToDisplay) {
		this.relationshipWithRemitterToDisplay = relationshipWithRemitterToDisplay;
	}

	public String getFxBaseRateToDisplay() {
		return fxBaseRateToDisplay;
	}

	public void setFxBaseRateToDisplay(String fxBaseRateToDisplay) {
		this.fxBaseRateToDisplay = fxBaseRateToDisplay;
	}

	public String getStatusToDisplay() {
		return statusToDisplay;
	}

	public void setStatusToDisplay(String statusToDisplay) {
		this.statusToDisplay = statusToDisplay;
	}

	public String getDisbursementStatusToDisplay() {
		return disbursementStatusToDisplay;
	}

	public void setDisbursementStatusToDisplay(String disbursementStatusToDisplay) {
		this.disbursementStatusToDisplay = disbursementStatusToDisplay;
	}
    public Long getOrderSeq() {
        return orderSeq;
    }
    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }
}
