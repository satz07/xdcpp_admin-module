package com.adminremit.auth.dto;

import java.util.Date;

public class RemitterTransactionDTO {

	private Date  BookingDate;
	private String transactionNo;
	private String originatingCurrency;
	private String originatingAmount;
	private String exchangeRate;
	private String disbursementAmount;
	private String paymentmode;
	private String recipientName;
	private String receivemode;
	private String status;
	private String iPLocation;
	private String disbursementDateTime;
	
	public Date getBookingDate() {
		return BookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		BookingDate = bookingDate;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getOriginatingCurrency() {
		return originatingCurrency;
	}
	public void setOriginatingCurrency(String originatingCurrency) {
		this.originatingCurrency = originatingCurrency;
	}
	public String getOriginatingAmount() {
		return originatingAmount;
	}
	public void setOriginatingAmount(String originatingAmount) {
		this.originatingAmount = originatingAmount;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getDisbursementAmount() {
		return disbursementAmount;
	}
	public void setDisbursementAmount(String disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}
	public String getPaymentmode() {
		return paymentmode;
	}
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getReceivemode() {
		return receivemode;
	}
	public void setReceivemode(String receivemode) {
		this.receivemode = receivemode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getiPLocation() {
		return iPLocation;
	}
	public void setiPLocation(String iPLocation) {
		this.iPLocation = iPLocation;
	}
	public String getDisbursementDateTime() {
		return disbursementDateTime;
	}
	public void setDisbursementDateTime(String disbursementDateTime) {
		this.disbursementDateTime = disbursementDateTime;
	}

}
