package com.adminremit.operations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.JobType;
import com.adminremit.operations.model.TransactionWorkflowAgeingDetails;
import com.adminremit.operations.model.WorkflowStatus;

@Repository
public interface TransactionWorkflowAgeingRepository extends JpaRepository<TransactionWorkflowAgeingDetails, Long> {
	
	public TransactionWorkflowAgeingDetails findByReferenceNoAndJobTypeAndWorkflowStatus(String refNo, JobType jobType, WorkflowStatus workflowStatus);

}
