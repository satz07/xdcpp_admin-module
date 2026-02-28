package com.adminremit.report.compliance.dto;

public class AverageTicketSize {
	
	private String avgRemittersProcessedTxns;
	private String transactionCurrency;

	public String getAvgRemittersProcessedTxns() {
		return avgRemittersProcessedTxns;
	}

	public void setAvgRemittersProcessedTxns(String avgRemittersProcessedTxns) {
		this.avgRemittersProcessedTxns = avgRemittersProcessedTxns;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}	
}
