package com.adminremit.terrapay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxInitiationRequest extends TrxQuotationRequest {
	
	protected String descriptionText;
	protected String fynteRefernceNumber;
	
	//Sender KYC details start
	protected String remitterNationality;
	
	//@NotNull @Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="remitterDateOfBirth needs to be in YYYY-MM-DD format.")  
	protected String remitterDateOfBirth;
	
	//@Pattern(regexp = "M|F|O", flags = Pattern.Flag.CASE_INSENSITIVE)
	protected String remitterGender;
	
	//@NotNull @Size(min=1, max=20) @Pattern(regexp = "nationalidcard|drivinglicense|passport", flags = Pattern.Flag.CASE_INSENSITIVE)
	protected String remitterIdType;
	
	//@NotNull @Size(min=1, max=30)
	protected String remitterIDNumber;
	
	//@Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="remitterIDIssueDate needs to be in YYYY-MM-DD format.")
	protected String remitterIdIssueDate;
	
	//@NotNull @Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="remitterIDIssueDate needs to be in YYYY-MM-DD format.")
	protected String remitterIDExpiryDate;
	
	//@Size(min=2, max=2)
	protected String remitterIdIssuerCountry;
	
		
	//Sender KYC details end
	
	//Sender KYC senderKyc:postalAddress start
	//@NotNull @Size(min=4, max=20)
	protected String remitterAddressLine1;
	protected String remitterAddressLine2;
	protected String remitterAddressLine3;
	//@NotNull @Size(min=4, max=20)
	protected String remitterCity;
	protected String remitterStateProvince;
	protected String remitterPostalCode;
   // "country": "FR" --- Remitter nationality
	
	//Sender KYC senderKyc:postalAddress end
	
	//Sender KYC senderKyc:subjectName start
	protected String remitterTitle;
	//@NotNull @Size(min=1, max=20)
	protected String remitterFirstName;
	protected String remitterMiddleName;
	protected String remitterLastName;
	
	//Sender KYC senderKyc:subjectName end
	
	
	
	//recipientKyc details start
		protected String recipientNationality;
		
		//@Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="recipientDateOfBirth needs to be in YYYY-MM-DD format.")  
		protected String recipientDateOfBirth;
		
		//@Pattern(regexp = "nationalidcard|drivinglicense|passport", flags = Pattern.Flag.CASE_INSENSITIVE)
		protected String recipientIdType;
		
		//@Size(min=1, max=30)
		protected String recipientIDNumber;
		
		//@Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="recipientIDIssueDate needs to be in YYYY-MM-DD format.")
		protected String recipientIdIssueDate;
		
		//@Pattern(regexp="^(\\\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$",message="recipientIDIssueDate needs to be in YYYY-MM-DD format.")
		protected String recipientIDExpiryDate;
		
		protected String recipientIdIssuerCountry;		
			
		//recipientKyc details end
		
		//recipientKyc:postalAddress start
		protected String recipientAddressLine1;
		protected String recipientAddressLine2;
		protected String recipientAddressLine3;
		protected String recipientCity;
		protected String recipientStateProvince;
		protected String recipientPostalCode;
		//recipientKyc:postalAddress end
		
		//Sender KYC recipientKyc:subjectName start
		//@NotNull @Size(min=1, max=20)
		protected String recipientFirstName;
		//@NotNull @Size(min=1, max=20)
		protected String recipientLastName;
		//recipientKyc:subjectName end
		
		//internationalTransferInformation start
		
		//@NotNull @Size(min=4, max=30)
		protected String remittancePurpose;
		
		//@NotNull @Size(min=4, max=17)
		protected String sourceOfFunds;
		
		//@NotNull @Size(min=3, max=11)
		protected String relationshipSender;
		//internationalTransferInformation end
	
		
	
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
