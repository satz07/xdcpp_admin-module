package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.models.ServiceChargeMaster;
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

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceChargeMasterController {

    @Autowired
    private ServiceChargeMasterService serviceChargeMasterService;

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
    

    @RequestMapping(value = "/service/charge",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, ServiceChargeMaster serviceChargeMaster) {
        modelAndView.addObject("service",serviceChargeMaster);
        modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());

        modelAndView.setViewName("masters/servicecharge/create");
        return modelAndView;
    }

    @RequestMapping(value = "/service/charge/create", method = RequestMethod.POST)
    public ModelAndView createServiceCharge(@Valid ServiceChargeMaster serviceChargeMaster, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
       
    	if (bindingResult.hasErrors()) {
            modelAndView.addObject("service",serviceChargeMaster);
            modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
            modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
            modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
            modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
            modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());

            modelAndView.addObject("errorMessage", "Please fill mandatory fields!!!");
            modelAndView.setViewName("masters/servicecharge/create");
            return modelAndView;
        } else {
            boolean isExist = serviceChargeMasterService.getExistingFeeByConditions(serviceChargeMaster);
            if(isExist && serviceChargeMaster.getId() == null) {
                serviceChargeMaster.setPublish(true);
                modelAndView.addObject("service",serviceChargeMaster);
                modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
                modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
                modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
                modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
                modelAndView.addObject("errorMessage", "Duplicate values!!");
                modelAndView.setViewName("masters/servicecharge/create");
                return modelAndView;
            }

            boolean isExistById = serviceChargeMasterService.getExistingFeeByConditionsById(serviceChargeMaster);
            if(isExistById && serviceChargeMaster.getId() != null) {
                serviceChargeMaster.setPublish(true);
                modelAndView.addObject("service",serviceChargeMaster);
                modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
                modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
                modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
                modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
                modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
                modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
                modelAndView.addObject("errorMessage", "Duplicate values!!");
                modelAndView.setViewName("masters/servicecharge/create");
                return modelAndView;
            }
            serviceChargeMasterService.save(serviceChargeMaster);
            modelAndView.addObject("confirmationMessage", "Service Charge has been created");
            List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService.listOfServiceCharge();
            modelAndView.addObject("servicecharges", serviceChargeMasters);
        }
        return new ModelAndView("redirect:/service/charge/list");
    }

    @RequestMapping(path = {"/service/charge/edit", "/service/charge/edit/{id}"})
    public ModelAndView editServiceCharge(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/servicecharge/create");
        }
        if (id.isPresent()) {
            ServiceChargeMaster serviceChargeMaster = serviceChargeMasterService.getById(id.get());
            model.addObject("service",serviceChargeMaster);
            model.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
            model.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
            model.addObject("userCategory", Arrays.asList(UserCategory.values()));
            model.addObject("countries",countriesService.listOfCountriesCountryCode());
            model.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            model.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            model.setViewName("masters/servicecharge/create");
        } else {
            model.addObject("service", new ServiceChargeMaster());
            model.setViewName("masters/servicecharge/create");
        }
        return model;
    }

    @RequestMapping(value = "/service/charge/list", method = RequestMethod.GET)
    public ModelAndView listServiceCharge(ModelAndView modelAndView) {
        List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService.listOfServiceCharge();
        modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.addObject("servicecharges", serviceChargeMasters);
        modelAndView.setViewName("masters/servicecharge/list");
        return modelAndView;
    }

    @RequestMapping(value = "/service/charge/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteServiceCharge(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        serviceChargeMasterService.deleteServiceCharge(id.get());
        modelAndView.addObject("confirmationMessage", "Service Charge has been deleted!!");
        List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService.listOfServiceCharge();
        modelAndView.addObject("paymentCode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("userCategory", Arrays.asList(UserCategory.values()));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.addObject("servicecharges", serviceChargeMasters);
        modelAndView.setViewName("masters/servicecharge/list");
        return modelAndView;
    }
    @RequestMapping(value = "/api/service/charge/getservicecurrencyCode/{countryid}", method = RequestMethod.GET)
   	public @ResponseBody List<CurrencyApiResponse> getServiceCurrencyCode(@PathVariable("countryid") Long id) throws RecordNotFoundException {
   //log.info("Country name is:"+countryName);
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
    @RequestMapping(value = "/api/service/charge/getServicepaymentCode/{id}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getservicepaymentCode(@PathVariable("id")Long id) throws RecordNotFoundException {
 
//log.info(""+id);

      Optional<Countries>  country = countriesRepository.findById(id);    
		Countries countries = new Countries();
		if(country.isPresent())
		{
			countries = country.get();
		}
		//log.info("Country is"+countries.getCountryName());
		
		List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
		List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountry(PaymentModeType.PAYMENT,countries);
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

@RequestMapping(value = "/api/service/charge/getServiceRecieveCode/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PaymentReceiveModeDTO> getservicerecieveCode(@PathVariable("id")Long id) throws RecordNotFoundException {

//log.info(""+id);

  Optional<Countries>  country = countriesRepository.findById(id);    
	Countries countries = new Countries();
	if(country.isPresent())
	{
		countries = country.get();
	}
	//log.info("Country is"+countries.getCountryName());
	
	List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
	List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountry(PaymentModeType.RECEIVE,countries);
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
}
