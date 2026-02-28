package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.dto.CountryApiResponse;
import com.adminremit.masters.dto.CurrencyApiResponse;
import com.adminremit.masters.dto.PaymentReceiveModeDTO;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.*;
import com.adminremit.masters.service.*;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class PaymentReceiveModeController {

	@Autowired
	private PaymentReceiveModeService paymentReceiveModeService;

	@Autowired
	private CountriesService countriesService;

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private PaymentCodeMasterService paymentCodeMasterService;

	@Autowired
	private FeeMasterService feeMasterService;

	@Autowired
	private GlobalLimitMasterService globalLimitMasterService;

	@Autowired
	private ServiceChargeMasterService serviceChargeMasterService;

	@Autowired
	private MarginMasterService marginMasterService;

	@RequestMapping(value = "/payment")
	public ModelAndView payment(ModelAndView modelAndView, PaymentReceiveModeDTO paymentReceiveModeDTO) {
		modelAndView.addObject("payment", paymentReceiveModeDTO);
		modelAndView.addObject("paymentModeType", PaymentModeType.PAYMENT);
		modelAndView.addObject("categories", Arrays.asList(UserCategory.values()));
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.addObject("paymentCode",
				paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
		modelAndView.setViewName("masters/payment/create");
		return modelAndView;
	}

	@RequestMapping(value = "/payment/create", method = RequestMethod.POST)
	public ModelAndView createPaymentMode(@Valid PaymentReceiveModeDTO paymentReceiveModeDTO,
			BindingResult bindingResult, ModelAndView model) throws RecordNotFoundException {
	//	log.info("PaymentReceiveModeDTO");

		if (bindingResult.hasErrors()) {
			model.addObject("payment", paymentReceiveModeDTO);
			model.addObject("paymentModeType", PaymentModeType.PAYMENT);
			model.addObject("categories", Arrays.asList(UserCategory.values()));
			model.addObject("countries", countriesService.listOfCountriesCountryCode());
			model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			model.addObject("paymentCode", paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
			model.setViewName("masters/payment/create");
			return model;
		} else {
		//	log.info(paymentReceiveModeDTO.getCurrencies());
			boolean isDuplicate = paymentReceiveModeService.isPaymentCodeAndReceiveCodeExist(
					UserType.valueOf(paymentReceiveModeDTO.getUserType()),
					UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()),
					Long.parseLong(paymentReceiveModeDTO.getCurrencies()),
					Long.parseLong(paymentReceiveModeDTO.getCountries()),
					Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster()));

			if (isDuplicate && paymentReceiveModeDTO.getId() == null) {
				paymentReceiveModeDTO.setPublish(false);
				model.addObject("errorMessage", "Duplicate value!!!");
				model.addObject("payment", paymentReceiveModeDTO);
				model.addObject("paymentModeType", PaymentModeType.PAYMENT);
				model.addObject("categories", Arrays.asList(UserCategory.values()));
				model.addObject("countries", countriesService.listOfCountriesCountryCode());
				model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				model.addObject("paymentCode",
						paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
				model.setViewName("masters/payment/create");
				return model;
			}

			boolean isDuplicateById = paymentReceiveModeService.isPaymentCodeAndReceiveCodeExistById(
					UserType.valueOf(paymentReceiveModeDTO.getUserType()),
					UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()),
					Long.parseLong(paymentReceiveModeDTO.getCurrencies()),
					Long.parseLong(paymentReceiveModeDTO.getCountries()),
					Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster()), paymentReceiveModeDTO);
			if (isDuplicateById && paymentReceiveModeDTO.getId() != null) {
				paymentReceiveModeDTO.setPublish(false);
				model.addObject("errorMessage", "Duplicate value!!!");
				model.addObject("payment", paymentReceiveModeDTO);
				model.addObject("paymentModeType", PaymentModeType.PAYMENT);
				model.addObject("categories", Arrays.asList(UserCategory.values()));
				model.addObject("countries", countriesService.listOfCountriesCountryCode());
				model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				model.addObject("paymentCode",
						paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
				model.setViewName("masters/payment/create");
				return model;
			}
			PaymentReceiveMode paymentReceiveMode = new PaymentReceiveMode();
			paymentReceiveMode.setPaymentModeType(PaymentModeType.PAYMENT);
			paymentReceiveMode.setId(paymentReceiveModeDTO.getId());
			paymentReceiveMode.setPublish(paymentReceiveModeDTO.getPublish());
			paymentReceiveMode.setComments(paymentReceiveModeDTO.getComments());
			paymentReceiveMode
					.setCountries(countriesService.getById(Long.parseLong(paymentReceiveModeDTO.getCountries())));
			paymentReceiveMode
					.setCurrencies(currenciesService.getById(Long.parseLong(paymentReceiveModeDTO.getCurrencies())));
			paymentReceiveMode.setDescription(paymentReceiveModeDTO.getDescription());
			paymentReceiveMode.setUserCategory(UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()));
			paymentReceiveMode.setUserType(UserType.valueOf(paymentReceiveModeDTO.getUserType()));
			paymentReceiveMode.setPaymentCodeMaster(
					paymentCodeMasterService.getById(Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster())));
			paymentReceiveMode.setRemarks(paymentReceiveModeDTO.getRemarks());
			paymentReceiveModeService.save(paymentReceiveMode);
		}
		return new ModelAndView("redirect:/payment/list");
	}

	@RequestMapping(path = { "/payment/edit", "/payment/edit/{id}" })
	public ModelAndView editPayment(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id,
			BindingResult bindingResult) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("masters/payment/create");
		}
		if (id.isPresent()) {
			PaymentReceiveMode entity = paymentReceiveModeService.getById(id.get());

			PaymentReceiveModeDTO paymentReceiveModeDTO = new PaymentReceiveModeDTO();
			paymentReceiveModeDTO.setId(entity.getId());
			paymentReceiveModeDTO.setComments(entity.getComments());
			paymentReceiveModeDTO.setCountries(entity.getCountries().getId().toString());
			paymentReceiveModeDTO.setCurrencies(entity.getCurrencies().getId().toString());
			paymentReceiveModeDTO.setUserType(entity.getUserType().toString());
			paymentReceiveModeDTO.setPaymentModeType(entity.getPaymentModeType().toString());
			paymentReceiveModeDTO.setUserCategory(entity.getUserCategory().toString());
			paymentReceiveModeDTO.setPaymentCodeMaster(entity.getPaymentCodeMaster().getId().toString());
			paymentReceiveModeDTO.setDescription(entity.getDescription());
			paymentReceiveModeDTO.setRemarks(entity.getRemarks());
			paymentReceiveModeDTO.setPublish(entity.getPublish());

			modelAndView.addObject("payment", paymentReceiveModeDTO);
			modelAndView.addObject("paymentModeType", PaymentModeType.PAYMENT);
			modelAndView.addObject("categories", Arrays.asList(UserCategory.values()));
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.addObject("paymentCode",
					paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
			modelAndView.setViewName("masters/payment/create");
		} else {
			modelAndView.addObject("payment", new PaymentReceiveMode());
			modelAndView.setViewName("masters/payment/create");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/payment/list", method = RequestMethod.GET)
	public ModelAndView listPayments(ModelAndView modelAndView, @PageableDefault(size = 10) Pageable pageable) {
		List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
				.findAllPaymentsReceive(PaymentModeType.PAYMENT);
		modelAndView.addObject("payments", paymentReceiveModes);
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.setViewName("masters/payment/list");
		return modelAndView;
	}

	@RequestMapping(value = "/payment/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deletePayment(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		List<FeeMaster> feeMasters = feeMasterService.listOfFeeByPaymentMode(id.get());
		List<GlobalLimitMaster> globalLimitMasters = globalLimitMasterService
				.listOfGlobalLimitMasterByPaymentMode(id.get());
		List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService
				.listOfServiceChargeMasterByPaymentMode(id.get());
		List<MarginMaster> marginMasters = marginMasterService.listOfMarginMasterByPaymentMode(id.get());
		if (feeMasters.size() == 0 && globalLimitMasters.size() == 0 && serviceChargeMasters.size() == 0
				&& marginMasters.size() == 0) {
			// paymentReceiveModeService.deletePaymentReceive(id.get());
			modelAndView.addObject("confirmationMessage", "Paymode has been deleted!!");
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.PAYMENT);
			modelAndView.addObject("payments", paymentReceiveModes);
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.setViewName("masters/payment/list");
			return modelAndView;
		} else {
			modelAndView.addObject("errorMessage", "You can't delete this paymode code!!");
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.PAYMENT);
			modelAndView.addObject("payments", paymentReceiveModes);
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.setViewName("masters/payment/list");
			return modelAndView;
		}

	}

	@RequestMapping(value = "/receive")
	public ModelAndView receive(ModelAndView modelAndView, PaymentReceiveModeDTO paymentReceiveModeDTO) {
		modelAndView.addObject("receive", paymentReceiveModeDTO);
		modelAndView.addObject("paymentModeType", PaymentModeType.RECEIVE);
		modelAndView.addObject("categories", Arrays.asList(UserCategory.values()));
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.addObject("paymentCode",
				paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.RECEIVE));
		modelAndView.setViewName("masters/receive/create");
		return modelAndView;
	}

	@RequestMapping(value = "/receive/create", method = RequestMethod.POST)
	public ModelAndView createReceiveMode(@Valid PaymentReceiveModeDTO paymentReceiveModeDTO,
			BindingResult bindingResult, ModelAndView model) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			model.addObject("receive", paymentReceiveModeDTO);
			model.addObject("paymentModeType", PaymentModeType.RECEIVE);
			model.addObject("categories", Arrays.asList(UserCategory.values()));
			model.addObject("countries", countriesService.listOfCountriesCountryCode());
			model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			model.addObject("paymentCode", paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.RECEIVE));
			model.setViewName("masters/receive/create");
			return model;
		} else {
			boolean isDuplicate = paymentReceiveModeService.isPaymentCodeAndReceiveCodeExist(
					UserType.valueOf(paymentReceiveModeDTO.getUserType()),
					UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()),
					Long.parseLong(paymentReceiveModeDTO.getCurrencies()),
					Long.parseLong(paymentReceiveModeDTO.getCountries()),
					Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster()));
			if (isDuplicate && paymentReceiveModeDTO.getId() == null) {
				paymentReceiveModeDTO.setPublish(true);
				model.addObject("errorMessage", "Duplicate value!!!");
				model.addObject("payment", paymentReceiveModeDTO);
				model.addObject("paymentModeType", PaymentModeType.PAYMENT);
				model.addObject("categories", Arrays.asList(UserCategory.values()));
				model.addObject("countries", countriesService.listOfCountriesCountryCode());
				model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				model.addObject("paymentCode",
						paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
				model.setViewName("masters/payment/create");
				return model;
			}
			boolean isDuplicateById = paymentReceiveModeService.isPaymentCodeAndReceiveCodeExistById(
					UserType.valueOf(paymentReceiveModeDTO.getUserType()),
					UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()),
					Long.parseLong(paymentReceiveModeDTO.getCurrencies()),
					Long.parseLong(paymentReceiveModeDTO.getCountries()),
					Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster()), paymentReceiveModeDTO);
			if (isDuplicateById && paymentReceiveModeDTO.getId() != null) {
				paymentReceiveModeDTO.setPublish(true);
				model.addObject("errorMessage", "Duplicate value!!!");
				model.addObject("payment", paymentReceiveModeDTO);
				model.addObject("paymentModeType", PaymentModeType.PAYMENT);
				model.addObject("categories", Arrays.asList(UserCategory.values()));
				model.addObject("countries", countriesService.listOfCountriesCountryCode());
				model.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
				model.addObject("paymentCode",
						paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.PAYMENT));
				model.setViewName("masters/payment/create");
				return model;
			}
			PaymentReceiveMode paymentReceiveMode = new PaymentReceiveMode();
			paymentReceiveMode.setPaymentModeType(PaymentModeType.RECEIVE);
			paymentReceiveMode.setId(paymentReceiveModeDTO.getId());
			paymentReceiveMode.setPublish(paymentReceiveModeDTO.getPublish());
			paymentReceiveMode.setComments(paymentReceiveModeDTO.getComments());
			paymentReceiveMode
					.setCountries(countriesService.getById(Long.parseLong(paymentReceiveModeDTO.getCountries())));
			paymentReceiveMode
					.setCurrencies(currenciesService.getById(Long.parseLong(paymentReceiveModeDTO.getCurrencies())));
			paymentReceiveMode.setDescription(paymentReceiveModeDTO.getDescription());
			paymentReceiveMode.setUserCategory(UserCategory.valueOf(paymentReceiveModeDTO.getUserCategory()));
			paymentReceiveMode.setUserType(UserType.valueOf(paymentReceiveModeDTO.getUserType()));
			paymentReceiveMode.setPaymentCodeMaster(
					paymentCodeMasterService.getById(Long.parseLong(paymentReceiveModeDTO.getPaymentCodeMaster())));
			paymentReceiveMode.setRemarks(paymentReceiveModeDTO.getRemarks());
			paymentReceiveModeService.save(paymentReceiveMode);
		}
		return new ModelAndView("redirect:/receive/list");
	}

	@RequestMapping(path = { "/receive/edit", "/receive/edit/{id}" })
	public ModelAndView editReceiveMode(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id,
			BindingResult bindingResult) throws RecordNotFoundException {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("masters/receive/create");
		}
		if (id.isPresent()) {
			PaymentReceiveMode entity = paymentReceiveModeService.getById(id.get());

			PaymentReceiveModeDTO paymentReceiveModeDTO = new PaymentReceiveModeDTO();
			paymentReceiveModeDTO.setId(entity.getId());
			paymentReceiveModeDTO.setComments(entity.getComments());
			paymentReceiveModeDTO.setCountries(entity.getCountries().getId().toString());
			paymentReceiveModeDTO.setCurrencies(entity.getCurrencies().getId().toString());
			paymentReceiveModeDTO.setUserType(entity.getUserType().toString());
			paymentReceiveModeDTO.setPaymentModeType(entity.getPaymentModeType().toString());
			paymentReceiveModeDTO.setUserCategory(entity.getUserCategory().toString());
			paymentReceiveModeDTO.setPaymentCodeMaster(entity.getPaymentCodeMaster().getId().toString());
			paymentReceiveModeDTO.setDescription(entity.getDescription());
			paymentReceiveModeDTO.setRemarks(entity.getRemarks());
			paymentReceiveModeDTO.setPublish(entity.getPublish());

			modelAndView.addObject("receive", paymentReceiveModeDTO);
			modelAndView.addObject("paymentModeType", PaymentModeType.RECEIVE);
			modelAndView.addObject("categories", Arrays.asList(UserCategory.values()));
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.addObject("paymentCode",
					paymentCodeMasterService.listOfPaymentCodeByType(PaymentModeType.RECEIVE));
			modelAndView.setViewName("masters/receive/create");
		} else {
			modelAndView.addObject("payment", new PaymentReceiveMode());
			modelAndView.setViewName("masters/receive/create");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/receive/list", method = RequestMethod.GET)
	public ModelAndView listReceive(ModelAndView modelAndView, @PageableDefault(size = 10) Pageable pageable) {
		List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
				.findAllPaymentsReceive(PaymentModeType.RECEIVE);
		modelAndView.addObject("payments", paymentReceiveModes);
		modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
		modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
		modelAndView.setViewName("masters/receive/list");
		return modelAndView;
	}

	@RequestMapping(value = "/receive/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteReceive(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		List<FeeMaster> feeMasters = feeMasterService.listOfFeeByReceiveMode(id.get());
		List<GlobalLimitMaster> globalLimitMasters = globalLimitMasterService
				.listOfGlobalLimitMasterByReceiveMode(id.get());
		List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService
				.listOfServiceChargeMasterByReceiveMode(id.get());
		List<MarginMaster> marginMasters = marginMasterService.listOfMarginMasterByReceiveMode(id.get());
		if (feeMasters.size() == 0 && globalLimitMasters.size() == 0 && serviceChargeMasters.size() == 0
				&& marginMasters.size() == 0) {
			//paymentReceiveModeService.deletePaymentReceive(id.get());
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.RECEIVE);
			modelAndView.addObject("payments", paymentReceiveModes);
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.addObject("confirmationMessage", "Receive mode has been deleted!!");
			modelAndView.setViewName("masters/receive/list");
			return modelAndView;
		} else {
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.RECEIVE);
			modelAndView.addObject("payments", paymentReceiveModes);
			modelAndView.addObject("countries", countriesService.listOfCountriesCountryCode());
			modelAndView.addObject("currencies", currenciesService.listOfCurrenciesCurrencyCode());
			modelAndView.addObject("errorMessage", "You can't delete this receive mode code!!");
			modelAndView.setViewName("masters/receive/list");
			return modelAndView;
		}
	}

	// modified
	 
	@RequestMapping(value = "/api/payment/getcurrencyCode/{countryId}",method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getCurrencyCode(@PathVariable("countryId") Long id) throws RecordNotFoundException {
	//	log.info("Country id is:" + id);
        Countries country = new Countries();
        
		List<Currencies> currencyCode = null;
		country = countriesService.getById(id);
		//log.info("country code"+country.getCountryCode());
		
		List<CurrencyApiResponse> currList = new ArrayList<CurrencyApiResponse>();
		
		currencyCode = currenciesService.getByCountryCodeForCurrencyCode(country.getCountryCode());
		//log.info(currencyCode.get(0).getCurrencyCode());
		
		for (Currencies currency : currencyCode) {
			CurrencyApiResponse currObj = new CurrencyApiResponse();
			currObj.setId(currency.getId());
			currObj.setCurrencyName(currency.getCurrencyName());
			currObj.setCurrencyCode(currency.getCurrencyCode());
			currObj.setCountryCode(currency.getCountryCode());
			currObj.setCountryName(currency.getCountryName());
			currObj.setCurrencyDescription(currency.getCurrencyDescription());
			currObj.setReceiveCountryName(currency.getCountryName());
			currObj.setSendCurrencyName(currency.getCurrencyName());
			currList.add(currObj);
		}

		
		return currList;

	}
	
	@RequestMapping(value = "/getpaymentcurrencycodebyIdapi/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CurrencyApiResponse> getPaymentCurrencyCodeByID(@PathVariable Long id) throws RecordNotFoundException {
		// log.info("country name is:" + id);
		// List<Currencies> countryCode =
		// currenciesService.getCountryCodeByCountryName(id);
		PaymentReceiveMode recieve = new PaymentReceiveMode();
			recieve = 	paymentReceiveModeService.getById(id);
	Currencies currency = recieve.getCurrencies();
	//log.info("currency"+currency);
		CurrencyApiResponse caResp = new CurrencyApiResponse();
		List<CurrencyApiResponse> currencyapiresponse = new ArrayList<CurrencyApiResponse>();
		
		caResp.setId( recieve.getCurrencies().getId());
		caResp.setCurrencyName( recieve.getCurrencies().getCurrencyName());
		caResp.setCurrencyCode( recieve.getCurrencies().getCurrencyCode());
		currencyapiresponse.add(caResp);
		return currencyapiresponse;
	}

	@RequestMapping(value = "/api/payment/deletepaymentapi/{id}", method = RequestMethod.GET)
	public @ResponseBody String deletePaymentById(@PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		List<FeeMaster> feeMasters = feeMasterService.listOfFeeByPaymentMode(id.get());
		List<GlobalLimitMaster> globalLimitMasters = globalLimitMasterService
				.listOfGlobalLimitMasterByPaymentMode(id.get());
		List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService
				.listOfServiceChargeMasterByPaymentMode(id.get());
		List<MarginMaster> marginMasters = marginMasterService.listOfMarginMasterByPaymentMode(id.get());

		if (feeMasters.size() == 0 && globalLimitMasters.size() == 0 && serviceChargeMasters.size() == 0
				&& marginMasters.size() == 0) {
			paymentReceiveModeService.deletePaymentReceive(id.get());
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.PAYMENT);
			return "0 Deleted Succefully";
		} else {
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.PAYMENT);
			return "1 You Can't Delete This Payment mode  ";
		}

	}
	@RequestMapping(value = "/api/receive/deleterecieveapi/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteRecievesById(@PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		List<FeeMaster> feeMasters = feeMasterService.listOfFeeByPaymentMode(id.get());
		List<GlobalLimitMaster> globalLimitMasters = globalLimitMasterService
				.listOfGlobalLimitMasterByPaymentMode(id.get());
		List<ServiceChargeMaster> serviceChargeMasters = serviceChargeMasterService
				.listOfServiceChargeMasterByPaymentMode(id.get());
		List<MarginMaster> marginMasters = marginMasterService.listOfMarginMasterByPaymentMode(id.get());

		if (feeMasters.size() == 0 && globalLimitMasters.size() == 0 && serviceChargeMasters.size() == 0
				&& marginMasters.size() == 0) {
			paymentReceiveModeService.deletePaymentReceive(id.get());
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.RECEIVE);
			return "0 Deleted Succefully";
		} else {
			List<PaymentReceiveMode> paymentReceiveModes = paymentReceiveModeService
					.findAllPaymentsReceive(PaymentModeType.RECEIVE);
			return "1 You Can't Delete This Payment mode  ";
		}

	}

	
}
