package com.adminremit.report.compliance.dto;

public class DailyLimit {
	
	private String dailyLimitAsPerCurrentTxnAmt;
	private String definedDailyLimit;
	private String transactionCurrency;
		
	public String getDailyLimitAsPerCurrentTxnAmt() {
		return dailyLimitAsPerCurrentTxnAmt;
	}
	public void setDailyLimitAsPerCurrentTxnAmt(String dailyLimitAsPerCurrentTxnAmt) {
		this.dailyLimitAsPerCurrentTxnAmt = dailyLimitAsPerCurrentTxnAmt;
	}
	public String getDefinedDailyLimit() {
		return definedDailyLimit;
	}
	public void setDefinedDailyLimit(String definedDailyLimit) {
		this.definedDailyLimit = definedDailyLimit;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
}
