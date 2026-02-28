package com.adminremit.backoffice.model;

public enum PaymentType {
    PENNY_DROP("PENNY_DROP"),
    DISBURSEMENT_APPROVE("DISBURSEMENT_APPROVE");   
	
	private String paymentType;
	
	private PaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}	
}
