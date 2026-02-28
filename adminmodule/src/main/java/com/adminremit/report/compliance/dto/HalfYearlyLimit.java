package com.adminremit.report.compliance.dto;

public class HalfYearlyLimit {
	
	private String halfYearlyLimitAsPerCurrentTxnAmt;
	private String definedhalfYearlyLimit;
	private String transactionCurrency;
	
	public String getHalfYearlyLimitAsPerCurrentTxnAmt() {
		return halfYearlyLimitAsPerCurrentTxnAmt;
	}
	public void setHalfYearlyLimitAsPerCurrentTxnAmt(String halfYearlyLimitAsPerCurrentTxnAmt) {
		this.halfYearlyLimitAsPerCurrentTxnAmt = halfYearlyLimitAsPerCurrentTxnAmt;
	}
	public String getDefinedhalfYearlyLimit() {
		return definedhalfYearlyLimit;
	}
	public void setDefinedhalfYearlyLimit(String definedhalfYearlyLimit) {
		this.definedhalfYearlyLimit = definedhalfYearlyLimit;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}	
}
