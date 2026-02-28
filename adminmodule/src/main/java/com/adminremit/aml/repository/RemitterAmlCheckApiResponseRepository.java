package com.adminremit.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.aml.model.RemitterAmlCheckResponse;

@Repository
public interface RemitterAmlCheckApiResponseRepository extends JpaRepository<RemitterAmlCheckResponse, Long> {
	
	public RemitterAmlCheckResponse findTopByUidAndUserIdOrderByIdDesc(String uid, String userId);

}
