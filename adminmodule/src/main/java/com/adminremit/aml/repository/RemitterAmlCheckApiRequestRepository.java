package com.adminremit.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.aml.model.RemitterAmlCheckRequest;

@Repository
public interface RemitterAmlCheckApiRequestRepository extends JpaRepository<RemitterAmlCheckRequest, Long> {

}
