package com.adminremit.masters.dto;


import java.util.List;

import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;

import lombok.Data;

@Data 
public class CurrencyApiResponse {
	
	
	    private Long id;
	    private String currencyName;
	    private String currencyCode;
	    private String countryCode;
	    private String countryName;
	    private String currencyDescription;
	    private String sendCurrencyName;
	    private String receiveCountryName;
	    private String sendCurrencyCode;
	    private String receiveCurrencyCode;
	    private Long receiveCurrencyId;
	    private Long sendCurrencyId;
	    private String paymentCodeMaster;    
	    private String paymentModeType;
	    private String paymentCode;
	    private Long paymentCodeId;
	    private String paymentCodedescription;
	    private String receiveCode;
	    private Long receiveCodeId;
	    private String description;
	    private String receiveCodeCodedescription;
	    private Long TatId;
	    private String sendCurrencyDescription;
	    private String receiveCurrencyDescription;
	    
		
	    

}
