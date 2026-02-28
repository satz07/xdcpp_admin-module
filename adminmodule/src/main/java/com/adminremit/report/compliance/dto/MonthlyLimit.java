package com.adminremit.report.compliance.dto;

public class MonthlyLimit {
	
	private String monthlyLimitAsPerCurrentTxnAmt;
	private String definedMonthlyLimit;
	private String transactionCurrency;
	
	public String getMonthlyLimitAsPerCurrentTxnAmt() {
		return monthlyLimitAsPerCurrentTxnAmt;
	}
	public void setMonthlyLimitAsPerCurrentTxnAmt(String monthlyLimitAsPerCurrentTxnAmt) {
		this.monthlyLimitAsPerCurrentTxnAmt = monthlyLimitAsPerCurrentTxnAmt;
	}
	public String getDefinedMonthlyLimit() {
		return definedMonthlyLimit;
	}
	public void setDefinedMonthlyLimit(String definedMonthlyLimit) {
		this.definedMonthlyLimit = definedMonthlyLimit;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
}
