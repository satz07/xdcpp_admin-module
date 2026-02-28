package com.adminremit.report.compliance.dto;

public class RegistrationCountry {
	
	private String userSelectedCountry;
	private String registrationIpBasedCountry;
	
	public String getUserSelectedCountry() {
		return userSelectedCountry;
	}
	public void setUserSelectedCountry(String userSelectedCountry) {
		this.userSelectedCountry = userSelectedCountry;
	}
	public String getRegistrationIpBasedCountry() {
		return registrationIpBasedCountry;
	}
	public void setRegistrationIpBasedCountry(String registrationIpBasedCountry) {
		this.registrationIpBasedCountry = registrationIpBasedCountry;
	}
}
