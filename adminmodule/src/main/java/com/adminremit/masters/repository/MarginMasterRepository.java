package com.adminremit.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;

@Repository
public interface MarginMasterRepository extends JpaRepository<MarginMaster,Long> {
    public List<MarginMaster> findAllByPublish(boolean publish);
    public List<MarginMaster> findAll();
    public List<MarginMaster> findAllByUserType(UserType userType);
    public List<MarginMaster> findAllBySendCurrencyCode(Currencies sendCurrencyCode);
    public List<MarginMaster> findAllByReceiveCurrencyCode(Currencies receiveCurrencyCode);
    public List<MarginMaster> findAllByPaymentCode(PaymentCodeMaster paymentCode);
    public List<MarginMaster> findAllByIsDeleted(Boolean isDeleted);
    public List<MarginMaster> findAllByIsDeletedAndReceiveCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<MarginMaster> findAllByIsDeletedAndPaymentCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<MarginMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode, Currencies sendCurrencyCode, Currencies receiveCurrencyCode, Countries sendCountryCode, Countries receiveCountryCode);
	public List<MarginMaster> findAllByIsDeletedAndPublishTrue(boolean b);
	public List<MarginMaster> findAllByIsDeletedAndPaymentCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
	public List<MarginMaster> findAllByIsDeletedAndReceiveCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
}
