package com.adminremit.operations.service;

import com.adminremit.operations.model.BankAccountDetails;
import com.adminremit.masters.models.BankMaster;
import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;

import java.io.IOException;
import java.util.List;

public interface BankDetailsService {



    List<BankMaster> getAllBankDetails();
    List<BankMaster> getBankDetailsById(Long id);
    public List<BankAccountDetails> getAllBankDetails(Long bankId);
}

