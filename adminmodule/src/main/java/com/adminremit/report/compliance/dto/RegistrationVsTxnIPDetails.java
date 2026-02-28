package com.adminremit.report.compliance.dto;

public class RegistrationVsTxnIPDetails {
	
	private String registrationIpLocation;
	private String txnIpLocation;
	
	public String getRegistrationIpLocation() {
		return registrationIpLocation;
	}
	public void setRegistrationIpLocation(String registrationIpLocation) {
		this.registrationIpLocation = registrationIpLocation;
	}
	public String getTxnIpLocation() {
		return txnIpLocation;
	}
	public void setTxnIpLocation(String txnIpLocation) {
		this.txnIpLocation = txnIpLocation;
	}
}
