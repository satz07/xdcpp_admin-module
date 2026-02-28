package com.adminremit.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.report.model.business.OwnershipDetails;

@Repository
public interface OwnershipDetailsRepository extends JpaRepository<OwnershipDetails, Long> {
	public List<OwnershipDetails> findByUserId(Long userId);
}
