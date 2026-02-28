package com.adminremit.report.compliance.dto;

public class RemitterManualKyc {
	
	private String manualKycApplicable;
	private String documentLink1;
	private String documentLink2;
	private String documentName1;
	private String documentName2;	
	
	public String getManualKycApplicable() {
		return manualKycApplicable;
	}
	public void setManualKycApplicable(String manualKycApplicable) {
		this.manualKycApplicable = manualKycApplicable;
	}
	public String getDocumentLink1() {
		return documentLink1;
	}
	public void setDocumentLink1(String documentLink1) {
		this.documentLink1 = documentLink1;
	}
	public String getDocumentLink2() {
		return documentLink2;
	}
	public void setDocumentLink2(String documentLink2) {
		this.documentLink2 = documentLink2;
	}
	public String getDocumentName1() {
		return documentName1;
	}
	public void setDocumentName1(String documentName1) {
		this.documentName1 = documentName1;
	}
	public String getDocumentName2() {
		return documentName2;
	}
	public void setDocumentName2(String documentName2) {
		this.documentName2 = documentName2;
	}	
}
