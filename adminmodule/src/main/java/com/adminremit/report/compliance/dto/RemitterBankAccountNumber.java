package com.adminremit.report.compliance.dto;

public class RemitterBankAccountNumber {
	
	private String remitterBankAccountNumber;
	private String emailIdOfSameBankAccountNumber;
	
	public String getRemitterBankAccountNumber() {
		return remitterBankAccountNumber;
	}
	public void setRemitterBankAccountNumber(String remitterBankAccountNumber) {
		this.remitterBankAccountNumber = remitterBankAccountNumber;
	}
	public String getEmailIdOfSameBankAccountNumber() {
		return emailIdOfSameBankAccountNumber;
	}
	public void setEmailIdOfSameBankAccountNumber(String emailIdOfSameBankAccountNumber) {
		this.emailIdOfSameBankAccountNumber = emailIdOfSameBankAccountNumber;
	}
}
