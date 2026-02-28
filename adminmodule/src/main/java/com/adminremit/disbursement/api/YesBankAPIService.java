package com.adminremit.disbursement.api;

import java.rmi.RemoteException;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.backoffice.dto.RemittanceDTO;

public interface YesBankAPIService {
	public String remitTransaction(RemittanceDTO remittanceDTO) throws Exception;
	public String benificaryNameCheck(RemittanceDTO remittanceDTO) throws RemoteException;
	public String transactionResponse(RemittResponseDTO remittResponseDTO) throws Exception;
	public String getTransactionStatus(RemittResponseDTO remittResponseDTO);
	public String startUPITransaction(RemittanceDTO remittanceDTO) throws Exception;
	public String getStatus(String referenceId) throws Exception;
	public String getBalance() throws Exception;
	public String getUPIDetails(String upiId) throws Exception;
	public String validateUPI(String upiId) throws Exception;

}
