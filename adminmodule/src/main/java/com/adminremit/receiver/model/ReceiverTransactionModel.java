package com.adminremit.receiver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.adminremit.operations.model.BaseEntity;

@Entity
@Table(name = "receiver_transaction_details")
public class ReceiverTransactionModel extends BaseEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name = "receiver_type_id")
    Long receiverType;

    @Column(name = "receiver_beneficiary_id")
    Long beneficiaryUser;

    @Column(name = "receiver_purpose_id")
    Long purposeList;

    @Column(name = "transaction_cal_id")
    Long transactionCalId;

    @Column(name = "user_id")
    Long userId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Long receiverType) {
        this.receiverType = receiverType;
    }

    public Long getBeneficiaryUser() {
        return beneficiaryUser;
    }

    public void setBeneficiaryUser(Long beneficiaryUser) {
        this.beneficiaryUser = beneficiaryUser;
    }

    public Long getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(Long purposeList) {
        this.purposeList = purposeList;
    }


    public Long getTransactionCalId() {
        return transactionCalId;
    }

    public void setTransactionCalId(Long transactionCalId) {
        this.transactionCalId = transactionCalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
