package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;

import java.util.List;

import javax.validation.Valid;

public interface CountriesService {
    public Countries save(Countries countries);
    public List<Countries> listOfCountries();
    public Countries getById(Long id) throws RecordNotFoundException;
    public void deleteCountry(Long countryId) throws RecordNotFoundException;
    public List<Countries> listOfCountriesDialingCode();
    public List<Countries> listOfCountriesCountryCode();
    public List<Countries> listOfCountriesCountryName();
    public boolean getByCountryCodeAndCountryName(String countryCode,String countryName);
    Countries findByCountryName(String countryName);
	//public List<Countries> getCountryCodeByCountryName(String countryName);
	public Countries findByCountryCode(String countryCode);
	//public List<Countries> getCountryCodeByCountryName(String id);
	public List<Countries> getCountryCodeByCountryName(String id);
    public boolean getCountryByCountryCodeAndCountryName(@Valid Countries countries);
    boolean getByCountryCode(String countryCode, Countries countries);
    boolean getByIdAndCountryCodeAndCountryName(Long id, String countryCode, String countryName);
    
    /**
     * Check if country can be deleted by checking all dependencies
     * @param countryId The country ID to check
     * @return A string describing which dependencies exist, or null if country can be deleted
     */
    String checkCountryDependencies(Long countryId) throws RecordNotFoundException;
	

}
