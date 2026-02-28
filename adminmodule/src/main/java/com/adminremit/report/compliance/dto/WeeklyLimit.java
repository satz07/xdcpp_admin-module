package com.adminremit.report.compliance.dto;

public class WeeklyLimit {
	
	private String weeklyLimitAsPerCurrentTxnAmt;
	private String definedWeeklyLimit;
	private String transactionCurrency;
	
	public String getWeeklyLimitAsPerCurrentTxnAmt() {
		return weeklyLimitAsPerCurrentTxnAmt;
	}
	public void setWeeklyLimitAsPerCurrentTxnAmt(String weeklyLimitAsPerCurrentTxnAmt) {
		this.weeklyLimitAsPerCurrentTxnAmt = weeklyLimitAsPerCurrentTxnAmt;
	}
	public String getDefinedWeeklyLimit() {
		return definedWeeklyLimit;
	}
	public void setDefinedWeeklyLimit(String definedWeeklyLimit) {
		this.definedWeeklyLimit = definedWeeklyLimit;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}	
}
