package com.adminremit.report.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.BusinessInfo;

@Repository
public interface BusinessInfoRepository extends JpaRepository<BusinessInfo, Long> {
	public BusinessInfo findOneByUserId(Long userId);
	public BusinessInfo findOneById(Long id);
	public BusinessInfo findOneByAbn(String abn);
	
}