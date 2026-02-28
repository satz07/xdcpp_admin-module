package com.adminremit.beneficiary.service;

import java.util.List;

import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;

public interface BeneficiaryNameCheckService {
	public BeneficiaryNameCheck save(BeneficiaryNameCheck beneficiaryNameCheck);
	public List<BeneficiaryNameCheck> findByDisbursementStatusAndPaymentType(YesBankAPITransactionStatus status, PaymentType paymentType);
}
