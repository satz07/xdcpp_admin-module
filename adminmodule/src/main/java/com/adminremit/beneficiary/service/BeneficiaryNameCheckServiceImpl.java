package com.adminremit.beneficiary.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.beneficiary.repository.BeneficiaryNameCheckRepository;
import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.repository.TransferAccountRepository;
import com.adminremit.operations.service.FilesStorageServiceImpl;
import com.adminremit.operations.service.TransferAccountServiceImpl;
import com.github.javaparser.utils.Log;

@Service
public class BeneficiaryNameCheckServiceImpl implements BeneficiaryNameCheckService {

	@Autowired
    private BeneficiaryNameCheckRepository beneficiaryNameCheckRepository;
	@Autowired
	private TransferAccountRepository transferAccountRepository;
	@Autowired
	private FilesStorageServiceImpl filesStorageServiceImpl;

    @Override
    public BeneficiaryNameCheck save(BeneficiaryNameCheck beneficiaryNameCheck) {
        return beneficiaryNameCheckRepository.save(beneficiaryNameCheck);
    }

	@Override
	public List<BeneficiaryNameCheck> findByDisbursementStatusAndPaymentType(YesBankAPITransactionStatus status, PaymentType paymentType) {
		/**
		 * Here the check for the Account; if already present in the transfer account Details table 
		 * then no penny drop status update should happen 
		 */
		
		OperationFileUpload operationFileUpload = filesStorageServiceImpl.isAccountAlreadyPresent();
		List <TransferAccountDetails> accounts = transferAccountRepository.findAccountIfAlreadyPresent(operationFileUpload.getBankName(), 
				operationFileUpload.getCreatedBy(),new BigDecimal(operationFileUpload.getAccountNumber()));
		
		System.out.println("check is implemented for the account present in the transfer account");
		return (accounts.size()>1) ? null: beneficiaryNameCheckRepository.findByStatus(status, paymentType);
	}
}
