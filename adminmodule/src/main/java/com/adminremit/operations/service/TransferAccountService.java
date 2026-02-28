package com.adminremit.operations.service;

import com.adminremit.operations.model.TransferAccountDetails;

import java.util.List;

public interface TransferAccountService {

    TransferAccountDetails save(TransferAccountDetails transferAccountDetails);
    public List<TransferAccountDetails> getAllTransferAccount();
    public List<TransferAccountDetails> getTransferAccountDetails(String refId);
}
