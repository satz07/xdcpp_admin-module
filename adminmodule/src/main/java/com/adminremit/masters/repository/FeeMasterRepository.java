package com.adminremit.masters.repository;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FeeMasterRepository extends JpaRepository<FeeMaster,Long> {
    public List<FeeMaster> findAllByPublish(boolean publish);
    public List<FeeMaster> findAll();
    public List<FeeMaster> findAllByUserType(UserType userType);
    public List<FeeMaster> findAllBySendCurrencyCode(Currencies sendCurrencyCode);
    public List<FeeMaster> findAllByReceiveCurrencyCode(Currencies receiveCurrencyCode);
    public List<FeeMaster> findAllByIsDeleted(Boolean isDeleted);
    public List<FeeMaster> findAllByIsDeletedAndReceiveCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<FeeMaster> findAllByIsDeletedAndPaymentCode(Boolean isDeleted, PaymentReceiveMode paymentReceiveMode);
    public List<FeeMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCode(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode);
    public List<FeeMaster> findAllByIsDeletedAndSendCurrencyCodeAndReceiveCurrencyCode(Boolean isDeleted,Currencies sendCurrencyCode, Currencies receiveCurrencyCode);
    public List<FeeMaster> findAllByIsDeletedAndSendCountryCodeAndReceiveCountryCode(Boolean isDeleted,Countries sendCountryCode, Countries receiveCountryCode);
    public List<FeeMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCodeAndRangeFromGreaterThanEqualOrRangeToLessThanEqual(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode, Currencies sendCurrencyCode, Currencies receiveCurrencyCode, Countries sendCountryCode, Countries receiveCountryCode,BigDecimal rangeFrom, BigDecimal rangeTo);
    public List<FeeMaster> findAllByIsDeletedAndRangeFromGreaterThanEqualAndRangeToLessThanEqual(Boolean isDeleted, BigDecimal rangeFrom, BigDecimal rangeTo);
    public List<FeeMaster> findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(Boolean isDeleted, PaymentReceiveMode receiveCode, PaymentReceiveMode paymentCode, Currencies sendCurrencyCode, Currencies receiveCurrencyCode, Countries sendCountryCode, Countries receiveCountryCode);
    
    @Query("SELECT min(rangeFrom) FROM FeeMaster WHERE sendCountryCode = ?1 and receiveCountryCode = ?2 and userType = ?3 and isDeleted=false and publish=true")
    public BigDecimal getMinFrom(Countries from, Countries to, UserType userType);
    
    @Query("SELECT u FROM FeeMaster u WHERE u.sendCountryCode = ?1 and u.receiveCountryCode = ?2 and u.userType = ?3 and u.isDeleted=false and u.publish=true")
    public List<FeeMaster> getPaymentCodesByAmt(Countries from, Countries to, UserType userType, BigDecimal amt);
    
    @Query("SELECT u FROM FeeMaster u WHERE u.sendCountryCode = ?1 and u.receiveCountryCode = ?2 and u.paymentCode = ?3 and (?4 >= range_from  and ?4 <= range_to) and u.userType= ?5 and u.isDeleted=false " +
            "and publish=true")

    public FeeMaster getFeeMasterByPaymentCodeAmount(Countries from, Countries to, PaymentReceiveMode paymentCode, BigDecimal amount, UserType userType);
    
    @Query("SELECT max(rangeTo) FROM FeeMaster WHERE sendCountryCode = ?1 and receiveCountryCode = ?2 and userType = ?3 and isDeleted=false and publish=true")
    public BigDecimal getMaxTo(Countries from, Countries to, UserType userType);
    
    @Query("SELECT u FROM FeeMaster u WHERE u.sendCountryCode = ?1 and u.receiveCountryCode = ?2"+
            " and u.userType = ?3 and u.isDeleted=false and u.publish=true")
    public List<FeeMaster> findAllPaymentReceiveModeFeeMaster(Countries from, Countries to, UserType userType);
	public List<FeeMaster> findAllByIsDeletedAndPublishTrue(boolean b);
	public List<FeeMaster> findAllByIsDeletedAndPaymentCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
	public List<FeeMaster> findAllByIsDeletedAndReceiveCodeAndPublishTrue(boolean b, PaymentReceiveMode byId);
}
