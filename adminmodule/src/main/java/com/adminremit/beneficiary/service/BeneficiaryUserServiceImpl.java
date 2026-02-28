package com.adminremit.beneficiary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.repository.BeneficiaryUserRepository;

@Service
public class BeneficiaryUserServiceImpl implements BeneficiaryUserService {
	
	@Autowired
    private BeneficiaryUserRepository beneficiaryUserRepository;

	@Override
	public BeneficiaryUser getBeneficiary(Long id) {
		BeneficiaryUser beneficiaryUser = null;
        try {
            Optional<BeneficiaryUser> beneficiaryUser1 = beneficiaryUserRepository.findById(id);
            if (beneficiaryUser1.isPresent()) {
                beneficiaryUser = beneficiaryUser1.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beneficiaryUser;
	}
}
