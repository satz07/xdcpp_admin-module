package com.adminremit.terrapay.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAccountRequest extends BaseRequest {
	
	//Beneficiary account number,mandatory for Bank Account
	protected String accountId;
	
	//Beneficiary mobile number,mandatory for Wallet
	protected String recipientMobile;

	//@NotNull
	//Full KYC name of the beneficiary as registered with the wallet provider/Bank.
	protected String recipientFullName;

	//Full name of the sender as per KYC Id document.
	protected String remitterFullName;
		
	
	/*
	 * This is a code that indicates the bank to which the transaction is to be sent.
	This field is conditional. If not set, then TerraPay will resolve the bank based on the bankCode. If the bankCode is incorrectly provided, then the bank will be resolved based on Bank Name (should match exactly as per the bank list shared by TerraPay). If these parameters do no match then the transaction will be rejected.
	If set, then TerraPay will resolve the bank based on the provider code.
	NOTE:
	* Mandatory for bank transactions to Bangladesh & China.
	* Mandatory provider code for China Union Pay is CNUNIONPAY.
	* Mandatory provider code for Philippines Direct to bank transfer is PH_DIRECT_TO_BANK.
	* Optional for other countries.
	 */
	protected String provider;
	
	protected String bankCode;

	//Beneficiary bank full name,mandatory for Bank Account
	protected String bankName;

	//@NotNull
	protected String receivingCountry;
	
	/*
	 * This is a code that indicates the branch code of the specific bank to which the transaction is to be sent.
		NOTE:
		* Mandatory for bank transactions to Brazil & Uruguay.
		* If the transaction is to Bangladesh then partner can send rounting number in banksubcode and they do not have to send the provider code.
		* Optional for other countries.
	 */
	protected String bankSubCode;
	
	

	
	
	@Override
	public String toString() {
		ObjectMapper obj = new ObjectMapper();
		String json = super.toString();
        try {
			json = obj.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
 	}


}
