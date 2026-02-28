package com.adminremit.report.compliance.dto;

public class RemitterBankAccountName {
	
	private String remitterBankAccountName;
	private String senderAcctHolderNameFromFile;
	
	public String getRemitterBankAccountName() {
		return remitterBankAccountName;
	}
	public void setRemitterBankAccountName(String remitterBankAccountName) {
		this.remitterBankAccountName = remitterBankAccountName;
	}
	public String getSenderAcctHolderNameFromFile() {
		return senderAcctHolderNameFromFile;
	}
	public void setSenderAcctHolderNameFromFile(String senderAcctHolderNameFromFile) {
		this.senderAcctHolderNameFromFile = senderAcctHolderNameFromFile;
	}
}
