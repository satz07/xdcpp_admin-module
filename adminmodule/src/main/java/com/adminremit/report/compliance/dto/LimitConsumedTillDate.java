package com.adminremit.report.compliance.dto;

public class LimitConsumedTillDate {
	
	private String limitConsumed;
	private String transactionCurrency;

	public String getLimitConsumed() {
		return limitConsumed;
	}

	public void setLimitConsumed(String limitConsumed) {
		this.limitConsumed = limitConsumed;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}	
}
