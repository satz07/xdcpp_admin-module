package com.adminremit.user.aml;

import java.util.Map;

public interface KYCCheckService {
	public Map<String,Object> amlCheck(String uid, Long personalId);    
    public Map<String,Object> amlCheckForBeneficiary(Long beneficiaryId, String uid);
}
