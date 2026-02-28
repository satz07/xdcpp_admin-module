package com.adminremit.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.aml.model.BeneficiaryAmlCheckRequest;

@Repository
public interface BeneficiaryAmlCheckApiRequestRepository extends JpaRepository<BeneficiaryAmlCheckRequest, Long> {

}
