package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.repository.PaymentReceiveModeRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentReceiveModeServiceImpl implements PaymentReceiveModeService{

    @Autowired
    private PaymentReceiveModeRepository paymentReceiveModeRepository;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private PaymentCodeMasterService paymentCodeMasterService;

    public PaymentReceiveMode save(PaymentReceiveMode paymentReceiveMode) {
        if(paymentReceiveMode.getId() == null) {
            paymentReceiveMode.setPublish(true);
            paymentReceiveMode =paymentReceiveModeRepository.save(paymentReceiveMode);
            return paymentReceiveMode;
        } else {
            Optional<PaymentReceiveMode> paymentReceiveMode1 = paymentReceiveModeRepository.findById(paymentReceiveMode.getId());
            if(paymentReceiveMode1.isPresent()) {
                PaymentReceiveMode paymentReceiveMode2 = paymentReceiveMode1.get();
                paymentReceiveMode2.setId(paymentReceiveMode.getId());
                paymentReceiveMode2.setUserType(paymentReceiveMode.getUserType());
                paymentReceiveMode2.setRemarks(paymentReceiveMode.getRemarks());
                paymentReceiveMode2.setPaymentCodeMaster(paymentReceiveMode.getPaymentCodeMaster());
                paymentReceiveMode2.setUserCategory(paymentReceiveMode.getUserCategory());
                paymentReceiveMode2.setDescription(paymentReceiveMode.getDescription());
                paymentReceiveMode2.setCurrencies(paymentReceiveMode.getCurrencies());
                paymentReceiveMode2.setCountries(paymentReceiveMode.getCountries());
                paymentReceiveMode2.setComments(paymentReceiveMode.getComments());
                paymentReceiveMode2.setPaymentModeType(paymentReceiveMode.getPaymentModeType());
                paymentReceiveMode2.setPublish(paymentReceiveMode.getPublish());
                return paymentReceiveModeRepository.save(paymentReceiveMode2);
            } {
                paymentReceiveMode =paymentReceiveModeRepository.save(paymentReceiveMode);
                return paymentReceiveMode;
            }
        }
    }

    public List<PaymentReceiveMode> findAllByType(PaymentModeType paymentModeType) {
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findAllByPaymentModeTypeAndIsDeleted(paymentModeType,false);
        return paymentReceiveModes;
    }

    public List<PaymentReceiveMode> findAllByTypeAndUserType(PaymentModeType paymentModeType,UserType userType) {
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findAllByPaymentModeTypeAndIsDeletedAndUserType(paymentModeType,false,userType);
        return paymentReceiveModes;
    }

    public List<PaymentReceiveMode> findAllByTypeAndcountry(PaymentModeType paymentModeType,Countries country) {
    	// log.info("paymodetype"+paymentModeType);
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findByPaymentModeTypeAndCountriesAndIsDeletedAndPublishTrue(paymentModeType,country,false);
       
		/*
		 * log.info("Countries"+paymentReceiveModes.get(0).getId());
		 * log.info("Countries"+paymentReceiveModes.get(1).getId());
		 * log.info("Countries"+paymentReceiveModes.size());
		 */
        return paymentReceiveModes;
    }
    public List<PaymentReceiveMode> findAllByTypeAndCurrency(PaymentModeType paymentModeType,Currencies currency) {
   	// log.info("paymodetype"+paymentModeType);
       List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findByPaymentModeTypeAndCurrenciesAndIsDeletedAndPublishTrue(paymentModeType,currency,false);
      
		
		/*
		 * log.info("Currencies"+paymentReceiveModes.get(0).getId());
		 * log.info("Currencies"+paymentReceiveModes.get(1).getId());
		 * log.info("Currencies"+paymentReceiveModes.size());
		 */
		 
       return paymentReceiveModes;
   }

    @Override
    public List<PaymentReceiveMode> findAllByTypeAndCurrencyAndUserType(PaymentModeType paymentModeType, Currencies currencies, UserType userType) {
        // log.info("paymodetype"+paymentModeType);
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findByPaymentModeTypeAndCurrenciesAndIsDeletedAndPublishTrueAndUserType(paymentModeType,currencies,false,userType);

        /*
         * log.info("Countries"+paymentReceiveModes.get(0).getId());
         * log.info("Countries"+paymentReceiveModes.get(1).getId());
         * log.info("Countries"+paymentReceiveModes.size());
         */
        return paymentReceiveModes;
    }

    @Override
    public List<PaymentReceiveMode> findAllByTypeAndcountryAndUserType(PaymentModeType paymentModeType, Countries countries, UserType userType) {
        // log.info("paymodetype"+paymentModeType);
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findByPaymentModeTypeAndCountriesAndIsDeletedAndPublishTrueAndUserType(paymentModeType,countries,false,userType);

        /*
         * log.info("Countries"+paymentReceiveModes.get(0).getId());
         * log.info("Countries"+paymentReceiveModes.get(1).getId());
         * log.info("Countries"+paymentReceiveModes.size());
         */
        return paymentReceiveModes;
    }

    public void deletePaymentReceive(Long id) throws RecordNotFoundException {
        Optional<PaymentReceiveMode> paymentReceiveMode = paymentReceiveModeRepository.findById(id);
        if(paymentReceiveMode.isPresent()) {
            PaymentReceiveMode paymentReceiveMode1 = paymentReceiveMode.get();
            paymentReceiveMode1.setIsDeleted(true);
            paymentReceiveModeRepository.save(paymentReceiveMode1);
        }
    }

    public PaymentReceiveMode getById(Long id) throws RecordNotFoundException {
        Optional<PaymentReceiveMode> paymentReceiveMode = paymentReceiveModeRepository.findById(id);
        PaymentReceiveMode paymentReceiveMode1 = null;
        if(paymentReceiveMode.isPresent()) {
            paymentReceiveMode1 = paymentReceiveMode.get();
        }
        return paymentReceiveMode1;
    }

    @Override
    public List<PaymentReceiveMode> findAllPaymentsReceive(PaymentModeType paymentModeType) {
        List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findAllByPaymentModeTypeAndIsDeleted(paymentModeType,false);
        return paymentReceiveModes;
    }

    @Override
    public List<PaymentReceiveMode> findAllPaymentsReceiveByCountry(Long countryId) throws RecordNotFoundException {
        return paymentReceiveModeRepository.findAllByIsDeletedAndCountries(false,countriesService.getById(countryId));
    }

    @Override
    public List<PaymentReceiveMode> findAllPaymentsReceiveByCurrency(Long currencyId) throws RecordNotFoundException {
        return paymentReceiveModeRepository.findAllByIsDeletedAndCurrenciesAndPublishTrue(false,currenciesService.getById(currencyId));
    }


    @Override
    public boolean isPaymentCodeAndReceiveCodeExist(UserType userType, UserCategory userCategory, Long currencyId, Long countryId, Long paymentCodeMaster) {
        boolean isDuplicate = false;
        try {
            Countries countries = countriesService.getById(countryId);
            Currencies currencies = currenciesService.getById(currencyId);
            PaymentCodeMaster paymentCodeMaster1 = paymentCodeMasterService.getById(paymentCodeMaster);
            List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findAllByUserTypeAndUserCategoryAndCurrenciesAndCountriesAndPaymentCodeMasterAndIsDeleted(userType, userCategory, currencies, countries, paymentCodeMaster1, false);
            if(paymentReceiveModes.size() > 0) {
                isDuplicate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    @Override
    public boolean isPaymentCodeAndReceiveCodeExistById(UserType userType, UserCategory userCategory, Long currencyId, Long countryId, Long paymentCodeMaster, PaymentReceiveModeDTO paymentReceiveModeDTO) {
        boolean isDuplicate = false;
        try {
            Countries countries = countriesService.getById(countryId);
            Currencies currencies = currenciesService.getById(currencyId);
            PaymentCodeMaster paymentCodeMaster1 = paymentCodeMasterService.getById(paymentCodeMaster);
            List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeRepository.findAllByUserTypeAndUserCategoryAndCurrenciesAndCountriesAndPaymentCodeMasterAndIsDeleted(userType, userCategory, currencies, countries, paymentCodeMaster1, false);
            if(paymentReceiveModes.size() > 0) {
                for (PaymentReceiveMode paymentReceiveMode: paymentReceiveModes) {
                    if(paymentReceiveMode.getId().longValue() != paymentReceiveModeDTO.getId().longValue()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    @Override
    public PaymentReceiveMode getPaymentReceiveModeFeeMaster(Long id) throws RecordNotFoundException{
        Optional<PaymentReceiveMode> paymentReceiveMode = paymentReceiveModeRepository.findById(id);

        return paymentReceiveMode.get();
    }
	/*
	 * @Override public List<PaymentReceiveMode> getCurrencyCodeByCountryCode(String
	 * countryCode) {
	 * 
	 * List<PaymentReceiveMode> paymentReceiveMode = null; try { List<Currencies>
	 * currencies = currenciesService.getCurrencyCodeByCountryName(countryCode);
	 * paymentReceiveMode =
	 * paymentReceiveModeRepository.findAllByCurrencies((Currencies) currencies);
	 * log.info("Countries: "+paymentReceiveMode); for (PaymentReceiveMode c :
	 * paymentReceiveMode) {
	 * log.info("country codes are: "+c.getCountries().getCountryCode());
	 * log.info("currency codes are: "+c.getCurrencies().getCurrencyCode()); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return paymentReceiveMode; }
	 */
	
}
