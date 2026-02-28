package com.adminremit.masters.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.FeeMasterService;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.masters.service.PaymentReceiveModeService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class FeeMasterController {

    @Autowired
    private FeeMasterService feeMasterService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private PaymentCodeMasterService paymentCodeMasterService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;
    
    @Autowired
    private PartnerService partnerService;

    @Autowired
    private CountriesRepository countriesRepository;
    
    @RequestMapping(value = "/fee",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, FeeMaster feeMaster) {
        List<PaymentReceiveMode> paymentCode = paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT);
        List<PaymentReceiveMode> receiveCode = paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE);
        feeMaster.setUserType(UserType.PERSONAL);
        modelAndView.addObject("fee",feeMaster);
        modelAndView.addObject("paymentCode",paymentCode);
        modelAndView.addObject("receiveCode",receiveCode);
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.setViewName("masters/fee/create");
        return modelAndView;
    }

    @RequestMapping(value = "/fee/create", method = RequestMethod.POST)
    public ModelAndView createFee(@Valid FeeMaster feeMaster, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("fee",feeMaster);
            modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
            modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
            modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
            modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
            modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
          
            modelAndView.addObject("errorMessage", "Please fill mandatory fields!!!");
            modelAndView.setViewName("masters/fee/create");
            return modelAndView;
        } else {
            boolean isExist = feeMasterService.getExistingFeeByConditions(feeMaster);
            if(isExist && feeMaster.getId() == null) {
                feeMaster.setPublish(true);
                modelAndView.addObject("fee",feeMaster);
                modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
                modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
                modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
                modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
                modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("masters/fee/create");
                return modelAndView;
            }
            boolean isExistById = feeMasterService.getExistingFeeByConditionsById(feeMaster);
            if(isExistById && feeMaster.getId() != null) {
                feeMaster.setPublish(true);
                modelAndView.addObject("fee",feeMaster);
                modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
                modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
                modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
                modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
                modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("masters/fee/create");
                return modelAndView;
            }
            feeMasterService.saveFeeMaster(feeMaster);
            modelAndView.addObject("confirmationMessage", "Fee has been created");
            List<FeeMaster> feeMasters = feeMasterService.listOfFees();
            modelAndView.addObject("fees", feeMasters);
        }
        return new ModelAndView("redirect:/fee/list");
    }

    @RequestMapping(path = {"/fee/edit", "/fee/edit/{userType}/{id}"})
    public ModelAndView editFee(ModelAndView model,@PathVariable("userType") Optional<String> userType, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/fee/create");
        }
        if (id.isPresent()) {
            FeeMaster feeMaster = feeMasterService.getById(id.get());
            model.addObject("fee",feeMaster);
            model.addObject("paymentCode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.PAYMENT,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            model.addObject("receiveCode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.RECEIVE,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            model.addObject("userCategory", Arrays.asList(UserCategory.values()));
            model.addObject("countries",countriesService.listOfCountriesCountryCode());
            model.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            model.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            model.setViewName("masters/fee/create");
        } else {
            model.addObject("fee", new FeeMaster());
            model.setViewName("masters/fee/create");
        }
        return model;
    }

    @RequestMapping(value = "/fee/list", method = RequestMethod.GET)
    public ModelAndView listFees(ModelAndView modelAndView) {
        List<FeeMaster> feeMasters = feeMasterService.listOfFees();
        modelAndView.addObject("fees", feeMasters);
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.setViewName("masters/fee/list");
        return modelAndView;
    }

    @RequestMapping(value = "/fee/delete/{id}", method = RequestMethod.GET)
    public String deleteFee(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        feeMasterService.deleteFeeMaster(id.get());
        modelAndView.addObject("confirmationMessage", "Fee has been deleted!!");
        return "redirect:/fee/list";
    }
    @RequestMapping(value = "/api/fee/getFeecurrencyCode/{countryid}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<CurrencyApiResponse> getFeeCurrencyCode(@PathVariable("countryid") Long id, @PathVariable("userType") String userType) throws RecordNotFoundException {
 //  log.info("Country name is:"+countryName);
   /*
    * String[] parts = countryName.split("-"); String splitCountryName = parts[1];
    */

        Countries country = new Countries();
        
		List<Currencies> currencyCode = null;
		country = countriesService.getById(id);
		CurrencyApiResponse currObj = new CurrencyApiResponse();
		List<CurrencyApiResponse> currList = new ArrayList<CurrencyApiResponse>();
		
		currencyCode = currenciesService.getByCountryCodeForCurrencyCode(country.getCountryCode());	
		for (Currencies currency : currencyCode) {
			currObj.setId(currency.getId());
			currObj.setCurrencyName(currency.getCurrencyName());
			currObj.setCurrencyCode(currency.getCurrencyCode());
			currObj.setCountryCode(currency.getCountryCode());
			currObj.setCountryName(currency.getCountryName());
			currObj.setCurrencyDescription(currency.getCurrencyDescription());
			currList.add(currObj);
		}		
		return currList;

	}
    @RequestMapping(value = "/getfeecurrencycodebyIdapi/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getfeeCurrencyCodeByID(@PathVariable Long id) throws RecordNotFoundException {
	//	log.info("fee id:" + id);
		
		FeeMaster recieve = new FeeMaster();
			recieve = 	feeMasterService.getById(id);
	Currencies currencyrecieve = recieve.getReceiveCurrencyCode();
	//log.info("currencyrecieve"+currencyrecieve.getCurrencyCode());
	Currencies currencysend = recieve.getSendCurrencyCode();
	//log.info("currencysend"+currencysend.getCurrencyCode()) ;
	PaymentReceiveMode paymentmode = recieve.getPaymentCode();
	
	PaymentReceiveMode recievemode = recieve.getReceiveCode();
		CurrencyApiResponse caResp = new CurrencyApiResponse();
		List<CurrencyApiResponse> currencyapiresponse = new ArrayList<CurrencyApiResponse>();
		
		caResp.setId( recieve.getReceiveCurrencyCode().getId());
	caResp.setSendCurrencyCode ( currencysend.getCurrencyCode());
	caResp.setReceiveCurrencyId( currencyrecieve.getId());
	caResp.setSendCurrencyId( currencysend.getId());
	caResp.setReceiveCurrencyCode(currencyrecieve .getCurrencyCode());
	caResp.setSendCurrencyName(currencyrecieve.getCurrencyName());
    caResp.setPaymentCode(paymentmode.getPaymentCodeMaster().getPaymentCode());
    caResp.setPaymentCodeId(recieve.getPaymentCode().getId());
    caResp.setPaymentCodedescription(paymentmode.getDescription());
    caResp.setReceiveCode(recievemode.getPaymentCodeMaster().getPaymentCode());
    caResp.setReceiveCodeId(recieve.getReceiveCode().getId());
    caResp.setReceiveCodeCodedescription(recievemode.getDescription());
    caResp.setSendCurrencyDescription(currencysend.getCurrencyName());
    caResp.setReceiveCurrencyDescription(currencyrecieve.getCurrencyName());
	currencyapiresponse.add(caResp);
		return currencyapiresponse;
	}
    
    
    @RequestMapping(value = "/api/fee/getFeepaymentCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getFeepaymentCode(@PathVariable("id")Long id, @PathVariable("userType")String userType) throws RecordNotFoundException {
 
//log.info(""+id);

      Optional<Countries>  country = countriesRepository.findById(id);    
		Countries countries = new Countries();
		if(country.isPresent())
		{
			countries = country.get();
		}
	//	log.info("Country is"+countries.getCountryName());
		
		List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
		List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountryAndUserType(PaymentModeType.PAYMENT,countries,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS);
		for(PaymentReceiveMode paymod : paymodes )
		{

			PaymentReceiveModeDTO paymode = new PaymentReceiveModeDTO();
			paymode.setId(paymod.getId());
			paymode.setCountries(paymod.getCountries().getCountryName());
			paymode.setDescription(paymod.getDescription());
			paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
			//log.info("id is"+paymod.getId());
			//log.info("id isee"+paymode.getId());
			paymentModes.add(paymode);
			
		}
		
		return paymentModes;
    }	
    @RequestMapping(value = "/api/fee/getFeerecieveCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getFeerecieveCode(@PathVariable("id")Long id, @PathVariable("userType")String userType) throws RecordNotFoundException {
 
//log.info(""+id);

      Optional<Countries>  country = countriesRepository.findById(id);    
		Countries countries = new Countries();
		if(country.isPresent())
		{
			countries = country.get();
		}
	//	log.info("Country is"+countries.getCountryName());
		
		List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();

		List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountryAndUserType(PaymentModeType.RECEIVE,countries,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS);
		for(PaymentReceiveMode paymod : paymodes )
		{

			PaymentReceiveModeDTO paymode = new PaymentReceiveModeDTO();
			paymode.setId(paymod.getId());
			paymode.setCountries(paymod.getCountries().getCountryName());
			paymode.setDescription(paymod.getDescription());
			paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
			//log.info("id is"+paymod.getId());
			//log.info("id isee"+paymode.getId());
			paymentModes.add(paymode);
			
		}
		
		return paymentModes;
    }

    @RequestMapping(value = "/fee/{feeId}/approve",method = RequestMethod.GET)
    public ModelAndView approveFee(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("feeId") Long feeId) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/fee/list");
        }
        Optional<FeeMaster> optional = feeMasterService.getFeeById(feeId);
        if(optional.isPresent()) {
            FeeMaster fee = optional.get();
            fee.setPublish(true);
            feeMasterService.approveFee(fee);
        }
        List<FeeMaster> feeList = feeMasterService.listOfFees();
        modelAndView.addObject("fees",feeList);
        return new ModelAndView("redirect:/fee/list");
    }

    @RequestMapping(value = "/fee/{feeId}/reject",method = RequestMethod.GET)
    public ModelAndView rejectFee(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("feeId")Long feeId){
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/fee/list");
        }
        log.debug("feeId "+feeId);
        Optional<FeeMaster> optional = feeMasterService.getFeeById(feeId);
        if(optional.isPresent()) {
            log.debug("O");
            FeeMaster fee = optional.get();
            fee.setPublish(false);
            fee.setIsDeleted(true);
            feeMasterService.rejectFee(fee);
        }
        modelAndView.addObject("confirmationMessage", "FEE Definition has been Rejected!!");
        List<FeeMaster> feeList = feeMasterService.listOfFees();
        modelAndView.addObject("tats",feeList);
        return new ModelAndView("redirect:/fee/list");
    }
}
