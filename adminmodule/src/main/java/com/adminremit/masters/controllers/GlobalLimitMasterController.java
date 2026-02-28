package com.adminremit.masters.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
import com.adminremit.masters.service.GlobalLimitMasterService;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.masters.service.PaymentReceiveModeService;

@Controller
public class GlobalLimitMasterController {

	@Autowired
	private GlobalLimitMasterService globalLimitMasterService;

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
    
	@RequestMapping(value = "/global", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView, GlobalLimitMaster globalLimitMaster) {
		globalLimitMaster.setUserType(UserType.PERSONAL);
		modelAndView.addObject("global", globalLimitMaster);
		modelAndView.addObject("paymentCode", paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
		modelAndView.addObject("receiveCode", paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
		modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());

		modelAndView.setViewName("masters/global/create");
		return modelAndView;
	}

	@RequestMapping(value = "/global/create", method = RequestMethod.POST)
	public ModelAndView createGlobalLimit(@Valid GlobalLimitMaster globalLimitMaster, BindingResult bindingResult,
			ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("global", globalLimitMaster);
			modelAndView.addObject("paymentCode", paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
			modelAndView.addObject("receiveCode", paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
			modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
			modelAndView.addObject("errorMessage", "Please fill mandatory fields!!!");
			modelAndView.setViewName("masters/global/create");
			return modelAndView;
		} else {
			boolean isExist = globalLimitMasterService.getExistingFeeByConditions(globalLimitMaster);
			if (isExist && globalLimitMaster.getId() == null) {
				globalLimitMaster.setPublish(true);
				modelAndView.addObject("global", globalLimitMaster);
				modelAndView.addObject("paymentCode", paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
				modelAndView.addObject("receiveCode", paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
				modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
				modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
				modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
				modelAndView.addObject("errorMessage", "Duplicate values!!");
				modelAndView.setViewName("masters/global/create");
				return modelAndView;
			}

			boolean isExistById = globalLimitMasterService.getExistingFeeByConditionsById(globalLimitMaster);
			if (isExistById && globalLimitMaster.getId() != null) {
				globalLimitMaster.setPublish(true);
				modelAndView.addObject("global", globalLimitMaster);
				modelAndView.addObject("paymentCode", paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
				modelAndView.addObject("receiveCode", paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
				modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
				modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
				modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
				modelAndView.addObject("errorMessage", "Duplicate values!!");
				modelAndView.setViewName("masters/global/create");
				return modelAndView;
			}
			globalLimitMasterService.save(globalLimitMaster);
			modelAndView.addObject("confirmationMessage", "Global Limit has been created");
		}
		return new ModelAndView("redirect:/global/list");
	}

	@RequestMapping(path = { "/global/edit", "/global/edit/{userType}/{id}" })
	public ModelAndView editGlobalLimit(ModelAndView model,@PathVariable("userType") Optional<String> userType, @PathVariable("id") Optional<Long> id,
			BindingResult bindingResult) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			model.setViewName("masters/global/create");
		}
		if (id.isPresent()) {
			GlobalLimitMaster globalLimitMaster = globalLimitMasterService.getById(id.get());
			model.addObject("global", globalLimitMaster);
			model.addObject("paymentCode", paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.PAYMENT,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
			model.addObject("receiveCode", paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.RECEIVE,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
			model.addObject("userCategory", Arrays.asList(UserCategory.values()));
			model.addObject("countries", countriesService.listOfCountriesCountryCode());
			model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			model.addObject("partnerName",partnerService.listOfPartnersPartnerName());
			model.setViewName("masters/global/create");
		} else {
			model.addObject("global", new GlobalLimitMaster());
			model.setViewName("masters/global/create");
		}
		return model;
	}

	@RequestMapping(value = "/global/list", method = RequestMethod.GET)
	public ModelAndView listGlobalLimits(ModelAndView modelAndView) {
		List<GlobalLimitMaster> globalLimitMasters = globalLimitMasterService.listOfGlobalLimits();
		modelAndView.addObject("paymentCode", paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
		modelAndView.addObject("receiveCode", paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
		modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
		modelAndView.addObject("globals", globalLimitMasters);
		modelAndView.setViewName("masters/global/list");
		return modelAndView;
	}

	@RequestMapping(value = "/global/delete/{id}", method = RequestMethod.GET)
	public String deleteGlobalLimits(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		globalLimitMasterService.deleteGlobalLimits(id.get());
		modelAndView.addObject("confirmationMessage", "Global Limit has been deleted!!");
		return "redirect:/global/list";
	}

	// modified
	@RequestMapping(value = "/api/global/getglobalcurrencyCode/{countryid}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getGlobalCurrencyCode(@PathVariable("countryid") Long id)
			throws RecordNotFoundException {
		// log.info("Country name is:"+countryName);
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

	@RequestMapping(value = "/getglobalcurrencycodebyIdapi/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getGlobalCurrencyCodeByID(@PathVariable Long id)
			throws RecordNotFoundException {

		GlobalLimitMaster recieve = new GlobalLimitMaster();
		recieve = globalLimitMasterService.getById(id);
		Currencies currencyrecieve = recieve.getReceiveCurrencyCode();

		Currencies currencysend = recieve.getSendCurrencyCode();
		
		PaymentReceiveMode paymentmode = recieve.getPaymentCode();
	
		PaymentReceiveMode recievemode = recieve.getReceiveCode();
		CurrencyApiResponse caResp = new CurrencyApiResponse();
		List<CurrencyApiResponse> currencyapiresponse = new ArrayList<CurrencyApiResponse>();

		caResp.setId(recieve.getReceiveCurrencyCode().getId());
		caResp.setSendCurrencyCode(currencysend.getCurrencyCode());
		caResp.setReceiveCurrencyId(currencyrecieve.getId());
		caResp.setSendCurrencyId(currencysend.getId());
		caResp.setReceiveCurrencyCode(currencyrecieve.getCurrencyCode());
		caResp.setSendCurrencyName(currencyrecieve.getCurrencyName());
		caResp.setReceiveCountryName(currencyrecieve.getCurrencyName());
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
	 @RequestMapping(value = "/api/global/getglobalPaymentCode/{id}/{userType}", method = RequestMethod.GET)
	   	public @ResponseBody List<PaymentReceiveModeDTO> getglobalpaymentCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {
	 
	//log.info(""+id);

	      Optional<Countries>  country = countriesRepository.findById(id);    
			Countries countries = new Countries();
			if(country.isPresent())
			{
				countries = country.get();
			}
			//log.info("Country is"+countries.getCountryName());
			
			List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
			List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountryAndUserType(PaymentModeType.PAYMENT,countries,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS);
			for(PaymentReceiveMode paymod : paymodes )
			{

				PaymentReceiveModeDTO paymode = new PaymentReceiveModeDTO();
				paymode.setId(paymod.getId());
				paymode.setCountries(paymod.getCountries().getCountryName());
				paymode.setDescription(paymod.getDescription());
				paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
				/*
				 * log.info("id is"+paymod.getId()); log.info("id isee"+paymode.getId());
				 */
				paymentModes.add(paymode);
				
			}
			
			return paymentModes;
	    }	
	 @RequestMapping(value = "/api/global/getglobalRecieveCode/{id}/{userType}", method = RequestMethod.GET)
	   	public @ResponseBody List<PaymentReceiveModeDTO> getMarginRecieveCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {
	 


	      Optional<Countries>  country = countriesRepository.findById(id);    
			Countries countries = new Countries();
			if(country.isPresent())
			{
				countries = country.get();
			}
			
			List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
			List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountryAndUserType(PaymentModeType.RECEIVE,countries,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS);
			for(PaymentReceiveMode paymod : paymodes )
			{

				PaymentReceiveModeDTO paymode = new PaymentReceiveModeDTO();
				paymode.setId(paymod.getId());
				paymode.setCountries(paymod.getCountries().getCountryName());
				paymode.setDescription(paymod.getDescription());
				paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
				/*
				 * log.info("id is"+paymod.getId()); log.info("id isee"+paymode.getId());
				 */
				paymentModes.add(paymode);
				
			}
			
			return paymentModes;
	    }


	@RequestMapping(value = "/global/{globalId}/approve",method = RequestMethod.GET)
	public ModelAndView approveGlobalLimit(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("globalId") Long globalId) {
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("masters/global/list");
		}
		Optional<GlobalLimitMaster> optional = globalLimitMasterService.getGlobalLimitById(globalId);
		if(optional.isPresent()) {
			GlobalLimitMaster globalLimit = optional.get();
			globalLimit.setPublish(true);
			globalLimitMasterService.approveGlobalLimit(globalLimit);
	}
		List<GlobalLimitMaster> globalLimitList = globalLimitMasterService.listOfGlobalLimits();
		modelAndView.addObject("globals",globalLimitList);
		return new ModelAndView("redirect:/global/list");
	}

	@RequestMapping(value = "/global/{globalId}/reject",method = RequestMethod.GET)
	public ModelAndView rejectGlobalLimit(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("globalId")Long globalId){
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("masters/global/list");
		}
		Optional<GlobalLimitMaster> optional = globalLimitMasterService.getGlobalLimitById(globalId);
		if(optional.isPresent()) {
			GlobalLimitMaster globalLimits = optional.get();
			globalLimits.setPublish(false);
			globalLimits.setIsDeleted(true);
			globalLimitMasterService.rejectGlobalLimits(globalLimits);
		}
		modelAndView.addObject("confirmationMessage", "Global limit Definition has been Rejected!!");
		List<GlobalLimitMaster> globalLimitList = globalLimitMasterService.listOfGlobalLimits();
		modelAndView.addObject("globals",globalLimitList);
		return new ModelAndView("redirect:/global/list");
	}
}
