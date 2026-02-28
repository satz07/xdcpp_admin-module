package com.adminremit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.user.model.EDDMaster;

@Repository
public interface EDDRepository  extends JpaRepository<EDDMaster, String> {
	
	public EDDMaster findByUserCalculatorMapping(String uid);
}
