package com.adminremit.operations.service;

import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.repository.UserCalculatorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BackofficeWorkflowServiceImpl implements BackofficeWorkflowService{

    @Autowired
    UserCalculatorRepository userCalculatorRepository;

    @Autowired
    TransactionWorkflowRepository transactionWorkflowRepository;


    @Override
    public TransactionWorkflow updateTransactionWorkflow(TransactionWorkflow transactionWorkflow) {
            return transactionWorkflowRepository.save(transactionWorkflow);

    }
}
