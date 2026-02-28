
package com.adminremit.beneficiary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.TransferType;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;

@Repository
public interface BeneficiaryNameCheckRepository extends JpaRepository<BeneficiaryNameCheck, Long> {
	
	@Query(value = "SELECT b FROM BeneficiaryNameCheck b WHERE b.yesBankAPITransactionStatus = :status and b.paymentType =:paymentType")
	public List<BeneficiaryNameCheck> findByStatus(@Param("status") YesBankAPITransactionStatus status, @Param("paymentType") PaymentType paymentType);
	
	public BeneficiaryNameCheck findByUidAndBeneficiaryId(String uid, long beneficiaryId);
	
	public BeneficiaryNameCheck findByBeneficiaryIdAndUidAndPaymentType(long beneficiaryId, String uid, PaymentType paymentType);
	
	public BeneficiaryNameCheck findByBeneficiaryIdAndUidAndPaymentTypeAndTransferType(long beneficiaryId, String uid, PaymentType paymentType, TransferType transferType);
}

