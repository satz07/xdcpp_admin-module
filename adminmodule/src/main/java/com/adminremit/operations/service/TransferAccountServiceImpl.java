package com.adminremit.operations.service;

import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.repository.TransferAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferAccountServiceImpl implements TransferAccountService{


    @Autowired
    TransferAccountRepository transferAccountRepository;

    @Override
    public TransferAccountDetails save(TransferAccountDetails transferAccountDetails) {
        TransferAccountDetails transferAccountDetails1 = transferAccountRepository.save(transferAccountDetails);
        return transferAccountDetails1;
    }

    @Override
    public List<TransferAccountDetails> getAllTransferAccount() {
        List<TransferAccountDetails> transferAccountDetails1 = transferAccountRepository.findAll();
        return transferAccountDetails1;
    }

    @Override
    public List<TransferAccountDetails> getTransferAccountDetails(String refId) {
        List<TransferAccountDetails> transferAccountDetails1 = transferAccountRepository.findAllBytransactionRefNo(refId);
        return transferAccountDetails1;
    }
    
    public Boolean isAccountAlreadyPresent(String bankRef,String senderName, BigDecimal accountNumber) {
    	
    	List<TransferAccountDetails> accounts = transferAccountRepository.findAccountIfAlreadyPresent(bankRef, senderName, accountNumber);
    	
    	return (accounts.size()>1)? false : true;
    }
}
