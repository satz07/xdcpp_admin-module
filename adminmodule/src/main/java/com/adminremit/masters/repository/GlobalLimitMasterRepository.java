package com.adminremit.masters.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalLimitMasterRepository extends JpaRepository<GlobalLimitMaster,Long> {
    public List<GlobalLimitMaster> findAllByPublish(Boolean publish);
    public List<GlobalLimitMaster> findAll();
    public List<GlobalLimitMaster> findAllByIsDeleted(Boolean isDeleted);
    public List<GlobalLimitMaster> findAllByIsDeletedAndReceiveCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<GlobalLimitMaster> findAllByIsDeletedAndPaymentCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<GlobalLimitMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode, Currencies sendCurrencyCode, Currencies receiveCurrencyCode, Countries sendCountryCode, Countries receiveCountryCode);
    
    @Query("SELECT u FROM GlobalLimitMaster u")
    List<GlobalLimitMaster> get();
	public List<GlobalLimitMaster> findAllByIsDeletedAndPublishTrue(boolean b);
	public List<GlobalLimitMaster> findAllByIsDeletedAndPaymentCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
	public List<GlobalLimitMaster> findAllByIsDeletedAndReceiveCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
}
