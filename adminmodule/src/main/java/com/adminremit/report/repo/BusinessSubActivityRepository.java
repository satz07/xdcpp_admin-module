package com.adminremit.report.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.BusinessSubActivity;

@Repository
public interface BusinessSubActivityRepository extends JpaRepository<BusinessSubActivity, Long> {
	public BusinessSubActivity findOneById(Long id);	
}