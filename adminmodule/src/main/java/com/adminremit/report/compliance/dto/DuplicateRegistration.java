package com.adminremit.report.compliance.dto;

public class DuplicateRegistration {
	
	private String remitterNameAndDob;
	private String emailIdOfDuplicateNameAndDob;
	
	public String getRemitterNameAndDob() {
		return remitterNameAndDob;
	}
	public void setRemitterNameAndDob(String remitterNameAndDob) {
		this.remitterNameAndDob = remitterNameAndDob;
	}
	public String getEmailIdOfDuplicateNameAndDob() {
		return emailIdOfDuplicateNameAndDob;
	}
	public void setEmailIdOfDuplicateNameAndDob(String emailIdOfDuplicateNameAndDob) {
		this.emailIdOfDuplicateNameAndDob = emailIdOfDuplicateNameAndDob;
	}
}
