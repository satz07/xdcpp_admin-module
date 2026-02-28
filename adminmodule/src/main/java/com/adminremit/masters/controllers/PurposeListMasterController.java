package com.adminremit.masters.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.adminremit.masters.dto.CountryApiResponse;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.masters.service.PaymentReceiveModeService;
import com.adminremit.masters.service.PurposeListMasterService;
import com.adminremit.masters.service.ReceiverTypeService;
import com.adminremit.masters.service.StatusMasterService;

@Controller
public class PurposeListMasterController {

    private static final Logger LOG = LoggerFactory.getLogger(PurposeListMasterController.class);

    @Autowired
    private StatusMasterService statusMasterService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private PaymentCodeMasterService paymentCodeMasterService;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private PurposeListMasterService purposeListMasterService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private ReceiverTypeService receiverTypeService;
    
    @Autowired
    private PartnerService partnerService;
    
    @Autowired
    private CountriesRepository countriesRepository;    


    @RequestMapping(value = "/purpose", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, PurposeListMaster purposeListMaster) {
        LOG.info("Inside the Purpose list");
        purposeListMaster.setUserType(UserType.PERSONAL);
        modelAndView.addObject("purposeListMaster",purposeListMaster);
        modelAndView.addObject("purposes", "");
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("receiverTypes",receiverTypeService.getAllReceiverType());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());

        modelAndView.setViewName("masters/purpose/create");
        return modelAndView;
    }

    @RequestMapping(value = "/purpose/create", method = RequestMethod.POST)
    public ModelAndView createPurpose(@Valid PurposeListMaster purposeListMaster, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
        LOG.info("Inside the Purpose list Post");
        if (bindingResult.hasErrors()) {
            LOG.info("Inside the Purpose list Post Error   "+bindingResult.toString());
            List<ReceiverType> receiverTypes = receiverTypeService.getAllReceiverType();
            modelAndView.addObject("purposeListMaster",purposeListMaster);
            modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
            modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            modelAndView.addObject("purposes", "");
            modelAndView.addObject("receiverTypes",receiverTypeService.getAllReceiverType());
            modelAndView.setViewName("masters/purpose/create");
            return modelAndView;
        } else {
            purposeListMasterService.save(purposeListMaster);
            modelAndView.addObject("confirmationMessage", "Purpose has been created");
            List<PurposeListMaster> purposeListMasters = purposeListMasterService.listOfPurposeList();
            modelAndView.addObject("purposeListMasters", purposeListMaster);
        }
        return new ModelAndView("redirect:/purpose/list");
    }

    @RequestMapping(path = {"/purpose/edit", "/purpose/edit/{userType}/{id}"})
    public ModelAndView editPurpose(ModelAndView model,@PathVariable("userType") Optional<String> userType, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/purpose/create");
        }
        if (id.isPresent()) {
            List<ReceiverType> receiverTypes = receiverTypeService.getAllReceiverType();
            PurposeListMaster purposeListMaster = purposeListMasterService.getById(id.get());
            model.addObject("countries",countriesService.listOfCountriesCountryCode());
            model.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            model.addObject("receiveCode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.RECEIVE,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            model.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            model.addObject("purposeListMaster",purposeListMaster);
            model.addObject("purposes", "");
            model.addObject("receiverTypes",receiverTypes);
            model.setViewName("masters/purpose/create");
        } else {
            model.addObject("purposeListMaster",new PurposeListMaster());
            model.setViewName("masters/purpose/create");
        }
        return model;
    }

    @RequestMapping(value = "/purpose/list", method = RequestMethod.GET)
    public ModelAndView listStatus(ModelAndView modelAndView) {
        List<PurposeListMaster> purposeListMasters = purposeListMasterService.listOfPurposeList();
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("receiveCode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("purposeListMasters", purposeListMasters);
        modelAndView.setViewName("masters/purpose/list");
        return modelAndView;
    }

    @RequestMapping(value = "/purpose/delete/{id}", method = RequestMethod.GET)
    public String deleteStatus(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        purposeListMasterService.deletePurposeList(id.get());
        modelAndView.addObject("confirmationMessage", "Purpose has been deleted!!");
        return "redirect:/purpose/list";
    }
    @RequestMapping(value = "/api/purpose/getpurposecurrencyCode/{countryCode}", method = RequestMethod.GET)
   	public @ResponseBody List<CurrencyApiResponse> getPurposeCurrencyCode(@PathVariable("countryCode") Long countryCode) throws RecordNotFoundException {
    	
        Countries country = new Countries();
        
		List<Currencies> currencyCode = null;
		country = countriesService.getById(countryCode);
	
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
   //modified
    @RequestMapping(value = "/getpurposecurrencycodebyIdapi/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getPurposeCurrencyCodeByID(@PathVariable Long id) throws RecordNotFoundException {
		// log.info("country name is:" + id);
		// List<Currencies> countryCode =
		// currenciesService.getCountryCodeByCountryName(id);
		PurposeListMaster recieve = new PurposeListMaster();
			recieve = 	purposeListMasterService.getById(id);
	Currencies currency = recieve.getCurrencyCode();
	PaymentReceiveMode recievemode = recieve.getReceiveCode();
	//log.info("currency"+currency);
		CurrencyApiResponse caResp = new CurrencyApiResponse();
		List<CurrencyApiResponse> currencyapiresponse = new ArrayList<CurrencyApiResponse>();
		
		caResp.setId( recieve.getCurrencyCode().getId());
		caResp.setCurrencyName( recieve.getCurrencyCode().getCurrencyName());
		caResp.setCurrencyCode( recieve.getCurrencyCode().getCurrencyCode());
		caResp.setReceiveCode(recievemode.getPaymentCodeMaster().getPaymentCode());
	    caResp.setReceiveCodeId(recieve.getReceiveCode().getId());
	    caResp.setReceiveCodeCodedescription(recievemode.getDescription());
		currencyapiresponse.add(caResp);
		return currencyapiresponse;
	}
    
    @RequestMapping(value = "/api/purpose/getpurposePaymentCode/{id}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getglobalpaymentCode(@PathVariable("id")Long id) throws RecordNotFoundException {
 
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
 @RequestMapping(value = "/api/purpose/getpurposeRecieveCode/{id}/{userType}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/purpose/{purposeId}/approve",method = RequestMethod.GET)
    public ModelAndView approvePurpose(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("purposeId") Long purposeId) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/purpose/list");
        }
        Optional<PurposeListMaster> optional = purposeListMasterService.getPurposeById(purposeId);
        if(optional.isPresent()) {
            PurposeListMaster purpose = optional.get();
            purpose.setPublish(true);
            purposeListMasterService.approvePurpose(purpose);
        }
        List<PurposeListMaster> purposeList = purposeListMasterService.listOfPurposeList();
        modelAndView.addObject("purposeListMasters",purposeList);
        return new ModelAndView("redirect:/purpose/list");
    }

    @RequestMapping(value = "/purpose/{purposeId}/reject",method = RequestMethod.GET)
    public ModelAndView rejectPurpose(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("purposeId")Long purposeId){
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/purpose/list");
        }
        Optional<PurposeListMaster> optional = purposeListMasterService.getPurposeById(purposeId);
        if(optional.isPresent()) {
            PurposeListMaster purpose = optional.get();
            purpose.setPublish(false);
            purpose.setIsDeleted(true);
            purposeListMasterService.rejectPurpose(purpose);
        }
        modelAndView.addObject("confirmationMessage", "Purpose Definition has been Rejected!!");
        List<PurposeListMaster> purposeList = purposeListMasterService.listOfPurposeList();
        modelAndView.addObject("purposeListMasters",purposeList);
        return new ModelAndView("redirect:/purpose/list");
    }
}
