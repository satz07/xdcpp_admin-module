package com.adminremit.operations.repository;

import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.personaldetails.model.PersonalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionWorkflowRepository extends JpaRepository<TransactionWorkflow, Long> {

   // TransactionWorkflow findFirst1ByOrderByCreateAtDescAnd();

	List<TransactionWorkflow> findByReferenceNoAndWorkflowStatusAndIsCompleted(String refNo, WorkflowStatus workflowStatus,boolean isCompleted);

    List<TransactionWorkflow> findAllByWorkflowStatusAndIsCompleted(WorkflowStatus workflowStatus, boolean isCompleted);
    
    List<TransactionWorkflow> findAllByWorkflowStatusAndIsCompletedAndIsGuarantedRateApplicable(WorkflowStatus workflowStatus, boolean isCompleted, boolean isGuarantedRateApplicable);
    
    @Query(value = "select * from transaction_worflow where workflow_state='STAGING_START' and is_completed=false and is_guaranted_rate_applicable=true and create_at >= :startDate and create_at < :endDate", nativeQuery = true)
	List<TransactionWorkflow> findAllStagingTransactions(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    TransactionWorkflow findByReferenceNoAndWorkflowStatusAndIsGuarantedRateApplicable(String refNo, WorkflowStatus workflowStatus,boolean isGuarantedApplicable);
    
    TransactionWorkflow findByReferenceNo(String referenceNo);
    
    List<TransactionWorkflow> findAllByReferenceNo(String referenceNo);
    
    TransactionWorkflow findByReferenceNoAndWorkflowStatus(String refNo, WorkflowStatus workflowStatus);
    
    @Query(value = "select * from transaction_worflow where workflow_state in ('CANCELLED', 'CANCELLED_BY_USER') and reference_id in(:referenceIdList)", nativeQuery = true)
	List<TransactionWorkflow> findAllCancelledTransactions(@Param("referenceIdList") List<String> referenceIdList);
    
    public TransactionWorkflow findTopByReferenceNoInAndWorkflowStatusOrderByIdDesc(List<String> referenceIdList, WorkflowStatus workflowStatus);
    
    public TransactionWorkflow findTopByReferenceNoOrderByIdDesc(String refNo);

    @Query(value = "select * from transaction_worflow where workflow_state='DISBURSEMENT_START' and reference_id = :referenceId", nativeQuery = true)
    public TransactionWorkflow getTransactionCreatedDate(@Param("referenceId") String refId);

    @Query(value = "select * from transaction_worflow where workflow_state='COMPLETED' and reference_id = :referenceId", nativeQuery = true)
    public TransactionWorkflow getTransactionCompletedDate(@Param("referenceId") String refId);
    
}
