package com.adminremit.backoffice.service;

import java.util.List;
import java.util.Map;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.operations.model.UserCalculatorMapping;

public interface DisbursementService {
	
	public List<UserCalculatorMapping> getAllDisbursementMakerList();	
	public List<UserCalculatorMapping> getAllDisbursementCheckerList();
	public BeneficiaryNameCheck startDisbursement(String uid, UserCalculatorMapping userCalcMapping, String transferType) throws Exception;
	public Map<String,String> getStatus(RemittResponseDTO remittResponseDTO) throws Exception;
	public void checkAndUpdateDisbursementStatus() throws Exception;
	public void checkAndCompleteDisbursementStatus() throws Exception;
	public void checkAndUpdatepennyDropStatus() throws Exception;
	public void checkAndSendReminder() throws Exception;
	public String getTransactionStatus(String referenceId) throws Exception;
	public String getBalance() throws Exception;
	
}
