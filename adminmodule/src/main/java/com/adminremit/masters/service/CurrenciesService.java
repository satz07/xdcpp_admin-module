package com.adminremit.masters.service;

import java.util.List;

import javax.validation.Valid;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;

public interface CurrenciesService {
	public Currencies save(Currencies currencies);

	public List<Currencies> listOfCurrencies();

	public Currencies getById(Long id) throws RecordNotFoundException;

	public void deleteCurrency(Long id);

	public List<Currencies> listOfCurrenciesCurrencyName();

	public List<Currencies> listOfCurrenciesCurrencyCode();

	public boolean isCurrencyDuplicate(String currencyCode,String countryCode);

	public boolean isCurrencyDuplicateWithDifferentId(String currencyCode, String countryCode, Long id);

	public Currencies findByCurrencyCode(String currencyCode);
	
	public Currencies findByCurrencyCodeAndCountryCode(String currencyCode,String countryCode);

	public List<Currencies> getCountryCodeByCountryName(Long id);

	List<Currencies> getCurrencyById(String countryCode);
	public List<Currencies> getCurrencyByCountryCodeforDelete(String code);
	public boolean isCurrencyIdDuplicate(Long currencyId);
	public Currencies findByCurrencyId(Long id);

	public List<Currencies> getByCountryCodeForCurrencyCode(String id)throws RecordNotFoundException;
	
	public List<Currencies> getCurrencyByCountryCode(String code);

	
	public boolean isCurrencyPresent(@Valid Currencies currencies);



	
	

	
}
