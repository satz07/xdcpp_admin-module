package com.adminremit.terrapay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxQuotationResponse extends BaseRespone {

		
	protected String quoteId;;
	protected String quoteExpiryTime;
	protected double sendingAmount;
	protected String sendingCurrency;
	protected double receivingAmount;
	protected String receivingCurrency;
	protected String quotationReference;
	protected double fxRate;
	
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
	