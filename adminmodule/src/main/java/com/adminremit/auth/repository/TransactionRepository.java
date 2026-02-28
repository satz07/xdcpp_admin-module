package com.adminremit.auth.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.personaldetails.model.PersonalDetails;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionWorkflow, Long>  {
	
	   public List<TransactionWorkflow> findAll();
	
	   
	   @Query(value = "SELECT * FROM transaction_worflow WHERE is_completed=false order by update_at desc limit 20", nativeQuery = true)
	   public List<TransactionWorkflow> findByIsCompleted();
	
	   @Query(value = "SELECT * FROM transaction_worflow WHERE reference_id=:trns_no and is_completed=true order by update_at desc limit 1", nativeQuery = true)
	   public TransactionWorkflow getPaginatedTransactionSummary(@Param("trns_no") String trns_no);
	
	   public List<TransactionWorkflow> findByReferenceNo(String referenceNo);
	   
	   public TransactionWorkflow findTopByReferenceNoOrderByIdDesc(String referenceNo);
	   
	   public TransactionWorkflow findByReferenceNoAndWorkflowStatus(String refNo, WorkflowStatus workflowStatus);
	   
}
