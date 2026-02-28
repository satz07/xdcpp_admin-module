package com.adminremit.receiver.service;

import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.receiver.model.ReceiverTransactionModel;

public interface ReceiverPurposeService {
	
	public ReceiverTransactionModel getReceiverTypeByUid(Long id);
	public PurposeListMaster getPurposeListById(Long id);
}
