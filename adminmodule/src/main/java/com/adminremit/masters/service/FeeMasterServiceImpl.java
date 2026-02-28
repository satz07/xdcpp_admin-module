package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.*;
import com.adminremit.masters.repository.FeeMasterRepository;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeeMasterServiceImpl implements FeeMasterService {

    @Autowired
    private FeeMasterRepository feeMasterRepository;

    @Autowired
    private MakerAndCheckerService makerAndCheckerService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private CountriesService countriesService;

    @Override
    @Transactional
    public FeeMaster saveFeeMaster(FeeMaster feeMaster) throws NoSuchFieldException {
        feeMaster.setPublish(false);
        if (feeMaster.getId() == null) {
            feeMaster = feeMasterRepository.save(feeMaster);
            return feeMaster;
        } else {
            Optional<FeeMaster> feeMaster1 = feeMasterRepository.findById(feeMaster.getId());
            if (feeMaster1.isPresent()) {
                MakerAndChecker makerAndChecker = new MakerAndChecker();
                List<MakerAndCheckerValues> makerAndCheckerValues = new ArrayList<>();
                boolean isValueChanged = false;
                if (feeMaster1.get().getRangeFrom() != feeMaster.getRangeFrom()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(feeMaster.getClass().getDeclaredField("rangeFrom").getName());
                    makerAndCheckerValue.setFieldType(feeMaster.getClass().getDeclaredField("rangeFrom").getType().getName());
                    makerAndCheckerValue.setFromValue(feeMaster1.get().getRangeFrom().toString());
                    makerAndCheckerValue.setToValue(feeMaster.getRangeFrom().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (feeMaster1.get().getRangeTo() != feeMaster.getRangeTo()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(feeMaster.getClass().getDeclaredField("rangeTo").getName());
                    makerAndCheckerValue.setFieldType(feeMaster.getClass().getDeclaredField("rangeTo").getType().getName());
                    makerAndCheckerValue.setFromValue(feeMaster1.get().getRangeFrom().toString());
                    makerAndCheckerValue.setToValue(feeMaster.getRangeFrom().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (feeMaster1.get().getTransactionFee() != feeMaster.getTransactionFee()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(feeMaster.getClass().getDeclaredField("transactionFee").getName());
                    makerAndCheckerValue.setFieldType(feeMaster.getClass().getDeclaredField("transactionFee").getType().getName());
                    makerAndCheckerValue.setFromValue(feeMaster1.get().getTransactionFee().toString());
                    makerAndCheckerValue.setToValue(feeMaster.getTransactionFee().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (feeMaster1.get().getIsTransactionFeeApplicable() != feeMaster.getIsTransactionFeeApplicable()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(feeMaster.getClass().getDeclaredField("isTransactionFeeApplicable").getName());
                    makerAndCheckerValue.setFieldType(feeMaster.getClass().getDeclaredField("isTransactionFeeApplicable").getType().getName());
                    makerAndCheckerValue.setFromValue(new Boolean(feeMaster1.get().getIsTransactionFeeApplicable()).toString());
                    makerAndCheckerValue.setToValue(new Boolean(feeMaster.getIsTransactionFeeApplicable()).toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (feeMaster1.get().getVendorTransactionFee() != feeMaster.getVendorTransactionFee()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(feeMaster.getClass().getDeclaredField("vendorTransactionFee").getName());
                    makerAndCheckerValue.setFieldType(feeMaster.getClass().getDeclaredField("vendorTransactionFee").getType().getName());
                    makerAndCheckerValue.setFromValue(feeMaster1.get().getVendorTransactionFee().toString());
                    makerAndCheckerValue.setToValue(feeMaster.getVendorTransactionFee().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (isValueChanged) {
                    makerAndChecker.setMakerCheckerFunction(MakerCheckerFunction.FEE);
                    makerAndChecker.setMakerAndCheckerValues(makerAndCheckerValues);
                    makerAndChecker.setRecordId(feeMaster.getId());
                    makerAndCheckerService.save(makerAndChecker);
                }
                return feeMasterRepository.save(feeMaster);
            }
            {
                feeMaster = feeMasterRepository.save(feeMaster);
                return feeMaster;
            }
        }
    }

    public void deleteFeeMaster(Long id) throws RecordNotFoundException {
        Optional<FeeMaster> feeMaster = feeMasterRepository.findById(id);
        if (feeMaster.isPresent()) {
            FeeMaster feeMaster1 = feeMaster.get();
            feeMaster1.setIsDeleted(true);
            feeMasterRepository.save(feeMaster1);
        }
    }

    public FeeMaster getById(Long id) throws RecordNotFoundException {
        Optional<FeeMaster> feeMaster = feeMasterRepository.findById(id);
        FeeMaster feeMaster1 = null;
        try {
            if (feeMaster.isPresent()) {
                feeMaster1 = feeMaster.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feeMaster1;
    }

    public List<FeeMaster> listOfFees() {
        List<FeeMaster> feeMasters = null;
        try {
            feeMasters = feeMasterRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feeMasters;
    }

    @Override
    public List<FeeMaster> listAll() {
        return feeMasterRepository.findAllByIsDeletedAndPublishTrue(false);
    }

    @Override
    public List<FeeMaster> listOfFeeByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return feeMasterRepository.findAllByIsDeletedAndPaymentCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public List<FeeMaster> listOfFeeByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return feeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public boolean getExistingFeeByConditions(FeeMaster feeMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(feeMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(feeMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(feeMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(feeMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(feeMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(feeMaster.getReceiveCurrencyCode().getId());
            List<FeeMaster> feeMasters3 = feeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (FeeMaster feeMaster1: feeMasters3) {
                boolean isEx = this.isBetween(feeMaster.getRangeFrom(),feeMaster1.getRangeFrom(),feeMaster1.getRangeTo());
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
    public boolean getExistingFeeByConditionsById(FeeMaster feeMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(feeMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(feeMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(feeMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(feeMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(feeMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(feeMaster.getReceiveCurrencyCode().getId());
            List<FeeMaster> feeMasters3 = feeMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (FeeMaster feeMaster1: feeMasters3) {
                if(feeMaster1.getId().longValue() != feeMaster.getId().longValue()) {
                    boolean isEx = this.isBetween(feeMaster.getRangeFrom(), feeMaster1.getRangeFrom(), feeMaster1.getRangeTo());
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

    @Override
    public Optional<FeeMaster> getFeeById(Long feeId) {
        return feeMasterRepository.findById(feeId);
    }

    @Override
    public void approveFee(FeeMaster fee) {
        Optional<FeeMaster> optionalFee = feeMasterRepository.findById(fee.getId());
        if (optionalFee.isPresent()) {
            feeMasterRepository.save(fee);
        }
    }

    @Override
    public void rejectFee(FeeMaster fee) {
        Optional<FeeMaster> optionalFee = feeMasterRepository.findById(fee.getId());
        if (optionalFee.isPresent()) {
            feeMasterRepository.save(fee);
        }
    }

    public boolean isBetween(BigDecimal price, BigDecimal start, BigDecimal end){
        return price.compareTo(start) >= 0 && price.compareTo(end) <= 0;
    }
}
