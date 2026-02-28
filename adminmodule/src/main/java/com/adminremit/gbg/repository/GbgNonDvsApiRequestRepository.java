package com.adminremit.gbg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.gbg.model.GbgNonDvsApiRequest;

@Repository
public interface GbgNonDvsApiRequestRepository  extends JpaRepository<GbgNonDvsApiRequest, Long>{
	
	public GbgNonDvsApiRequest findByPersonalDetailsId(long personalDetailsId);
}
