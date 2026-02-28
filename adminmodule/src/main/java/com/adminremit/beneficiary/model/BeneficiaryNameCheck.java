package com.adminremit.beneficiary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.TransferType;
import com.adminremit.backoffice.model.YesBankAPINames;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;

@Entity
@Table(name = "beneficiary_name_check")
public class BeneficiaryNameCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "unique_request_no")
    private String uniqueRequestNo;

    @Column(name = "unique_response_no")
    private String uniqueResponseNo;

    @Column(name = "uid")
    private String uid;

    @Column(name = "beneficiary_id")
    private Long beneficiaryId;

    @Column(name = "name_in_response")
    private String nameInResponse;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private YesBankAPITransactionStatus yesBankAPITransactionStatus;

    @Column(name = "api_name")
    @Enumerated(EnumType.STRING)
    private YesBankAPINames yesBankAPINames;

    @Column(name = "transaction_method")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Column(name = "attempt_no")
    private String attemptNo;
    
    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueRequestNo() {
        return uniqueRequestNo;
    }

    public void setUniqueRequestNo(String uniqueRequestNo) {
        this.uniqueRequestNo = uniqueRequestNo;
    }

    public String getUniqueResponseNo() {
        return uniqueResponseNo;
    }

    public void setUniqueResponseNo(String uniqueResponseNo) {
        this.uniqueResponseNo = uniqueResponseNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getNameInResponse() {
        return nameInResponse;
    }

    public void setNameInResponse(String nameInResponse) {
        this.nameInResponse = nameInResponse;
    }

    public YesBankAPITransactionStatus getYesBankAPITransactionStatus() {
        return yesBankAPITransactionStatus;
    }

    public void setYesBankAPITransactionStatus(YesBankAPITransactionStatus yesBankAPITransactionStatus) {
        this.yesBankAPITransactionStatus = yesBankAPITransactionStatus;
    }

    public YesBankAPINames getYesBankAPINames() {
        return yesBankAPINames;
    }

    public void setYesBankAPINames(YesBankAPINames yesBankAPINames) {
        this.yesBankAPINames = yesBankAPINames;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public String getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(String attemptNo) {
        this.attemptNo = attemptNo;
    }

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
}
