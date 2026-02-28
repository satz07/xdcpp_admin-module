package com.adminremit.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;

@Repository
public interface PaymentReceiveModeRepository extends JpaRepository<PaymentReceiveMode,Long> {
    public List<PaymentReceiveMode> findAll();
    public List<PaymentReceiveMode> findAllByPaymentModeType(PaymentModeType paymentModeType);
    public List<PaymentReceiveMode> findAllByPaymentModeTypeAndIsDeleted(PaymentModeType paymentModeType, Boolean publish);
    public List<PaymentReceiveMode> findAllByIsDeleted(Boolean isDeleted);
    public List<PaymentReceiveMode> findAllByIsDeletedAndCountries(Boolean isDeleted, Countries countries);
    public List<PaymentReceiveMode> findAllByIsDeletedAndCurrencies(Boolean isDeleted, Currencies currencies);
    public List<PaymentReceiveMode> findAllByUserTypeAndUserCategoryAndCurrenciesAndCountriesAndPaymentCodeMasterAndIsDeleted(UserType userType, UserCategory userCategory, Currencies currencies, Countries countries, PaymentCodeMaster paymentCodeMaster, Boolean isDeleted);
    public List<PaymentReceiveMode> findAllByIdIn(List<Long> id);
	public List<PaymentReceiveMode> findAllByCurrencies(Currencies currencies);
	
	public List<PaymentReceiveMode> findAllByPaymentModeTypeAndIsDeleted(PaymentModeType paymentModeType,
			boolean b);
	public List<PaymentReceiveMode> findAllByPaymentModeTypeAndIsDeletedAndPublishTrue(PaymentModeType paymentModeType,
			boolean b);
	public List<PaymentReceiveMode> findAllByPaymentModeTypeAndIsDeletedAndUserType(PaymentModeType paymentModeType,boolean b,UserType userType);
	public List<PaymentReceiveMode> findAllByIsDeletedAndCountriesAndPublishTrue(boolean b, Countries byId);
	public List<PaymentReceiveMode> findAllByIsDeletedAndCurrenciesAndPublishTrue(boolean b, Currencies byId);
	public List<PaymentReceiveMode> findByPaymentModeTypeAndCountriesAndIsDeletedAndPublishTrue(PaymentModeType paymentModeType,Countries country,boolean b);
	public List<PaymentReceiveMode> findByPaymentModeTypeAndCountriesAndIsDeletedAndPublishTrueAndUserType(PaymentModeType paymentModeType,Countries country,boolean b,UserType userType);
	public List<PaymentReceiveMode> findByPaymentModeTypeAndCurrenciesAndIsDeletedAndPublishTrue(PaymentModeType paymentModeType,Currencies currency,boolean b);
	public List<PaymentReceiveMode> findByPaymentModeTypeAndCurrenciesAndIsDeletedAndPublishTrueAndUserType(PaymentModeType paymentModeType,Currencies currency,boolean b,UserType userType);
	}
