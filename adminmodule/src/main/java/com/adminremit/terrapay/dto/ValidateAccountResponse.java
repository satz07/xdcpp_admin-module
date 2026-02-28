package com.adminremit.terrapay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidateAccountResponse extends BaseRespone {

		
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
