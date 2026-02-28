package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.*;
import com.adminremit.masters.repository.GlobalLimitMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GlobalLimitMasterServiceImpl implements GlobalLimitMasterService{

    @Autowired
    private GlobalLimitMasterRepository globalLimitMasterRepository;

    @Autowired
    private MakerAndCheckerService makerAndCheckerService;


    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private FeeMasterService feeMasterService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private CountriesService countriesService;

    public GlobalLimitMaster save(GlobalLimitMaster globalLimitMaster) throws NoSuchFieldException{
        globalLimitMaster.setPublish(false);
        if(globalLimitMaster.getId() == null) {
            globalLimitMaster =globalLimitMasterRepository.save(globalLimitMaster);
            return globalLimitMaster;
        } else {
            Optional<GlobalLimitMaster> globalLimitMaster1 = globalLimitMasterRepository.findById(globalLimitMaster.getId());
            if(globalLimitMaster1.isPresent()) {
                MakerAndChecker makerAndChecker = new MakerAndChecker();
                List<MakerAndCheckerValues> makerAndCheckerValues = new ArrayList<>();
                boolean isValueChanged = false;
                if (globalLimitMaster1.get().getMinTxnLimit() != globalLimitMaster.getMinTxnLimit()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(globalLimitMaster.getClass().getDeclaredField("minTxnLimit").getName());
                    makerAndCheckerValue.setFieldType(globalLimitMaster.getClass().getDeclaredField("minTxnLimit").getType().getName());
                    makerAndCheckerValue.setFromValue(globalLimitMaster1.get().getMinTxnLimit().toString());
                    makerAndCheckerValue.setToValue(globalLimitMaster.getMinTxnLimit().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }
                if (globalLimitMaster1.get().getMaxTxnLimit() != globalLimitMaster.getMaxTxnLimit()) {
                    isValueChanged = true;
                    MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
                    makerAndCheckerValue.setFieldName(globalLimitMaster.getClass().getDeclaredField("maxTxnLimit").getName());
                    makerAndCheckerValue.setFieldType(globalLimitMaster.getClass().getDeclaredField("maxTxnLimit").getType().getName());
                    makerAndCheckerValue.setFromValue(globalLimitMaster1.get().getMaxTxnLimit().toString());
                    makerAndCheckerValue.setToValue(globalLimitMaster.getMaxTxnLimit().toString());
                    makerAndCheckerValues.add(makerAndCheckerValue);
                }

                if (isValueChanged) {
                    makerAndChecker.setMakerCheckerFunction(MakerCheckerFunction.GLOBAL_LIMITS);
                    makerAndChecker.setMakerAndCheckerValues(makerAndCheckerValues);
                    makerAndChecker.setRecordId(globalLimitMaster.getId());
                    makerAndCheckerService.save(makerAndChecker);
                }
                return globalLimitMasterRepository.save(globalLimitMaster);
            } {
                globalLimitMaster =globalLimitMasterRepository.save(globalLimitMaster);
                return globalLimitMaster;
            }
        }
    }

    public void deleteGlobalLimits(Long id) throws RecordNotFoundException {
        Optional<GlobalLimitMaster> globalLimitMaster  = globalLimitMasterRepository.findById(id);
        if(globalLimitMaster.isPresent()) {
            GlobalLimitMaster globalLimitMaster1 = globalLimitMaster.get();
            globalLimitMaster1.setIsDeleted(true);
            globalLimitMasterRepository.save(globalLimitMaster1);
        }
    }

    public GlobalLimitMaster getById(Long id) throws RecordNotFoundException {
        Optional<GlobalLimitMaster> globalLimitMaster = globalLimitMasterRepository.findById(id);
        GlobalLimitMaster globalLimitMaster1 = null;
        try {
            if(globalLimitMaster.isPresent()) {
                globalLimitMaster1 = globalLimitMaster.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return globalLimitMaster1;
    }

    public List<GlobalLimitMaster> listOfGlobalLimits() {
        List<GlobalLimitMaster> globalLimitMasters = null;
        globalLimitMasters = globalLimitMasterRepository.findAllByIsDeleted(false);
        return globalLimitMasters;
    }

    @Override
    public List<GlobalLimitMaster> listOfGlobalLimitMasterByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return globalLimitMasterRepository.findAllByIsDeletedAndPaymentCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public List<GlobalLimitMaster> listOfGlobalLimitMasterByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException {
        return globalLimitMasterRepository.findAllByIsDeletedAndReceiveCodeAndPublishTrue(false,paymentReceiveModeService.getById(paymentReceiveModeId));
    }

    @Override
    public boolean getExistingFeeByConditions(GlobalLimitMaster globalLimitMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(globalLimitMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(globalLimitMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(globalLimitMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(globalLimitMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(globalLimitMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(globalLimitMaster.getReceiveCurrencyCode().getId());
            List<GlobalLimitMaster> globalLimitMasterServices = globalLimitMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (GlobalLimitMaster globalLimitMaster1: globalLimitMasterServices) {
                boolean isEx = feeMasterService.isBetween(globalLimitMaster.getMinTxnLimit(),globalLimitMaster1.getMaxTxnLimit(),globalLimitMaster.getMaxTxnLimit());
                //boolean isEx = feeMasterService.isBetween(globalLimitMaster.getRangeFrom(),serviceChargeMaster1.getRangeFrom(),serviceChargeMaster1.getRangeTo());
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
    public boolean getExistingFeeByConditionsById(GlobalLimitMaster globalLimitMaster) {
        boolean isExist = false;
        try {
            PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(globalLimitMaster.getReceiveCode().getId());
            PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(globalLimitMaster.getPaymentCode().getId());
            Countries sendCountry = countriesService.getById(globalLimitMaster.getSendCountryCode().getId());
            Countries receiveCountry = countriesService.getById(globalLimitMaster.getReceiveCountryCode().getId());
            Currencies sendCurrency = currenciesService.getById(globalLimitMaster.getSendCurrencyCode().getId());
            Currencies receiveCurrency = currenciesService.getById(globalLimitMaster.getReceiveCurrencyCode().getId());
            List<GlobalLimitMaster> globalLimitMasterServices = globalLimitMasterRepository.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(false,receiveMode, paymentMode, sendCurrency,receiveCurrency,sendCountry,receiveCountry);
            for (GlobalLimitMaster globalLimitMaster1: globalLimitMasterServices) {
                if(globalLimitMaster1.getId().longValue() != globalLimitMaster.getId().longValue()) {
                    boolean isEx = feeMasterService.isBetween(globalLimitMaster.getMinTxnLimit(), globalLimitMaster1.getMaxTxnLimit(), globalLimitMaster.getMaxTxnLimit());
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
    public Optional<GlobalLimitMaster> getGlobalLimitById(Long globalId) {
        return globalLimitMasterRepository.findById(globalId);
    }

    @Override
    public void approveGlobalLimit(GlobalLimitMaster globalLimit) {
        Optional<GlobalLimitMaster> optionalMargin = globalLimitMasterRepository.findById(globalLimit.getId());
        if (optionalMargin.isPresent()) {
            globalLimitMasterRepository.save(globalLimit);
        }
    }

    @Override
    public void rejectGlobalLimits(GlobalLimitMaster globalLimits) {
        Optional<GlobalLimitMaster> optionalFee = globalLimitMasterRepository.findById(globalLimits.getId());
        if (optionalFee.isPresent()) {
            globalLimitMasterRepository.save(globalLimits);
        }
    }

}
