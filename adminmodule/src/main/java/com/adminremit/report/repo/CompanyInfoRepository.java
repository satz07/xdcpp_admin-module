package com.adminremit.report.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.CompanyInfo;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {
	public CompanyInfo findOneByBusinessId(Long businessId);
	public CompanyInfo findOneById(Long id);
}