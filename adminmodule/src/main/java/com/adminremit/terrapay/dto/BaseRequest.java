package com.adminremit.terrapay.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BaseRequest {

	@NotNull
	//2-digit origin country code like AU
	protected String originCountry;
	
	@NotNull
	//Payment Method: W- Wallet and B- Bank Account
	protected String paymentMethod;
	


}
