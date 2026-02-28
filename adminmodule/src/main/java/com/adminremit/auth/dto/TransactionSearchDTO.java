package com.adminremit.auth.dto;

public class TransactionSearchDTO {
	private String partnerId;
	private String from;
	private String to;
	private String trnsno;
	private String email;
	private String status;	
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTrnsno() {
		return trnsno;
	}
	public void setTrnsno(String trnsno) {
		this.trnsno = trnsno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
