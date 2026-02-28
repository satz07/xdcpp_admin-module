package com.adminremit.report.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.BusinessActivityVo;

@Repository
public interface BusinessActivityRepository extends JpaRepository<BusinessActivityVo, Long> {
	public BusinessActivityVo findOneById(Long id);	
}