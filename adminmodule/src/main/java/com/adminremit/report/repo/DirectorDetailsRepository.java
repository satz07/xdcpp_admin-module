package com.adminremit.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.DirectorDetails;

@Repository
public interface DirectorDetailsRepository extends JpaRepository<DirectorDetails, Long> {
	public List<DirectorDetails> findByCompanyId(Long companyId);
	public DirectorDetails findOneByCompanyId(Long id);
	public DirectorDetails findOneById(Long id);
}