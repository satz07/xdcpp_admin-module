package com.adminremit.terrapay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxInitiationResponse extends BaseRespone {

		
	protected String terraPayReference;
	
		
	public TrxInitiationResponse(ValidateAccountResponse response) {
		this.message=response.message;
		this.status=response.status;
		this.statusCode=response.statusCode;				
	}
	
	public TrxInitiationResponse(TrxQuotationResponse response) {
		this.message=response.message;
		this.status=response.status;
		this.statusCode=response.statusCode;				
	}
	
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
	