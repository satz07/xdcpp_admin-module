package com.adminremit.beneficiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adminremit.beneficiary.model.BeneficiaryUser;

public interface BeneficiaryUserRepository extends JpaRepository<BeneficiaryUser,Long> {
	
	@Query(value = "SELECT count(*) FROM beneficiary_user WHERE user_id=:id", nativeQuery = true)
	public long getCountOfBeneficiaryForUser(@Param("id") Long id);
}
