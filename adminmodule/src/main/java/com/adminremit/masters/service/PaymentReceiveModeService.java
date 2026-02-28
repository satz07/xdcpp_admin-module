package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;

import java.util.List;

public interface PaymentReceiveModeService {
    public PaymentReceiveMode save(PaymentReceiveMode paymentReceiveMode);
    public List<PaymentReceiveMode> findAllByType(PaymentModeType paymentModeType);
    public List<PaymentReceiveMode> findAllByTypeAndUserType(PaymentModeType paymentModeType,UserType userType);
    public void deletePaymentReceive(Long id) throws RecordNotFoundException;
    public PaymentReceiveMode getById(Long id) throws RecordNotFoundException;
    public List<PaymentReceiveMode> findAllPaymentsReceive(PaymentModeType paymentModeType);
    public List<PaymentReceiveMode> findAllPaymentsReceiveByCountry(Long countryId) throws RecordNotFoundException;
    public List<PaymentReceiveMode> findAllPaymentsReceiveByCurrency(Long currencyId) throws RecordNotFoundException;
    public boolean isPaymentCodeAndReceiveCodeExist(UserType userType, UserCategory userCategory, Long currencyId, Long countryId, Long paymentCodeMaster);
    public boolean isPaymentCodeAndReceiveCodeExistById(UserType userType, UserCategory userCategory, Long currencyId, Long countryId, Long paymentCodeMaster, PaymentReceiveModeDTO paymentReceiveModeDTO);
    public PaymentReceiveMode getPaymentReceiveModeFeeMaster(Long id) throws RecordNotFoundException;
    public List<PaymentReceiveMode> findAllByTypeAndcountry(PaymentModeType paymentModeType,Countries countries);
    public List<PaymentReceiveMode> findAllByTypeAndCurrency(PaymentModeType paymentModeType,Currencies currencies);
    public List<PaymentReceiveMode> findAllByTypeAndCurrencyAndUserType(PaymentModeType paymentModeType,Currencies currencies,UserType userType);
    public List<PaymentReceiveMode> findAllByTypeAndcountryAndUserType(PaymentModeType paymentModeType,Countries countries,UserType userType);
}
