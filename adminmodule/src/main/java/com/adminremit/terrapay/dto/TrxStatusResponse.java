package com.adminremit.terrapay.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxStatusResponse extends BaseRespone {
	
	/*
	 * Response Code	Response Message	Contact TerraPay	Transaction Action	Remarks/Comments
3000	Remit Success	No	No	Transaction is in success state.
3050	Remit Acknowledged, status PENDING	No	Poll	Partner needs to poll status API till transaction status changes to success/failed.
3001	Transaction request should be current date..	No	Retry	Transaction request should be current date.
3002	Beneficiary Validation failed	No	Cancel	Beneficiary validation is in failure state for which remit request is initiated.
3003	TerraPay transaction id is invalid	No	Retry	Retry transaction with valid TerraPay transaction id.
3004	Duplicate transaction Id	No	Cancel	Send transaction with unique transaction/reference id.
3005	Quote and Remit parameters do not match	No	Retry	Send the parameters as per API specification.
3006	Sender KYC validation failed	No	Cancel	Transaction is failed.
3007	Beneficiary KYC validation failed	No	Cancel	Transaction is failed.
3008	Quote expired	No	Cancel	Initiate a new a quote request and send new quote reference with remit request.
3009	Failed to process quote request	Yes	Retry	Operation team needs to check.
3010	Mandatory KYC parameter check failed	No	Retry	Check for sender's KYC parameters in remit request.
3011	Invalid Fx Rate	Yes	Retry	Check with TerraPay operations on the Fx rate configuration.
3022	Corridor validation failed	Yes	Retry	Operation team needs to check the corr configuration.
3027	Server is busy. Please try after sometime	Yes	Retry	Check with TerraPay technical team before retry.
3030	Possible duplicate transaction received within configured time.	Yes	Retry	Wait for sometime and send the transaction again.
3031	Connection timeout while connecting to destination partner	Yes	Retry	Wait for sometime and send the transaction again.
3032	Remit failed	Yes	Cancel	Operation team needs to check logs and update.
3049	Remit Failed. Insufficient funds	No	Retry	Check your balance at TerraPay and retry after balance is funded.
3060	Beneficiary daily transaction count limit reached	No	Cancel	Beneficiary has reached the daily transaction count.
3061	Beneficiary weekly transaction count limit reached	No	Cancel	Beneficiary has reached the weekly transaction count.
3062	Beneficiary monthly transaction count limit reached	No	Cancel	Beneficiary has reached the monthly transaction count.
3072	Receiver Daily Transaction Limit Reached	No	Cancel	Beneficiary daily transaction limit has reached.
3075	Sending Partner Min allowed amount check failed.	No	Cancel	Sending partner is sending less than minimum confiured transaction amount.
3076	Sending Partner Max allowed amount check failed.	No	Cancel	Sending partner is sending more than maximum configured transaction amount.
3077	Receiving Partner Min allowed amount check failed.	No	Cancel	Receiving partner is receiving less than minimum confiured transaction amount.
3078	Receiving Partner Max allowed amount check failed.	No	Cancel	Receiving partner is receiving more than maximum confiured transaction amount.
3079	Sender Min allowed amount check failed.	No	Cancel	Sender is sending less than minimum confiured transaction amount.
3080	Sender Max allowed amount check failed.	No	Cancel	Sender is sending more than maximum confiured transaction amount.
3081	Receiver Min allowed amount check failed.	No	Cancel	Beneficiary is receiving less than minimum confiured transaction amount.
3082	Receiver Max allowed amount check failed.	No	Cancel	Beneficiary is receiving more than maximum confiured transaction amount.
3100	Credit Failed. Msisdn not found.	No	Cancel	Transaction has failed due to invalid wallet account.
3101	Bank Credit Failed. Invalid Account.	No	Cancel	Transaction has failed by bank due to invalid bank account.
3102	Bank Credit Failed. Bank Not Reachable.	No	Cancel	Transaction failed by destination partner as bank is not reachable.
3103	Bank credit failed. Account name mismatch.	No	Cancel	Transaction failed by bank due to account name mismatch.
3104	Bank credit failed. Transaction limit exceeded.	No	Cancel	Transaction failed by bank as transaction limit exceeded.
3105	Bank credit failed. Transaction not permitted.	No	Cancel	Transaction failed by bank as transaction is permitted.
3106	Bank credit failed. Unknown Error.	Yes	Retry/Cancel	As the transaction failed by bank, please check with operations team either transaction can be retried or not.
3107	Invalid Amount Limit.	No	Cancel	Transaction failed due to max limit per transaction.
3108	Invalid Digital Signature.	Yes	Cancel	Transaction has failed due to account not registed.
3109	Insufficient funds in the receiving partner account.	Yes	Cancel	Operation team needs to check the prefunding balance.
3110	Invalid Beneficiary Account	Yes	Cancel	Transaction has failed due to invalid account.
3111	Beneficiary Account not Registered	Yes	Cancel	Transaction has failed due to account not registed.
3112	Duplicate VendorId	No	Cancel	Send the correct vendorID.
3113	Beneficiary Account Limit Reached.	No	Cancel	Transaction failed due to transaction limit exceeded.
3114	Beneficiary Account Barred.	No	Cancel	Transaction failed as account is Barred.
3115	Beneficiary Account Inactive	No	Cancel	Transaction failed as account is inactive.
3116	Beneficiary Account Locked	No	Cancel	Transaction failed as account is locked.
3117	Transfer type not supported	No	Cancel	Please check th transfer type and retry the transaction with the correct Transfer type
3132	Remit Failed - Max retry limit reached.	No	Cancel	Transaction failed as transaction limit exceeded.
3222	Invalid UPI amount.	No	Cancel	The provided amount is not acceptable for UPI transaction.
	 */
	
	protected String terraPayReferenceNumber;
	protected String fynteReferenceNumber;

		
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
