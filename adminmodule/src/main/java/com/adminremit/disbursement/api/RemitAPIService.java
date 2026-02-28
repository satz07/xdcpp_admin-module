package com.adminremit.disbursement.api;

import java.util.Map;

import com.adminremit.operations.model.UserCalculatorMapping;

public interface RemitAPIService {
	public Map<String, String> initiateTransaction(UserCalculatorMapping userCalculatorMapping) throws Exception;
	public Map<String, String> getStatus(String referenceId) throws Exception;
	
}
