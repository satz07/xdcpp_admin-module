package com.adminremit.operations.service;

import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.operations.dto.FileUploadDTO;
import com.adminremit.operations.model.BankAccountDetails;
import com.adminremit.masters.models.BankMaster;
import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Service
public class BankDetailsServiceImpl implements BankDetailsService{

    private static final Logger LOG = LoggerFactory.getLogger(BankDetailsServiceImpl.class);

    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;


    @Override
    public List<BankMaster> getAllBankDetails() {
        return bankMasterRepository.findAll();
    }

    @Override
    public List<BankMaster> getBankDetailsById(Long id) {
        return bankMasterRepository.findAllById(id);
    }

    @Override
    public List<BankAccountDetails> getAllBankDetails(Long bankId) {
        return bankAccountRepository.findAllByBankId(bankId);
    }



}
