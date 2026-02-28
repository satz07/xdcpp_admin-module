package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.dto.TATRequest;
import com.adminremit.masters.dto.TATResponse;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.GlobalLimitMaster;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.models.TAT;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.repository.CurrenciesRepository;
import com.adminremit.masters.service.*;
import com.github.javaparser.utils.Log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/tat")
public class TATController {

    @Autowired
    TATService tatService;
    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CurrenciesService currenciesService;


    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
	private CurrenciesRepository currenciesRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, TAT tat) {
        tat.setUserType("PERSONAL");
        modelAndView.addObject("tat",tat);
        modelAndView.addObject("paymentMode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
        modelAndView.addObject("receiveMode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
        modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
        modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
        modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
        modelAndView.addObject("paymentHrsList",Arrays.asList(1,2,3,4,5,6,12,24));
        modelAndView.addObject("expiryTimeList",Arrays.asList(12,24,48,72));
        modelAndView.addObject("cancelTimeList",Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        modelAndView.addObject("userTypeList",Arrays.asList("PERSONAL","BUSINESS"));
        modelAndView.setViewName("masters/tat/create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ModelAndView addTAT(@Valid TAT tat,BindingResult bindingResult, ModelAndView modelAndView) {
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("tat",tat);
            modelAndView.addObject("paymentMode",paymentReceiveModeService.findAllByType(PaymentModeType.PAYMENT));
            modelAndView.addObject("receiveMode",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
            modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
            modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
             modelAndView.addObject("paymentHrsList",Arrays.asList(1,2,3,4,5,6,12,24));
            modelAndView.addObject("expiryTimeList",Arrays.asList(12,24,48,72));
            modelAndView.addObject("cancelTimeList",Arrays.asList(1,2,3,4,5,6,7,8,9,10));
            modelAndView.setViewName("masters/tat/create");
            return modelAndView;
        } else  {
             tatService.addTAT(tat);
            modelAndView.addObject("confirmationMessage", "Holiday has been added");
            List<TAT> holidaysList = tatService.listOfTATs();
            modelAndView.addObject("tats", holidaysList);
        }
        return new ModelAndView("redirect:/tat/list");
    }

    @RequestMapping(value = "/{userType}/{tatId}/patch",method = RequestMethod.GET)
    public ModelAndView editTAT(ModelAndView modelAndView, BindingResult bindingResult, @PathVariable("userType") String userType,@PathVariable("tatId") Long tatId ) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/tat/create");
        }
        Optional<TAT> optionalTAT = tatService.getTATById(tatId);
        if(optionalTAT.isPresent()) {
            TAT tat = optionalTAT.get();
            modelAndView.addObject("tat",tat);
            modelAndView.addObject("paymentMode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.PAYMENT,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            modelAndView.addObject("receiveMode",paymentReceiveModeService.findAllByTypeAndUserType(PaymentModeType.RECEIVE,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS));
            modelAndView.addObject("countries",countriesService.listOfCountriesCountryCode());
            modelAndView.addObject("currencies",currenciesService.listOfCurrenciesCurrencyCode());
            modelAndView.addObject("partnerName",partnerService.listOfPartnersPartnerName());
            modelAndView.addObject("paymentHrsList",Arrays.asList(1,2,3,4,5,6,12,24));
            modelAndView.addObject("expiryTimeList",Arrays.asList(12,24,48,72));
            modelAndView.addObject("cancelTimeList",Arrays.asList(1,2,3,4,5,6,7,8,9,10));
            modelAndView.addObject("userTypeList",Arrays.asList("PERSONAL","BUSINESS"));
            modelAndView.setViewName("masters/tat/create");

        } else {
            modelAndView.addObject("tat", new TAT());
            modelAndView.setViewName("masters/tat/create");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getTATs(ModelAndView modelAndView){
        List<TAT> tatList = tatService.listOfTATs();
        modelAndView.addObject("tats",tatList);
        modelAndView.setViewName("masters/tat/list");
        return modelAndView;
    }



    @RequestMapping(value = "/delete/{tatId}",method = RequestMethod.GET)
    public ModelAndView deleteTAT(ModelAndView modelAndView,@PathVariable("tatId") Optional<Long> tatId) {
    	Log.info("TAT ID"+tatId);
        tatService.deleteTAT(tatId.get());
        modelAndView.addObject("confirmationMessage", "tat has been deleted!!");
        return new ModelAndView ("redirect:/tat/list");
    }


    @RequestMapping(value = "/checkerlist", method = RequestMethod.GET)
    public ModelAndView getCheckerTATs(ModelAndView modelAndView){
        List<TAT> tatList = tatService.listOfCheckerTATs();
        modelAndView.addObject("tats",tatList);
        modelAndView.setViewName("masters/tat/checkerlist");
        return modelAndView;
    }


    @RequestMapping(value = "/{tatId}/approve",method = RequestMethod.GET)
    public ModelAndView approveHolidays(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("tatId") Long tatId) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/tat/checkerlist");
        }
        Optional<TAT> optional = tatService.getTATById(tatId);
        if(optional.isPresent()) {
            TAT tat = optional.get();
            tat.setPublish(true);
            tatService.approveTAT(tat);
        }
        List<TAT> tatList = tatService.listOfTATs();
        modelAndView.addObject("tats",tatList);
        //modelAndView.setViewName("masters/tat/checkerlist");
        //return modelAndView;
        return new ModelAndView("redirect:/tat/list");
    }

    @RequestMapping(value = "/{tatId}/reject",method = RequestMethod.GET)
    public ModelAndView rejectHoliday(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("tatId")Long tatId){
    	  if(bindingResult.hasErrors()) {
              modelAndView.setViewName("masters/tat/checkerlist");
          }
    	  log.debug("tatId "+tatId);
        Optional<TAT> optional = tatService.getTATById(tatId);
        if(optional.isPresent()) {
            log.debug("O");
            TAT tat = optional.get();
            tat.setPublish(false);
            tat.setIsDeleted(true);
            tatService.rejectTAT(tat);
        }
        modelAndView.addObject("confirmationMessage", "TAT Definition has been Rejected!!");
        List<TAT> tatList = tatService.listOfTATs();
        modelAndView.addObject("tats",tatList);
        //modelAndView.setViewName("masters/tat/checkerlist");
       // return modelAndView;
        return new ModelAndView("redirect:/tat/list");
    }
    @RequestMapping(value = "/api/gettatpaymentCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getTatpaymentCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {
 
     
      Optional<Currencies>  currency = currenciesRepository.findById(id);    
      Currencies currencies= new Currencies();
		if(currency.isPresent())
		{
			currencies = currency.get();
		}
		//log.info("Country is"+countries.getCountryName());

        Optional<Countries> country = countriesRepository.findByCountryCodeAndIsDeleted(currencies.getCountryCode(),false);
        Countries countries = new Countries();
        if(country.isPresent())
        {
            countries = country.get();
        }
		List<PaymentReceiveModeDTO> paymentModes = new ArrayList<PaymentReceiveModeDTO>();		
		List<PaymentReceiveMode> paymodes = paymentReceiveModeService.findAllByTypeAndcountryAndUserType(PaymentModeType.PAYMENT,countries,(UserType.PERSONAL.toString()).equals(userType) ? UserType.PERSONAL : UserType.BUSINESS);
		for(PaymentReceiveMode paymod : paymodes )
		{

			PaymentReceiveModeDTO paymode = new PaymentReceiveModeDTO();
			paymode.setId(paymod.getId());
			paymode.setCountries(paymod.getCountries().getCountryName());
			paymode.setCurrencies(paymod.getCurrencies().getCurrencyCode());
			paymode.setDescription(paymod.getDescription());
			paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
		//	log.info("id is"+paymod.getId());
			//log.info("id isee"+paymode.getId());
			paymentModes.add(paymode);
			
		}
		
		return paymentModes;
    }	
    @RequestMapping(value = "/api/gettatrecieveCode/{id}/{userType}", method = RequestMethod.GET)
   	public @ResponseBody List<PaymentReceiveModeDTO> getTatrecieveCode(@PathVariable("id")Long id,@PathVariable("userType")String userType) throws RecordNotFoundException {

     
      Optional<Currencies>  currency = currenciesRepository.findById(id);    
      Currencies currencies= new Currencies();
		if(currency.isPresent())
		{
			currencies = currency.get();
		}
		//log.info("Country is"+countries.getCountryName());

        Optional<Countries> country = countriesRepository.findByCountryCodeAndIsDeleted(currencies.getCountryCode(),false);

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
			paymode.setCurrencies(paymod.getCurrencies().getCurrencyCode());
			paymode.setDescription(paymod.getDescription());
			paymode.setPaymentCodeMaster(paymod.getPaymentCodeMaster().getPaymentCode());
		//	log.info("id is"+paymod.getId());
			//log.info("id isee"+paymode.getId());
			paymentModes.add(paymode);
			
		}
		
		return paymentModes;
    }	
    @RequestMapping(value = "/gettatpaymentcodebyIdapi/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getGlobalCurrencyCodeByID(@PathVariable Long id)
			throws RecordNotFoundException {

    	TAT recieve = new TAT();
			recieve = 	tatService.getById(id);
		
		
		PaymentReceiveMode paymentmode = recieve.getPaymentMode();
			
		PaymentReceiveMode recievemode = recieve.getReceiveMode();
		CurrencyApiResponse caResp = new CurrencyApiResponse();
		List<CurrencyApiResponse> currencyapiresponse = new ArrayList<CurrencyApiResponse>();

	    caResp.setPaymentCode(paymentmode.getPaymentCodeMaster().getPaymentCode());
	    caResp.setPaymentCodeId(paymentmode.getId());
		caResp.setPaymentCodedescription(paymentmode.getDescription());
	    caResp.setReceiveCode(recievemode.getPaymentCodeMaster().getPaymentCode());
	    caResp.setReceiveCodeId(recievemode.getId());
	    caResp.setReceiveCodeCodedescription(recievemode.getDescription());
		currencyapiresponse.add(caResp);
		return currencyapiresponse;
	}
	
	  @RequestMapping(value = "/api/deleteapi/{id}", method = RequestMethod.GET)
	  public @ResponseBody String deleteTATById(@PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
		  log.info("id::"+id.toString());
	  //tatService.deleteTAT(id.get());
	  tatService.deleteTAT(id.get());
	  List<TAT> tatList = tatService.listOfTATs();
	  return "Deleted Succesfylly";
	}
	 
}
