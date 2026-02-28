package com.adminremit.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.UserCalculatorMapping;

@Repository
public interface DisbursementRepository extends JpaRepository<UserCalculatorMapping, Long>, JpaSpecificationExecutor<UserCalculatorMapping> {


}

