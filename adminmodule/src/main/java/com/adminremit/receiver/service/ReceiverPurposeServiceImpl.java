package com.adminremit.receiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.receiver.model.ReceiverTransactionModel;
import com.adminremit.receiver.repository.ReceiverPurposeRepo;
import com.adminremit.receiver.repository.ReceiverTransactionalRepo;

@Service
public class ReceiverPurposeServiceImpl implements ReceiverPurposeService {
	
	@Autowired
    private ReceiverPurposeRepo receiverPurposeRepo;
	 
	@Autowired
    private ReceiverTransactionalRepo receiverTransactionalRepo;

	@Override
	public ReceiverTransactionModel getReceiverTypeByUid(Long id) {
		ReceiverTransactionModel receiverTransactionModel = receiverTransactionalRepo.findByUid(id).get();
		return receiverTransactionModel;
	}

	@Override
	public PurposeListMaster getPurposeListById(Long id) {
		PurposeListMaster receiverCategory = receiverPurposeRepo.findById(id).get();
        return receiverCategory;
	}
}
