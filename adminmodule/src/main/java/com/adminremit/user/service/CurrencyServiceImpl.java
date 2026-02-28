package com.adminremit.user.service;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.GlobalLimitMaster;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.repository.CurrenciesRepo;
import com.adminremit.masters.repository.FeeMasterRepository;
import com.adminremit.masters.repository.GlobalLimitMasterRepository;
import com.adminremit.masters.repository.MarginRepository;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.PaymentReceiveModeService;
import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.repository.UserCalculatorRepository;
import com.adminremit.user.dto.CurrencyConvert;
import com.adminremit.user.model.CurrencyCalculatorModel;


@Service
public class CurrencyServiceImpl implements CurrencyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	
	BigDecimal transferAmt = new BigDecimal(2000);
	
	 @Autowired
	 private CountriesService countryService;
	 
	 @Autowired
	 PaymentReceiveModeService paymentReceiveModeService;
	 
	 @Autowired
	 UserCalculatorRepository userCalculatorRepository;
	 
	 @Autowired
	 FeeMasterRepository feeMasterRepository;
	 
	 @Autowired
	 CurrenciesRepo currenciesRepo;
	 
	 @Autowired
	 RedisTemplate<String,Float> RedisTemplate;
	 
	 @Autowired
	 MarginRepository marginRepository;
	 
	 @Autowired
	 private KieContainer kieContainer; 
	 
	 HashOperations hashOperations;
	 
	 @Autowired
	 GlobalLimitMasterRepository globalLimitMasterRepository;


	
	@Override
    public CurrencyCalculatorModel getCurrencyCalculatorDetails(CurrencyConvert currencyConvert) throws RecordNotFoundException {
        CurrencyCalculatorModel currencyCalculatorModel = new CurrencyCalculatorModel();
        LOG.info("From Country :"+currencyConvert.getFromCountryId()+"        To Country:"+currencyConvert.getToCountryId() +"  UID:"+currencyConvert.getUid());
        DecimalFormat df=new DecimalFormat( "#,##0.##");
        DecimalFormat transformat=new DecimalFormat( "#,##0.####");
        
        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findByUid(currencyConvert.getUid());

        if(currencyConvert.getAmt()==null)
        {
            currencyConvert.setAmt(transferAmt);
        }
        
        // Added for Payment Mode
        Countries fromCountries = countryService.getById(currencyConvert.getFromCountryId());
        Countries toCountries = countryService.getById(currencyConvert.getToCountryId());
        List<PaymentReceiveMode> paymentModeTypes =  getPaymentReceiveMode(fromCountries,toCountries, userCalculatorMapping);
        Map<Long,String> paymentReceiveModes = new HashMap<Long,String>();
        for(PaymentReceiveMode paymentModeType : paymentModeTypes)
        {
            paymentReceiveModes.put(paymentModeType.getId(),paymentModeType.getDescription());
        }
        LOG.info("Payment Recieve Mode from country :"+fromCountries.getCountryCode()+"  all Payment Code  "+paymentReceiveModes);
        PaymentReceiveMode paymentCodeMasters = null;
        if(currencyConvert.getTransferMethod()!=null){
            LOG.info(" getTransferMethod() "+currencyConvert.getTransferMethod());
            paymentCodeMasters = paymentReceiveModeService.getPaymentReceiveModeFeeMaster(currencyConvert.getTransferMethod());
            LOG.info(" PaymentCodeMaster "+paymentCodeMasters.getDescription());
        }
        else if(currencyConvert.getUid()!=null) {
            
            LOG.info("userCalculatorMapping is null :"+userCalculatorMapping);
            if(userCalculatorMapping!=null)
             paymentCodeMasters = paymentReceiveModeService.getPaymentReceiveModeFeeMaster(userCalculatorMapping.getPaymentCodeid());
            else{
            	 RemittUser user = userCalculatorMapping!=null ? userCalculatorMapping.getUser() : null;
            	 BigDecimal minAmount = null;
            	 if(user!=null && user.getUserType()!=null) {
            		 minAmount = feeMasterRepository.getMinFrom(fromCountries,toCountries, UserType.valueOf(user.getUserType()));
            	 }
                 
                 
                 // Viren - Check if the entered amount is less than the min amount then we need to fall back to the min amount for that
                 // currency / country combination
                 // TODO - The transfer mode shall be considered
                 if(currencyConvert.getAmt().compareTo(minAmount)  == -1) {
                     currencyConvert.setAmt(minAmount);
                 }
                 
                 //FeeMaster feeMaster = getFeeMasterByAmount(fromCountries,toCountries,currencyConvert.getAmt());
                 List<FeeMaster> feeMaster = getFeeMasterListByAmount(fromCountries,toCountries,currencyConvert.getAmt(), user.getUserType());
                 
                 for(FeeMaster fees : feeMaster) {
                	 if(paymentCodeMasters == null || paymentCodeMasters.getDescription() == "PayID") {
                 		paymentCodeMasters = fees.getPaymentCode();	
                 	}
                 }
            }
        }

        // Rate conversion
        Currencies fromCurrencyDetails = currenciesRepo.findAllByCountryCode(fromCountries.getCountryCode()).get(0);
        Currencies toCurrencyDetails = currenciesRepo.findAllByCountryCode(toCountries.getCountryCode()).get(0);

        LOG.info("From Currency :"+fromCurrencyDetails.getCurrencyCode()+"To Currency :"+toCurrencyDetails.getCurrencyCode());

        //Currency value for specific Countries
        Map<String,Float> currencyDetails = getCurrency(fromCurrencyDetails.getCurrencyCode());
        float currencyRateFrom  = currencyDetails.get(fromCurrencyDetails.getCurrencyCode());
        float currencyRateTo  = currencyDetails.get(toCurrencyDetails.getCurrencyCode());

        LOG.info("From Currency Amt:"+currencyRateFrom);
        LOG.info("To Currency Amt:"+currencyRateTo);
        LOG.info("paymentCodeMasters:"+paymentCodeMasters.getId());
        LOG.info("Amount:"+ currencyConvert.getAmt());
        LOG.info("FromCurrencyDetails:"+fromCurrencyDetails.getId()+" Currency Code :"+fromCurrencyDetails.getCurrencyCode());
        LOG.info("ToCurrencyDetails:"+toCurrencyDetails.getId()+" Currency Code :"+toCurrencyDetails.getCurrencyCode());

        //List<FeeMaster> feeMasters = feeMasterRepository.getFeeMasterByPaymentCodes(fromCountries, toCountries, paymentCodeMasters);
        RemittUser user = userCalculatorMapping!=null ? userCalculatorMapping.getUser() : null;
        FeeMaster feemaster = new FeeMaster();
        if(user!=null && user.getUserType()!=null) {
        	feemaster = feeMasterRepository.getFeeMasterByPaymentCodeAmount(fromCountries,toCountries,paymentCodeMasters, currencyConvert.getAmt(), UserType.valueOf(user.getUserType()));
        }
        
        LOG.info("Feemaster "+feemaster.getFlatPercFlag()+" Transaction Fee" +feemaster.getTransactionFee());
        MarginMaster marginMaster = new MarginMaster();
        if(user!=null && user.getUserType()!=null) {
        	marginMaster = marginRepository.getMarginMasterByPaymentCode(fromCurrencyDetails,toCurrencyDetails,paymentCodeMasters, currencyConvert.getAmt(), UserType.valueOf(user.getUserType()));
        }        
        
        KieSession kieSession = kieContainer.newKieSession();
        HashMap hm = new HashMap();
        hm.put("feeMaster",feemaster);
        hm.put("currencyAmt", currencyConvert.getAmt());
        hm.put("fxBaseRate",new BigDecimal(currencyRateTo));
        if(marginMaster!=null)
        {
            hm.put("marginMaster",marginMaster);
        }
        kieSession.insert(hm); // which object to validate
        kieSession.fireAllRules(); // fire all rules defined into drool file (drl)
        kieSession.dispose();

        //get receive Mode Master
        LOG.info("============= > Feemaster Receive Mode "+feemaster.getReceiveCode().getId()+"    "+feemaster.getReceiveCode().getPaymentCodeMaster().getPaymentCode());


        currencyCalculatorModel.setFromCountryCode(fromCountries.getCountryCode());
        currencyCalculatorModel.setFromCurrencyCode(fromCurrencyDetails.getCurrencyCode());
        currencyCalculatorModel.setFromCurrencyValue(new BigDecimal(currencyRateFrom));
        currencyCalculatorModel.setStrFromCurrencyValue(df.format(currencyRateFrom));
        currencyCalculatorModel.setToCountryCode(toCountries.getCountryCode());
        currencyCalculatorModel.setToCurrencyCode(toCurrencyDetails.getCurrencyCode());
        BigDecimal totalMargin = new BigDecimal(hm.get("totalMargin").toString());
        currencyCalculatorModel.setToCurrencyValue(totalMargin);
        currencyCalculatorModel.setStrToCurrencyValue(transformat.format(totalMargin));
        currencyCalculatorModel.setPaymentCodeid(paymentCodeMasters.getId());
        currencyCalculatorModel.setPaymentType(paymentCodeMasters.getPaymentCodeMaster().getPaymentCode());
        currencyCalculatorModel.setReceiveCodeid(feemaster.getReceiveCode().getId());
        currencyCalculatorModel.setReceiveType(feemaster.getReceiveCode().getPaymentCodeMaster().getPaymentCode());


        currencyCalculatorModel.setTransferAmount( currencyConvert.getAmt());
        currencyCalculatorModel.setStrTransferAmount(df.format( currencyConvert.getAmt()));
        currencyCalculatorModel.setPaymentReceiveModes(paymentReceiveModes);
        BigDecimal transactionFee = new BigDecimal(hm.get("transactionFee").toString());
        currencyCalculatorModel.setTransactionFee(transactionFee);
        currencyCalculatorModel.setStrTransactionFee(df.format(transactionFee));
        int tat = feemaster.getPaymentmodeTAT()+ feemaster.getReceivemodeTAT();
        currencyCalculatorModel.setTat(tat);
        BigDecimal total = new BigDecimal(hm.get("totalAmt").toString());
        if(total.signum()<0) {
            total = new BigDecimal(0);
        }
        currencyCalculatorModel.setTotalConvertedValue(total);
        currencyCalculatorModel.setStrTotalConvertedValue(df.format(total));
        //min max Range
        currencyCalculatorModel.setFromRange(feeMasterRepository.getMinFrom(fromCountries,toCountries, UserType.valueOf(user.getUserType())));
        currencyCalculatorModel.setToRange(feeMasterRepository.getMaxTo(fromCountries,toCountries, UserType.valueOf(user.getUserType())));
        // Before reponse Object will store the Values.
        currencyCalculatorModel.setFromCountryCodeId(fromCountries.getId());
        currencyCalculatorModel.setToCountryCodeId(toCountries.getId());

        // Viren Commented this because of compilation errors
        //currencyCalculatorModel.setUserAccountType(feemaster.getUserType());
        
        String fxBaseRate = hm.get("baseRate").toString();
        currencyCalculatorModel.setFxBaseRate(fxBaseRate);
        
        String marginSpread = hm.get("margin").toString();
        currencyCalculatorModel.setMarginSpread(marginSpread);

        UserCalculatorMapping savedUserCalculation = saveUserCurrencyCalcMapping(currencyConvert.getUid(),currencyCalculatorModel);
        return currencyCalculatorModel;
    }
	
	private  List<PaymentReceiveMode> getPaymentReceiveMode(Countries fromCountry,Countries toCountries, UserCalculatorMapping userCalculatorMapping) throws RecordNotFoundException {
		RemittUser user = userCalculatorMapping.getUser();
		List<FeeMaster> feeMasters = new ArrayList<>();
		
		if(user!=null && user.getUserType()!=null) {
			feeMasters = feeMasterRepository.findAllPaymentReceiveModeFeeMaster(fromCountry,toCountries, UserType.valueOf(user.getUserType().toUpperCase()));
		}
        	
        List<PaymentReceiveMode> paymentReceiveModeList  = new ArrayList<>();
        for(FeeMaster feeMaster: feeMasters)
        {
            paymentReceiveModeList.add(feeMaster.getPaymentCode());
        }
        
        return paymentReceiveModeList;
    }
	
	@Override
    public UserCalculatorMapping getUserCurrencyCalcMapping(String uid) {
        UserCalculatorMapping userCalculatorMapping = null;
        userCalculatorMapping = userCalculatorRepository.findByUid(uid);
        return userCalculatorMapping;
    }
	
	private  List<FeeMaster>  getFeeMasterListByAmount(Countries fromCountry,Countries toCountry,BigDecimal amt, String userType) throws RecordNotFoundException {

        List<FeeMaster> feeMasters = feeMasterRepository.getPaymentCodesByAmt(fromCountry,toCountry,UserType.valueOf(userType), amt);
        if(feeMasters!=null)
        {
            return feeMasters;
        }
        return null;
    }
	
	@Override
    public Map<String, Float> getCurrency(String currency) {
        hashOperations = RedisTemplate.opsForHash();
        Map<String, Float> entry = (Map<String, Float>) hashOperations.entries("CURRENCY:"+currency);
        return entry;
    }
	
    @Override
   public UserCalculatorMapping saveUserCurrencyCalcMapping(String uid,CurrencyCalculatorModel currencyCalculatorModel) {

       UserCalculatorMapping userCalculatorMapping = null;
       userCalculatorMapping = userCalculatorRepository.findByUid(uid);
       LOG.info("UserCalculatorMapping   "+userCalculatorMapping);
       if (userCalculatorMapping != null){
           userCalculatorMapping.setFromCountryCode(currencyCalculatorModel.getFromCountryCode());
           userCalculatorMapping.setFromCurrencyCode(currencyCalculatorModel.getFromCurrencyCode());
           userCalculatorMapping.setFromCurrencyValue(currencyCalculatorModel.getFromCurrencyValue());

           userCalculatorMapping.setToCountryCode(currencyCalculatorModel.getToCountryCode());
           userCalculatorMapping.setToCurrencyCode(currencyCalculatorModel.getToCurrencyCode());
           userCalculatorMapping.setToCurrencyValue(currencyCalculatorModel.getToCurrencyValue());

           //userCalculatorMapping.setPaymentCodeid(currencyCalculatorModel.getPaymentCodeid());
           //userCalculatorMapping.setPaymentMode(currencyCalculatorModel.getPaymentType());
           //userCalculatorMapping.setReceiveModeId(currencyCalculatorModel.getReceiveCodeid());
           //userCalculatorMapping.setReceiveMode(currencyCalculatorModel.getReceiveType());


           userCalculatorMapping.setTransferAmount(currencyCalculatorModel.getTransferAmount());
           userCalculatorMapping.setTransactionFee(currencyCalculatorModel.getTransactionFee());
           userCalculatorMapping.setTotalConvertedValue(currencyCalculatorModel.getTotalConvertedValue());
           userCalculatorMapping.setTat(currencyCalculatorModel.getTat());
           userCalculatorMapping.setFromCountryId(currencyCalculatorModel.getFromCountryCodeId());
           userCalculatorMapping.setToCountryId(currencyCalculatorModel.getToCountryCodeId());
           //userCalculatorMapping.setUserAccountType(currencyCalculatorModel.getUserAccountType());
           userCalculatorMapping.setFxBaseRate(currencyCalculatorModel.getFxBaseRate());
           userCalculatorMapping.setMarginSpread(currencyCalculatorModel.getMarginSpread());

           userCalculatorRepository.save(userCalculatorMapping);
       }
       else
       {


           UserCalculatorMapping newUserCalculatorMapping = new UserCalculatorMapping();
           newUserCalculatorMapping.setUid(uid);

           newUserCalculatorMapping.setFromCountryCode(currencyCalculatorModel.getFromCountryCode());
           newUserCalculatorMapping.setFromCurrencyCode(currencyCalculatorModel.getFromCurrencyCode());
           newUserCalculatorMapping.setFromCurrencyValue(currencyCalculatorModel.getFromCurrencyValue());

           newUserCalculatorMapping.setToCountryCode(currencyCalculatorModel.getToCountryCode());
           newUserCalculatorMapping.setToCurrencyCode(currencyCalculatorModel.getToCurrencyCode());
           newUserCalculatorMapping.setToCurrencyValue(currencyCalculatorModel.getToCurrencyValue());

           newUserCalculatorMapping.setPaymentCodeid(currencyCalculatorModel.getPaymentCodeid());
           newUserCalculatorMapping.setPaymentMode(currencyCalculatorModel.getPaymentType());
           newUserCalculatorMapping.setReceiveModeId(currencyCalculatorModel.getReceiveCodeid());
           newUserCalculatorMapping.setReceiveMode(currencyCalculatorModel.getReceiveType());

           newUserCalculatorMapping.setTransferAmount(currencyCalculatorModel.getTransferAmount());
           newUserCalculatorMapping.setTransactionFee(currencyCalculatorModel.getTransactionFee());
           newUserCalculatorMapping.setTat(currencyCalculatorModel.getTat());

           newUserCalculatorMapping.setTotalConvertedValue(currencyCalculatorModel.getTotalConvertedValue());

           newUserCalculatorMapping.setFromCountryId(currencyCalculatorModel.getFromCountryCodeId());
           newUserCalculatorMapping.setToCountryId(currencyCalculatorModel.getToCountryCodeId());
           newUserCalculatorMapping.setUserAccountType(currencyCalculatorModel.getUserAccountType());
           newUserCalculatorMapping.setFxBaseRate(currencyCalculatorModel.getFxBaseRate());
           newUserCalculatorMapping.setMarginSpread(currencyCalculatorModel.getMarginSpread());


           userCalculatorMapping=  userCalculatorRepository.save(newUserCalculatorMapping);

       }
     return userCalculatorMapping;

   }
    
    @Override
    public UserCalculatorMapping updateCurrencyCalcMapping(UserCalculatorMapping userCalculatorMapping) {
        UserCalculatorMapping userCalculatorMapping1 = userCalculatorRepository.save(userCalculatorMapping);
        return userCalculatorMapping1;
    }
    
    @Override
    public GlobalLimitMaster getLimits() {
        return globalLimitMasterRepository.get().get(0);
    }

}
