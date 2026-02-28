package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class MarginController {

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private MarginMasterService marginMasterService;

    @Autowired
    private PaymentCodeMasterService paymentCodeMasterService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private CountriesRepository countriesRepository;

    @RequestMapping(value = "/margin",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, MarginMaster marginMaster) {
        marginMaster.setUserType(UserType.PERSONAL);
        modelAndView.addObject("margin",marginMaster);
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountries());
        modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
        modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveModeCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.setViewName("masters/margin/create");
        return modelAndView;
    }

    @RequestMapping(value = "/margin/create", method = RequestMethod.POST)
    public ModelAndView createMargin(@Valid MarginMaster marginMaster, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("margin",marginMaster);
            modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
            modelAndView.addObject("countries",countriesService.listOfCountries());
            modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
            modelAndView.addObject("errorMessage", "Please fill mandatory fields!!!");
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            modelAndView.setViewName("masters/margin/create");
            return modelAndView;
        } else {
            boolean isExist = marginMasterService.getExistingFeeByConditions(marginMaster);
            if(isExist && marginMaster.getId() == null) {
                marginMaster.setPublish(true);
                modelAndView.addObject("margin",marginMaster);
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountries());
                modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
                modelAndView.addObject("errorMessage", "Duplicate Record!!!");
                modelAndView.setViewName("masters/margin/create");
                return modelAndView;
            }
            boolean isExistById = marginMasterService.getExistingFeeByConditionsById(marginMaster);
            if(isExistById && marginMaster.getId() != null) {
                marginMaster.setPublish(true);
                modelAndView.addObject("margin",marginMaster);
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountries());
                modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
                modelAndView.addObject("errorMessage", "Duplicate Record!!!");
                modelAndView.setViewName("masters/margin/create");
                return modelAndView;
            }
            marginMasterService.save(marginMaster);
            modelAndView.addObject("confirmationMessage", "Margin has been created");
            List<MarginMaster> marginMasters = marginMasterService.listOfMargins();
            modelAndView.addObject("margins", marginMasters);
        }
        return new ModelAndView("redirect:/margin/list");
    }

    @RequestMapping(path = {"/margin/edit", "/margin/edit/{userType}/{id}"})
    public ModelAndView editMargin(ModelAndView model, @PathVariable("userType") Optional<String> userType,@PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/margin/create");
        }
        if (id.isPresent()) {
            MarginMaster marginMaster = marginMasterService.getById(id.get());
            model.addObject("margin",marginMaster);
            model.addObject("userCategory", Arrays.asList(UserCategory.values()));
            model.addObject("countries",countriesService.listOfCountries());
            model.addObject("currencies",currenciesService.listOfCurrencies());
            model.addObject("paymentCode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.PAYMENT,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            model.addObject("receiveModeCode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.RECEIVE,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            model.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            model.setViewName("masters/margin/create");
        } else {
            model.addObject("margin", new MarginMaster());
            model.setViewName("masters/margin/create");
        }
        return model;
    }

    @RequestMapping(value = "/margin/list", method = RequestMethod.GET)
    public ModelAndView listMargins(ModelAndView modelAndView) {
        List<MarginMaster> marginMasters = marginMasterService.listOfMargins();
        modelAndView.addObject("margins", marginMasters);
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
        modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.setViewName("masters/margin/list");
        return modelAndView;
    }

    @RequestMapping(value = "/margin/delete/{id}", method = RequestMethod.GET)
    public String deleteMargin(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        marginMasterService.deleteMargin(id.get());
        modelAndView.addObject("confirmationMessage", "Margin has been deleted!!");
        return "redirect:/margin/list";
    }
    @RequestMapping(value = "/api/margin/getMargincurrencyCode/{countryid}", method = RequestMethod.GET)
   	public @ResponseBody List<CurrencyApiResponse> getSendCurrencyCode(@PathVariable("countryid") Long id) throws RecordNotFoundException {
   //log.info("Country name is:"+countryName);
   /*
    * String[] parts = countryName.split("-"); String splitCountryName = parts[1];
    */

    	//log.info("Country id is:" + id);
        Countries country = new Countries();
        
		List<Currencies> currencyCode = null;
		country = countriesService.getById(id);
	//	log.info("country code"+country.getCountryCode());
		CurrencyApiResponse currObj = new CurrencyApiResponse();
		List<CurrencyApiResponse> currList = new ArrayList<CurrencyApiResponse>();
		
		currencyCode = currenciesService.getByCountryCodeForCurrencyCode(country.getCountryCode());
		//log.info(currencyCode.get(0).getCurrencyCode());
		
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
      //Modified
    @RequestMapping(value = "/getmargincurrencycodebyIdapi/{id}", method = RequestMethod.GET)
   	public @ResponseBody List<CurrencyApiResponse> getMarginCurrencyCodeByID(@PathVariable Long id) throws RecordNotFoundException {
   	
   		
   		MarginMaster recieve = new MarginMaster();
   			recieve = 	marginMasterService.getById(id);
   	Currencies currencyrecieve = recieve.getReceiveCurrencyCode();
   	
   	Currencies currencysend = recieve.getSendCurrencyCode();
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
    @RequestMapping(value = "/api/margin/getMarginpaymentCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getmarginpaymentCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {
 
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
    @RequestMapping(value = "/api/margin/getMarginrecieveCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getMarginrecieveCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {
 


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

    @RequestMapping(value = "/margin/{marginId}/approve",method = RequestMethod.GET)
    public ModelAndView approveMargin(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("marginId") Long marginId) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/margin/list");
        }
        Optional<MarginMaster> optional = marginMasterService.getMarginById(marginId);
        if(optional.isPresent()) {
            MarginMaster margin = optional.get();
            margin.setPublish(true);
            marginMasterService.approveMargin(margin);
        }
        List<MarginMaster> marginList = marginMasterService.listOfMargins();
        modelAndView.addObject("margins",marginList);
        return new ModelAndView("redirect:/margin/list");
    }

    @RequestMapping(value = "/margin/{marginId}/reject",method = RequestMethod.GET)
    public ModelAndView rejectMargin(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("marginId")Long marginId){
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/margin/list");
        }
        Optional<MarginMaster> optional = marginMasterService.getMarginById(marginId);
        if(optional.isPresent()) {
            MarginMaster margin = optional.get();
            margin.setPublish(false);
            margin.setIsDeleted(true);
            marginMasterService.rejectMargin(margin);
        }
        modelAndView.addObject("confirmationMessage", "Margin Definition has been Rejected!!");
        List<MarginMaster> marginList = marginMasterService.listOfMargins();
        modelAndView.addObject("margins",marginList);
        return new ModelAndView("redirect:/margin/list");
    }
}
