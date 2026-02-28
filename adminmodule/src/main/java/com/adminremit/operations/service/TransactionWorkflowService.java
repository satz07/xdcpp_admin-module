package com.adminremit.operations.service;

import com.adminremit.operations.model.*;

import java.util.Date;
import java.util.List;

public interface TransactionWorkflowService {

    public TransactionWorkflow updateTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus);

    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping,WorkflowStatus workflowStatus);


    public List<TransactionWorkflow> getAllTransactionWorkflow(WorkflowStatus workflowStatus);

    public TransactionWorkflow createTransactionWorkFlowUnamatched(WorkflowStatus workflowStatus, TransferAccountDetails transferAccountDetails);

    public TransactionWorkflow updateTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, ReconStatus status);
    
    public TransactionWorkflow updateFailedTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus,String status, String subStatus, String subStatusText, Date dt);
    
    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping,WorkflowStatus workflowStatus,ReconStatus status);
    
    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, ActionStatus actionStatus);
    
    public void checkAndSetGuarantedRateApplicableFlag();
    
    public void eligibleForAutoCancelOfTxns();
    						
    public TransactionWorkflow createTransactionWorkFlowOnCompletionOfTxn(UserCalculatorMapping userCalculatorMapping,WorkflowStatus workflowStatus, String bankRefNumber, Date utrTime);

    public Date getTransactionCreatedDate(String refId);

    public Date getTransactionCompletedDate(String refId);
}
