package com.adminremit.masters.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.DeliveryPartnerMaster;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.DeliveryPartnerMasterService;
import com.adminremit.masters.service.PaymentReceiveModeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DeliveryPartnerMasterController {
	@Autowired
	private PaymentReceiveModeService paymentReceiveModeService;
	
	@Autowired
	private DeliveryPartnerMasterService dpmService;
	
	 @Autowired
	 private CountriesService countriesService;
	
	@RequestMapping(value = "/deliverypartner", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView, DeliveryPartnerMaster deliveryPartnerMaster) {
		modelAndView.addObject("deliveryPartnerMaster", deliveryPartnerMaster);
		 modelAndView.addObject("countries",countriesService.listOfCountries());
		 modelAndView.addObject("payments",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
		modelAndView.setViewName("masters/deliverypartner/create");
		
		return modelAndView;
	}
		
	@RequestMapping(value = "/deliverypartner/create", method = RequestMethod.POST)
	public ModelAndView createDeliveryPartner(@Valid DeliveryPartnerMaster deliveryPartner, BindingResult bindingResult,
			ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("deliveryPartnerMaster", deliveryPartner);
			 modelAndView.addObject("countries",countriesService.listOfCountries());
			 modelAndView.addObject("payments",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
			modelAndView.setViewName("masters/deliverypartner/create");
			return modelAndView;
		} else {
//			boolean isCountryDuplicate = countriesService.getByCountryCode(countries.getCountryCode());
//			if (!isCountryDuplicate && isCountryDuplicate) {
//				modelAndView.addObject("errorMessage", "Duplicate value!!!");
//				modelAndView.addObject("country", countries);
//				modelAndView.setViewName("masters/country/create");
//				return modelAndView;
//			}

			dpmService.save(deliveryPartner);
			modelAndView.addObject("confirmationMessage", "DeliveryPartnerMaster has been created");
			List<DeliveryPartnerMaster> dpmList = dpmService.listOfdpm();
			modelAndView.addObject("deliveryPartnerList", dpmList);
		}
		return new ModelAndView("redirect:/deliverypartner/list");
	}
	
	
	@RequestMapping(path = { "/deliverypartner/edit", "/deliverypartner/edit/{id}" })
	public ModelAndView editDelivaryPartner(ModelAndView model, @PathVariable("id") Optional<Long> id,
			BindingResult bindingResult) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			model.setViewName("masters/deliverypartner/create");
		}
		log.info("id +"+id);
		if (id.isPresent()) {
			
			DeliveryPartnerMaster dpm = dpmService.getById(id.get());
			log.info("dpm +"+dpm.toString());
			model.addObject("deliveryPartnerMaster", dpm);
			model.addObject("countries",countriesService.listOfCountries());
			model.addObject("payments",paymentReceiveModeService.findAllByType(PaymentModeType.RECEIVE));
			model.setViewName("masters/deliverypartner/create");
		} else {
			model.addObject("deliveryPartnerMaster", new DeliveryPartnerMaster());
			model.setViewName("masters/deliverypartner/create");
		}
		return model;
	}
	
	@RequestMapping(value = "/deliverypartner/list", method = RequestMethod.GET)
	public ModelAndView listDeliverypartner(ModelAndView modelAndView, @PageableDefault(size = 10) Pageable pageable) {
		List<DeliveryPartnerMaster> dpmList = dpmService.listOfdpm();
		modelAndView.addObject("deliveryPartnerList", dpmList);
		modelAndView.setViewName("masters/deliverypartner/list");
		return modelAndView;
	}
	  @RequestMapping(value = "/api/deliverypartner/deleteapi/{Id}",method = RequestMethod.GET)
	    public String deleteDeliverypartner(ModelAndView modelAndView, @PathVariable("Id") Optional<Long> Id) {
		//  dpmService.deleteDelivery(Id.get());
		  List<DeliveryPartnerMaster> dpmList = dpmService.listOfdpm();
			return "Deleted Succefully";
	    }

		


}
