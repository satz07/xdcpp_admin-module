package com.adminremit.report.compliance.dto;

public class YearlyLimit {
	
	private String yearlyLimitAsPerCurrentTxnAmt;
	private String definedYearlyLimit;
	private String transactionCurrency;
	
	public String getYearlyLimitAsPerCurrentTxnAmt() {
		return yearlyLimitAsPerCurrentTxnAmt;
	}
	public void setYearlyLimitAsPerCurrentTxnAmt(String yearlyLimitAsPerCurrentTxnAmt) {
		this.yearlyLimitAsPerCurrentTxnAmt = yearlyLimitAsPerCurrentTxnAmt;
	}
	public String getDefinedYearlyLimit() {
		return definedYearlyLimit;
	}
	public void setDefinedYearlyLimit(String definedYearlyLimit) {
		this.definedYearlyLimit = definedYearlyLimit;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}	
}
