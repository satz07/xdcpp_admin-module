package com.adminremit.masters.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceChargeMasterRepository extends JpaRepository<ServiceChargeMaster,Long> {
    public List<ServiceChargeMaster> findAllByPublish(boolean publish);
    public List<ServiceChargeMaster> findAll();
    public List<ServiceChargeMaster> findAllByIsDeleted(Boolean isDeleted);
    public List<ServiceChargeMaster> findAllByIsDeletedAndReceiveCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<ServiceChargeMaster> findAllByIsDeletedAndPaymentCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<ServiceChargeMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode, Currencies sendCurrencyCode, Currencies receiveCurrencyCode, Countries sendCountryCode, Countries receiveCountryCode);
	public List<ServiceChargeMaster> findAllByIsDeletedAndPublishTrue(boolean b);
	public List<ServiceChargeMaster> findAllByIsDeletedAndPaymentCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
	public List<ServiceChargeMaster> findAllByIsDeletedAndReceiveCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
}
