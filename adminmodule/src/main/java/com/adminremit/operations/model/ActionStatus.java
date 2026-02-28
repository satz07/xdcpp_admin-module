package com.adminremit.operations.model;

public enum ActionStatus {
	DISBURSE("Disburse"),
	REFUND("Refund"),
	APPROVE("Approve"),
	REJECT("Reject");
	
	private final String status;
	
	ActionStatus(String status) {
		this.status = status;		
	}

	public String getStatus() {
		return status;
	}	
}
