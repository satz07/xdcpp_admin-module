package com.adminremit.backoffice.model;

public enum YesBankAPITransactionStatus {
    COMPLETED("COMPLETED"),
    SENT_TO_BENEFICIARY("SENT_TO_BENEFICIARY"),
    IN_PROCESS("IN_PROCESS"),
    FAILED("FAILED"),
    ONHOLD("ONHOLD"),
    RETURNED_FROM_BENEFICIARY("RETURNED_FROM_BENEFICIARY");
	
	private String transactionStatus;
	
	private YesBankAPITransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
