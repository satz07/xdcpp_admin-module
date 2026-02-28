package com.adminremit.beneficiary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.auth.repository.BeneficiaryAccountRepository;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;

@Service
public class BeneficiaryAccountServiceImpl implements BeneficiaryAccountService {

    @Autowired
    private BeneficiaryAccountRepository beneficiaryAccountRepository;

    
    @Override
    public BeneficiaryAccount getById(Long id) {
        BeneficiaryAccount beneficiaryAccount = null;
        Optional<BeneficiaryAccount> beneficiaryAccountOptional =  beneficiaryAccountRepository.findById(id);
        if(beneficiaryAccountOptional.isPresent()) {
            beneficiaryAccount = beneficiaryAccountOptional.get();
        }
        return  beneficiaryAccount;
    }

    @Override
    public BeneficiaryAccount update(BeneficiaryAccount beneficiaryAccount) {
        return beneficiaryAccountRepository.save(beneficiaryAccount);
    }
    
    @Override
	public BeneficiaryAccount getByBeneficiaryUser(BeneficiaryUser beneficiaryUser) {
		if(beneficiaryUser!=null) {
			return beneficiaryAccountRepository.getByBeneficiaryUser(beneficiaryUser.getId());
		}
		
		return null;	
	}
}
