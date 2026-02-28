package com.adminremit.terrapay.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxQuotationRequest extends ValidateAccountRequest {
	
	protected Double requestAmount;

	protected String remitterMobile;
	
		
	protected String sendingCurrency;
	
	protected String receivingCurrency;
	
	
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
