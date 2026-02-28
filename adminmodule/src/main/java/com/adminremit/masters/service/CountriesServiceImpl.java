package com.adminremit.masters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.repository.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.repository.CountriesRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CountriesServiceImpl implements CountriesService {

	@Autowired
	private CountriesRepository countriesRepository;

	@Autowired
	private CurrenciesRepository currenciesRepository;

	@Autowired
	private PaymentReceiveModeService paymentReceiveModeService;

	@Autowired
	private CurrenciesService currenciesService;

	public Countries save(Countries countries) {
		if (countries.getId() == null) {
			log.info("publish is" +countries.getPublish());
			countries = countriesRepository.save(countries);
			return countries;
		} else {
			Optional<Countries> countries1 = countriesRepository.findById(countries.getId());
			if (countries1.isPresent()) {
				Countries updateCountry = countries1.get();
				updateCountry.setPublish(countries.getIsPublish());
				updateCountry.setCountryCode(countries.getCountryCode());
				updateCountry.setCountryName(countries.getCountryName());
				updateCountry.setDialingCode(countries.getDialingCode());
				return countriesRepository.save(updateCountry);
			}
			{
				countries = countriesRepository.save(countries);
				return countries;
			}
		}
	}

	public List<Countries> listOfCountries() {
		List<Countries> countries = null;
		try {
			countries = countriesRepository.findAllByIsDeleted(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}

	public Countries getById(Long id) throws RecordNotFoundException {
		Countries countries = null;
		log.info("Country ",id);
		try {
			Optional<Countries> countries1 = countriesRepository.findById(id);
			log.info("is country present"+countries1.isPresent());
			if (countries1.isPresent()) {
				countries = countries1.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}

	@Override
	public void deleteCountry(Long countryId) throws RecordNotFoundException {
		Optional<Countries> countries = countriesRepository.findById(countryId);
		if (countries.isPresent()) {
			Countries countries1 = countries.get();
			countries1.setIsDeleted(true);
			countriesRepository.save(countries1);
		}
	}

	@Override
	public List<Countries> listOfCountriesDialingCode() {
		List<Countries> countries = new ArrayList<>();
		try {
			List<Countries> countries1 = countriesRepository.findAllByIsDeletedOrderByDialingCodeAsc(false);
			List<Integer> list = new ArrayList<>();
			for (Countries countries2 : countries1) {
				if (!list.contains(countries2.getDialingCode())) {
					countries.add(countries2);
					list.add(countries2.getDialingCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}

	@Override
	public List<Countries> listOfCountriesCountryCode() {
		List<Countries> countries = null;
		try {
			countries = countriesRepository.findAllByIsDeletedOrderByCountryCodeAsc(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}

	@Override
	public List<Countries> listOfCountriesCountryName() {
		List<Countries> countries = null;
		try {
			countries = countriesRepository.findAllByIsDeletedOrderByCountryNameAsc(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}
	@Override
	public boolean getByCountryCodeAndCountryName(String countryCode, String countryName) {
		boolean isCountryDuplicate = false;
		try {
			List<Countries> countries = countriesRepository.findAllByCountryCodeAndCountryNameAndIsDeleted(countryCode,countryName, false);
			if (countries.size() > 0) {
				isCountryDuplicate = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCountryDuplicate;
	}

	@Override
	public boolean getByCountryCode(String countryCode, Countries countries) {
		boolean isCountryDuplicate = false;
		try {
			List<Countries> countries1 = countriesRepository.findAllByCountryCodeAndIsDeleted(countryCode, false);
			if (countries1.size() > 0) {
				for (Countries countrie: countries1) {
                    if(countrie.getId().longValue()!= countries.getId().longValue()) {
                        return true;
                    }
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCountryDuplicate;
	}

	@Override
	public boolean getByIdAndCountryCodeAndCountryName(Long id, String countryCode, String countryName) {
		boolean isCountryDuplicate = false;
		try {
			List<Countries> countries = countriesRepository.findByIdAndCountryCodeAndCountryNameAndIsDeleted(countryCode,countryName,id, false);
			if(countries.size() > 0){
				isCountryDuplicate = true;
			}else{
				isCountryDuplicate = false;
			}
//			List<Countries> countries1 = countriesRepository.findByIdAndCountryCodeAndCountryNameAndIsDeleted(countryCode,countryName,id, false);
//			if (countries1.size() > 0) {
//				isCountryDuplicate = false;
//			}else{
//				List<Countries> countries2 = countriesRepository.findAllByCountryCodeAndCountryNameAndIsDeleted(countryCode,countryName,false);
//				if(countries2.size() > 0){
//					isCountryDuplicate = true;
//				}else{
//					isCountryDuplicate = false;
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCountryDuplicate;
	}

	@Override
	public Countries findByCountryName(String countryName) {
		return countriesRepository.findByCountryNameAndIsDeletedFalse(countryName);
	}

	@Override
	public Countries findByCountryCode(String countryCode) {
		
		return countriesRepository.findByCountryCode(countryCode);
	}

	@Override
	public List<Countries> getCountryCodeByCountryName(String id) {
		// TODO Auto-generated method stub
		Countries country=countriesRepository.findByCountryNameAndIsDeletedFalse(id);
		List<Countries> Country = new ArrayList <Countries>();
		Country.add(country);
		return Country ;
	}

	@Override
	public boolean getCountryByCountryCodeAndCountryName(Countries countries) {
		boolean isPresent = false;
		try {
			Optional<Countries> existCountry = countriesRepository.findByCountryCodeAndCountryNameAndIsDeleted(countries.getCountryCode(),countries.getCountryName(), true);
			if (existCountry.isPresent()) {
				isPresent = true;
				Countries updateCountry =existCountry.get();

				updateCountry.setIsDeleted(false);
				countriesRepository.save(updateCountry);
				return isPresent;

			}
			else {
				return isPresent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isPresent;
	}

	@Override
	public String checkCountryDependencies(Long countryId) throws RecordNotFoundException {
		try {
			// Get the country first
			Countries country = getById(countryId);
			if (country == null) {
				throw new RecordNotFoundException("Country with ID " + countryId + " not found");
			}

			// Check for PaymentReceiveMode dependencies
			List<PaymentReceiveMode> paymentReceiveModes = 
					paymentReceiveModeService.findAllPaymentsReceiveByCountry(countryId);

			// Check for Currencies dependencies
			List<Currencies> currencies = 
					currenciesService.getCurrencyByCountryCodeforDelete(country.getCountryCode());

			// Build dependency message if any exist
			List<String> dependencies = new ArrayList<>();
			
			if (paymentReceiveModes != null && !paymentReceiveModes.isEmpty()) {
				dependencies.add("Payment Receive Modes (" + paymentReceiveModes.size() + ")");
			}
			
			if (currencies != null && !currencies.isEmpty()) {
				dependencies.add("Currencies (" + currencies.size() + ")");
			}

			if (!dependencies.isEmpty()) {
				return "Country has dependencies: " + String.join(", ", dependencies);
			}

			// No dependencies found, safe to delete
			return null;
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error checking country dependencies for country ID: {}", countryId, e);
			throw new RecordNotFoundException("Error checking country dependencies: " + e.getMessage());
		}
	}

	
	


	/*
	 * @Override public List<Countries> getCountryCodeByCountryName(String
	 * countryName) { List<Countries> countries = null; try { countries =
	 * countriesRepository.getCountryCodeByCountryName(countryName); for (Countries
	 * c : countries) { log.info("country codes are: "+c.getCountryName());
	 * log.info("country codes are: "+c.getCountryCode()); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return countries; }
	 */
	
}

