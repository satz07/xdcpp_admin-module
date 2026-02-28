package com.adminremit.backoffice.repository;

import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.model.UserCalculatorMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StagingSearchRepository extends JpaRepository<UserCalculatorMapping, Long>, JpaSpecificationExecutor<UserCalculatorMapping> {


}

