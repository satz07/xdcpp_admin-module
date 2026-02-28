package com.adminremit.backoffice.model;

public enum TransferType {
    IMPS("IMPS"),
    RTGS("RTGS"),
    NEFT("NEFT"),
    UPI("UPI"),
    ANY("ANY"),
    FT("FT");
	
	private String transferType;
	
	private TransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}	
}
