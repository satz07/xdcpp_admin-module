package com.adminremit.beneficiary.service;

import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;

public interface BeneficiaryAccountService {
    public BeneficiaryAccount getById(Long id);
    public BeneficiaryAccount update(BeneficiaryAccount beneficiaryAccount);
    public BeneficiaryAccount getByBeneficiaryUser(BeneficiaryUser beneficiaryUser);
}
