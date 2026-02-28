package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.dto.CountryApiResponse;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.PaymentReceiveModeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class CurrenciesController {

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private CountriesService countriesService;

	@Autowired
	private PaymentReceiveModeService paymentReceiveModeService;

	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView, Currencies currencies) {
		modelAndView.addObject("currency", currencies);
		List<Countries> countries = countriesService.listOfCountries();
		modelAndView.addObject("countries", countries);
		modelAndView.setViewName("masters/currency/create");
		return modelAndView;
	}

	@RequestMapping(value = "/currency/create", method = RequestMethod.POST)
	public ModelAndView createCurrency(@Valid Currencies currencies, BindingResult bindingResult,
			ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
		if (bindingResult.hasErrors()) {
			List<Countries> countries = countriesService.listOfCountries();
			modelAndView.addObject("countries", countries);
			modelAndView.addObject("currency", currencies);
			modelAndView.setViewName("masters/currency/create");
			return modelAndView;
		} else {

			if (currencies.getId() == null) {
				boolean isCurrencyDuplicate = currenciesService.isCurrencyDuplicate(currencies.getCurrencyCode(), currencies.getCountryCode());
				if (isCurrencyDuplicate) {
					List<Countries> countries = countriesService.listOfCountries();
					modelAndView.addObject("countries", countries);
					modelAndView.addObject("currency", currencies);
					modelAndView.addObject("errorMessage", "Duplicate value!!!");
					modelAndView.setViewName("masters/currency/create");
					return modelAndView;
				}

				boolean isPresent = currenciesService.isCurrencyPresent(currencies);
				if (isPresent) {
					List<Countries> countries = countriesService.listOfCountries();
					modelAndView.addObject("countries", countries);
					modelAndView.addObject("currency", currencies);
					modelAndView.addObject("Currency is present in backend", "rollback");
					//	modelAndView.setViewName("masters/currency/create");
					return new ModelAndView("redirect:/currency/list");
				}
			}
			if(currencies.getId() != null){
				boolean isCurrencyDuplicateWithDifferentId = currenciesService.isCurrencyDuplicateWithDifferentId(currencies.getCurrencyCode(), currencies.getCountryCode(), currencies.getId());
				if (isCurrencyDuplicateWithDifferentId) {
					List<Countries> countries = countriesService.listOfCountries();
					modelAndView.addObject("countries", countries);
					modelAndView.addObject("currency", currencies);
					modelAndView.addObject("errorMessage", "Duplicate value!!!");
					modelAndView.setViewName("masters/currency/create");
					return modelAndView;
				}
			}
			currenciesService.save(currencies);
			modelAndView.addObject("confirmationMessage", "Currency has been created");
			List<Countries> countries = countriesService.listOfCountries();
			modelAndView.addObject("countries", countries);
			modelAndView.addObject("currency", currencies);
		
		}
		return new ModelAndView("redirect:/currency/list");
	}

	@RequestMapping(path = { "/currency/edit", "/currency/edit/{id}" })
	public ModelAndView editCurrency(ModelAndView model, @PathVariable("id") Optional<Long> id,
			BindingResult bindingResult) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			model.setViewName("masters/currency/create");
		}
		if (id.isPresent()) {
			Currencies currencies = currenciesService.getById(id.get());
			model.addObject("currency", currencies);
			List<Countries> countries = countriesService.listOfCountries();
			model.addObject("countries", countries);
			model.setViewName("masters/currency/create");
		} else {
			model.addObject("currency", new Currencies());
			model.setViewName("masters/currency/create");
		}
		return model;
	}

	@RequestMapping(value = "/currency/list", method = RequestMethod.GET)
	public ModelAndView listCountries(ModelAndView modelAndView, @PageableDefault(size = 10) Pageable pageable) {
		List<Currencies> currencies = currenciesService.listOfCurrenciesCurrencyCode();
		modelAndView.addObject("currencies", currencies);
		modelAndView.setViewName("masters/currency/list");
		return modelAndView;
	}
	@RequestMapping(value = "/currency/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteCountry(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
				.findAllPaymentsReceiveByCurrency(id.get());
		if (paymentReceiveModes.size() == 0) {
			//currenciesService.deleteCurrency(id.get());
			modelAndView.addObject("confirmationMessage", "Country has been deleted!!");
			List<Currencies> currencies = currenciesService.listOfCurrenciesCurrencyCode();
			modelAndView.addObject("currencies", currencies);
			modelAndView.setViewName("masters/currency/list");
			return modelAndView;
		} else {
			modelAndView.addObject("errorMessage", "You can't delete this currency!!!");
			List<Currencies> currencies = currenciesService.listOfCurrenciesCurrencyCode();
			modelAndView.addObject("currencies", currencies);
			modelAndView.setViewName("masters/currency/list");
			return modelAndView;
		}
	}
	/*
	 * @RequestMapping(value = "/currency/getcurrencyCode", method =
	 * RequestMethod.GET) public ModelAndView getCurrencyCode(ModelAndView
	 * modelAndView, @RequestParam String countryName, Currencies currencies) {
	 * log.info("country name is:" + countryName); List<Currencies> countryCode =
	 * currenciesService.getCountryCodeByCountryName(countryName);
	 * modelAndView.addObject("countryCode", countryCode); List<Countries> countries
	 * = countriesService.listOfCountries(); modelAndView.addObject("countries",
	 * countries); modelAndView.addObject("currency", currencies);
	 * modelAndView.setViewName("masters/currency/create"); return modelAndView; }
	 */
	
	  @RequestMapping(value = "/api/currency/getcountryCodeapi/{id}", method =  RequestMethod.GET) 
	  public @ResponseBody CountryApiResponse getCurrencyCode(@PathVariable String id ) { log.info("country name is API1:" + id); 
	 // List<Currencies> countryCode = currenciesService.getCountryCodeByCountryName(id);
	  	Countries country = countriesService.findByCountryName(id);
	  	CountryApiResponse caResp = new CountryApiResponse();
	  	caResp.setId(country.getId());
	  	caResp.setCountryName(country.getCountryName());
	  	caResp.setCountryCode(country.getCountryCode());
	  	
	  		return caResp;
	  }
	  
	  @RequestMapping(value = "/getcountrycodebyIdapi/{id}", method =  RequestMethod.GET) 
	  public @ResponseBody CountryApiResponse getCurrencyCodeByID(@PathVariable Long id ) throws RecordNotFoundException { log.info("country name is API2:" + id); 
	 // List<Currencies> countryCode = currenciesService.getCountryCodeByCountryName(id);
	  	Currencies country = currenciesService.getById(id);
	  	CountryApiResponse caResp = new CountryApiResponse();
	  	caResp.setId(country.getId());
	  	caResp.setCountryName(country.getCountryName());
	  	caResp.setCountryCode(country.getCountryCode());
	  	
	  		return caResp;
	  }
	  

		@RequestMapping(value = "/api/currency/deletecurrencyapi/{id}", method = RequestMethod.GET)
		public @ResponseBody  String deleteCurrencyById(@PathVariable("id") Optional<Long> id)
				throws RecordNotFoundException {
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceiveByCurrency(id.get());
			if (paymentReceiveModes.size() == 0) {
				currenciesService.deleteCurrency(id.get());
				List<Currencies> currencies = currenciesService.listOfCurrenciesCurrencyCode();
				return "Deleted Succefully";
			} else {
				List<Currencies> currencies = currenciesService.listOfCurrenciesCurrencyCode();
				return "You Can't Delete This Country As This Has Been Mapped To PaymentRecieveMode";
			}

		}
	 
}
