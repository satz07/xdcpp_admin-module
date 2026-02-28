package com.adminremit.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.aml.model.BeneficiaryAmlCheckResponse;

@Repository
public interface BeneficiaryAmlCheckApiResponseRepository extends JpaRepository<BeneficiaryAmlCheckResponse, Long> {
	
	public BeneficiaryAmlCheckResponse findTopByUidAndUserIdAndBeneficiaryIdOrderByIdDesc(String uid, String userId, long beneficiaryId);

}
