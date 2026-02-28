package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.*;
import com.adminremit.masters.repository.ServiceChargeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceChargeMasterImpl implements ServiceChargeMasterService {

    @Autowired
    private ServiceChargeMasterRepository serviceChargeMasterRepository;

    @Autowired
    private MakerAndCheckerService makerAndCheckerService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private FeeMasterService feeMasterService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CurrenciesService currenciesService;

    public ServiceChargeMaster save(ServiceChargeMaster serviceChargeMaster) throws NoSuchFieldException {
        if(serviceChargeMaster.getId() == null) {
            serviceChargeMaster.setPublish(true);
            serviceChargeMaster =serviceChargeMasterRepository.save(serviceChargeMaster);
            return serviceChargeMaster;
        } else {
            Optional<ServiceChargeMaster> serviceChargeMaster1 = serviceChargeMasterRepository.findById(serviceChargeMaster.getId());
            if(serviceChargeMaster1.isPresent()) {
                MakerAndChecker makerAndChecker = new MakerAndChecker();
                List<MakerAndCheckerValues> makerAndCheckerValues = new ArrayList<>();
                boolean isValueChanged = false;
                if (serviceChargeMaster1.get().getRangeFrom() != serviceChargeMaster.getRangeFrom()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(serviceChargeMaster.getClass().getDeclaredField("rangeFrom").getName());
                    makerAndCheckerValue.setFieldType(serviceChargeMaster.getClass().getDeclaredField("rangeFrom").getType().getName());
                    makerAndCheckerValue.setFromValue(serviceChargeMaster1.get().getRangeFrom().toString());
                    makerAndCheckerValue.setToValue(serviceChargeMaster.getRangeFrom().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (serviceChargeMaster1.get().getRangeTo() != serviceChargeMaster.getRangeTo()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(serviceChargeMaster.getClass().getDeclaredField("rangeTo").getName());
                    makerAndCheckerValue.setFieldType(serviceChargeMaster.getClass().getDeclaredField("rangeTo").getType().getName());
                    makerAndCheckerValue.setFromValue(serviceChargeMaster1.get().getRangeFrom().toString());
                    makerAndCheckerValue.setToValue(serviceChargeMaster.getRangeFrom().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }

                if (isValueChanged) {
                    makerAndChecker.setMakerCheckerFunction(MakerCheckerFunction.SERVICE_CHARGE);
                    makerAndChecker.setMakerAndCheckerValues(makerAndCheckerValues);
                    makerAndChecker.setRecordId(serviceChargeMaster.getId());
                    makerAndCheckerService.save(makerAndChecker);
                }
                return serviceChargeMasterRepository.save(serviceChargeMaster);
            } {
                serviceChargeMaster =serviceChargeMasterRepository.save(serviceChargeMaster);
                return serviceChargeMaster;
            }
        }
    }

    public void deleteServiceCharge(Long id) throws RecordNotFoundException {
        Optional<ServiceChargeMaster> serviceChargeMaster  = serviceChargeMasterRepository.findById(id);
        if(serviceChargeMaster.isPresent()) {
            ServiceChargeMaster serviceChargeMaster1 = serviceChargeMaster.get();
            serviceChargeMaster1.setIsDeleted(true);
            serviceChargeMasterRepository.save(serviceChargeMaster1);
        }
    }

    public ServiceChargeMaster getById(Long id) throws RecordNotFoundException {
        Optional<ServiceChargeMaster> serviceChargeMaster = serviceChargeMasterRepository.findById(id);
        ServiceChargeMaster serviceChargeMaster1 = null;
        try {
            if(serviceChargeMaster.isPresent()) {
                serviceChargeMaster1 = serviceChargeMaster.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceChargeMaster1;
    }

    public List<ServiceChargeMaster> listOfServiceCharge() {
        List<ServiceChargeMaster> serviceChargeMasters = null;
        serviceChargeMasters = serviceChargeMasterRepository.findAllByIsDeleted(false);
        return serviceChargeMasters;
    }

    @Override
    public List<ServiceChargeMaster> listOfFeeByPaymentReceiveMode(Long paymentReceiveModeId) {
        return null;
    }

    @Override
    public List<ServiceChargeMaster> listOfServiceChargeMasterByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return serviceChargeMasterRepository.findAllByIsDeletedAndPaymentCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public List<ServiceChargeMaster> listOfServiceChargeMasterByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return serviceChargeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public boolean getExistingFeeByConditions(ServiceChargeMaster serviceChargeMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(serviceChargeMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(serviceChargeMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(serviceChargeMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(serviceChargeMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(serviceChargeMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(serviceChargeMaster.getReceiveCurrencyCode().getId());
            List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (ServiceChargeMaster serviceChargeMaster1: serviceChargeMasters) {
                boolean isEx = feeMasterService.isBetween(serviceChargeMaster.getRangeFrom(),serviceChargeMaster1.getRangeFrom(),serviceChargeMaster1.getRangeTo());
                if(isEx) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    @Override
    public boolean getExistingFeeByConditionsById(ServiceChargeMaster serviceChargeMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(serviceChargeMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(serviceChargeMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(serviceChargeMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(serviceChargeMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(serviceChargeMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(serviceChargeMaster.getReceiveCurrencyCode().getId());
            List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (ServiceChargeMaster serviceChargeMaster1: serviceChargeMasters) {
                if(serviceChargeMaster1.getId().longValue() != serviceChargeMaster.getId().longValue()) {
                    boolean isEx = feeMasterService.isBetween(serviceChargeMaster.getRangeFrom(), serviceChargeMaster1.getRangeFrom(), serviceChargeMaster1.getRangeTo());
                    if (isEx) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

}
