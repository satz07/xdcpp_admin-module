package com.adminremit.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adminremit.beneficiary.model.BeneficiaryAccount;

public interface BeneficiaryAccountRepository extends JpaRepository<BeneficiaryAccount, Long> {

	 @Query(value = "SELECT * FROM beneficiary_account_details WHERE id=:id", nativeQuery = true)
	   public BeneficiaryAccount getBeneficiaryAccountDetails(@Param("id") Long id);
	 
	 @Query(value = "SELECT * FROM beneficiary_account_details WHERE account_number=:accountNumber and ifsc_code=:ifscCode and beneficiary_user not in (:beneficiaryId)", nativeQuery = true)
	 public List<BeneficiaryAccount> getBeneficiaryAccountOtherThanGivenBeneficiaryId(String accountNumber, String ifscCode, long beneficiaryId);
	 
	 @Query("SELECT b FROM BeneficiaryAccount b WHERE b.beneficiaryUser.id = :id")
	 BeneficiaryAccount getByBeneficiaryUser(@Param(value="id") Long id);
	
}
