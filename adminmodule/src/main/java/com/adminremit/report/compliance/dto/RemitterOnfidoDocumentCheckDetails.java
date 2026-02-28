package com.adminremit.report.compliance.dto;

public class RemitterOnfidoDocumentCheckDetails {
	
	private String documentTypeSelectedByUser;
	private String responseFromOnfido;
	private String checkId;
	
	public String getDocumentTypeSelectedByUser() {
		return documentTypeSelectedByUser;
	}
	public void setDocumentTypeSelectedByUser(String documentTypeSelectedByUser) {
		this.documentTypeSelectedByUser = documentTypeSelectedByUser;
	}
	public String getResponseFromOnfido() {
		return responseFromOnfido;
	}
	public void setResponseFromOnfido(String responseFromOnfido) {
		this.responseFromOnfido = responseFromOnfido;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}	
}
