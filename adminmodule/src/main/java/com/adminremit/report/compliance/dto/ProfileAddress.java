package com.adminremit.report.compliance.dto;

public class ProfileAddress {
	
	private String addressByUser;
	private String suburbByUser;
	private String stateByUser;
	private String postalCodeByUser;
	private String countryByUser;

	public String getAddressByUser() {
		return addressByUser;
	}

	public void setAddressByUser(String addressByUser) {
		this.addressByUser = addressByUser;
	}

	public String getSuburbByUser() {
		return suburbByUser;
	}

	public void setSuburbByUser(String suburbByUser) {
		this.suburbByUser = suburbByUser;
	}

	public String getStateByUser() {
		return stateByUser;
	}

	public void setStateByUser(String stateByUser) {
		this.stateByUser = stateByUser;
	}

	public String getPostalCodeByUser() {
		return postalCodeByUser;
	}

	public void setPostalCodeByUser(String postalCodeByUser) {
		this.postalCodeByUser = postalCodeByUser;
	}

	public String getCountryByUser() {
		return countryByUser;
	}

	public void setCountryByUser(String countryByUser) {
		this.countryByUser = countryByUser;
	}	
}
