package com.adminremit.backoffice.service;

import java.util.Map;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.backoffice.dto.RemittanceDTO;
import com.adminremit.backoffice.dto.UPIValidationResponseDTO;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;

public interface StartRemitterService {
    public RemittanceDTO createRemittanceObject(String uid);
    public BeneficiaryNameCheck checkBeneficiaryName(String uid) throws Exception;
    public Map<String,String> getStatus(RemittResponseDTO remittResponseDTO) throws Exception;
    public boolean isBeneficiaryNameMatched(String uid);
    public UPIValidationResponseDTO getUPIValidationDetails(String upiId) throws Exception;
    public String startRemitApi(RemittanceDTO remittanceDTO) throws Exception;
    public String getRemitStatusApi(RemittResponseDTO remittResponseDTO) throws Exception;
    public String startUPIApi(RemittanceDTO remittanceDTO) throws Exception;
    public String StartUPITransaction(String uid) throws Exception;
}
