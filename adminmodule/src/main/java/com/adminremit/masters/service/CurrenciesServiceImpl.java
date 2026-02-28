package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.repository.CurrenciesRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Slf4j
@Service
public class CurrenciesServiceImpl implements CurrenciesService {

	@Autowired
	private CurrenciesRepository currenciesRepository;

	public Currencies save(Currencies currencies) {
		if (currencies.getId() == null) {
			currencies = currenciesRepository.save(currencies);
			return currencies;
		} else {
			Optional<Currencies> currencies1 = currenciesRepository.findById(currencies.getId());
			if (currencies1.isPresent()) {
				Currencies updateCountry = currencies1.get();
				updateCountry.setPublish(currencies.getPublish());
				updateCountry.setCountryCode(currencies.getCountryCode());
				updateCountry.setCountryName(currencies.getCountryName());
				updateCountry.setCurrencyCode(currencies.getCurrencyCode());
				updateCountry.setCurrencyName(currencies.getCurrencyName());
				updateCountry.setCurrencyDescription(currencies.getCurrencyDescription());
				return currenciesRepository.save(updateCountry);
			}
			{
				currencies = currenciesRepository.save(currencies);
				return currencies;
			}
		}
	}

	public List<Currencies> listOfCurrencies() {
		List<Currencies> currencies = null;
		try {
			currencies = currenciesRepository.findAllByIsDeleted(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencies;
	}

	public Currencies getById(Long id) throws RecordNotFoundException {
		Currencies currencies = null;
		try {
			log.info(id.toString());
			Optional<Currencies> currencies1 = currenciesRepository.findById(id);
			if (currencies1.isPresent()) {
				currencies = currencies1.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencies;
	}

	@Override
	public void deleteCurrency(Long id) {
		try {
			Currencies currencies = currenciesRepository.findById(id).get();
			if (currencies != null) {
				currencies.setIsDeleted(true);
				currenciesRepository.save(currencies);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Currencies> listOfCurrenciesCurrencyName() {
		List<Currencies> currencies = null;
		try {
			currencies = currenciesRepository.findAllByIsDeletedOrderByCurrencyNameAsc(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencies;
	}

	@Override
	public List<Currencies> listOfCurrenciesCurrencyCode() {
		List<Currencies> currencies = new ArrayList<>();
		try {
			List<Currencies> currencies1 = currenciesRepository.findAllByIsDeletedOrderByCurrencyCodeAsc(false);
			List<String> list = new ArrayList<>();
			for (Currencies currencies2 : currencies1) {
			//	if (!list.contains(currencies2.getCurrencyCode())) {
					currencies.add(currencies2);
					list.add(currencies2.getCurrencyCode());
			//	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencies;
	}

	@Override
	public boolean isCurrencyDuplicate(String currencyCode,String countryCode) {
		boolean isCurrencyDuplicate = false;
		try {
			List<Currencies> currencies = currenciesRepository.findAllByCurrencyCodeAndIsDeleted(currencyCode,countryCode, false);
			if (currencies.size() > 0) {
				isCurrencyDuplicate = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCurrencyDuplicate;
	}

    @Override
    public boolean isCurrencyDuplicateWithDifferentId(String currencyCode, String countryCode, Long id) {
        boolean isCurrencyDuplicateWithDifferentId = false;
        try {
            List<Currencies> currenciesId = currenciesRepository.findAllByIdAndCurrencyCodeAndIsDeleted(id,currencyCode,countryCode, false);
            if (currenciesId.size() > 0) {
                isCurrencyDuplicateWithDifferentId = false;
            }else{
                List<Currencies> currencies = currenciesRepository.findAllByCurrencyCodeAndIsDeleted(currencyCode,countryCode, false);
                if(currencies.size() > 0){
                    isCurrencyDuplicateWithDifferentId = true;
                }else{
                    isCurrencyDuplicateWithDifferentId = false;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
	    return isCurrencyDuplicateWithDifferentId;
    }
	
	@SuppressWarnings("null")
	@Override
	public boolean isCurrencyIdDuplicate(Long currencyId) {
		boolean isCurrencyDuplicate = false;
		try {
			
			
			
			
			List<Currencies> currencies = null;
			
			Optional<Currencies> curr= currenciesRepository.findById(currencyId);
			Currencies currtemp= curr.isPresent() ? curr.get() : null;
			if(currtemp!=null) {
				currencies.add(currtemp);
			}
			
			if (currencies.size() > 0) {
				isCurrencyDuplicate = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCurrencyDuplicate;
	}

	@Override
	public Currencies findByCurrencyCode(String currencyCode) {
		Optional<Currencies> currency = currenciesRepository.findByCurrencyCode(currencyCode);
		return currency.isPresent() ? currency.get() : null;
	}
	
	
	@Override
	public Currencies findByCurrencyId(Long id) {
		Optional<Currencies> currency = currenciesRepository.findById(id);
		return currency.isPresent() ? currency.get() : null;
	}

	@Override
	public List<Currencies> getCountryCodeByCountryName(Long id) {
		List<Currencies> countries = null;
		try {
			countries = currenciesRepository.findByCountryName(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return countries;
	}

	public List<Currencies> getCurrencyById(String countryCode) {
	
		List<Currencies> currencies = new ArrayList<>();
	
			currencies = currenciesRepository.findByCountryCode(countryCode);
		return currencies;
	}

	@Override
	public List<Currencies> getByCountryCodeForCurrencyCode(String countryCode) throws RecordNotFoundException {
		List<Currencies> currencies = null;
		   log.info("countryCode is"+countryCode);
			currencies = currenciesRepository.findByCountryCodeAndIsDeleted(countryCode,false);
		
		return currencies;
	}
	
	public List<Currencies> getCurrencyByCountryCode(String code) {
		
		List<Currencies> currencies = new ArrayList<>();
	
			currencies = currenciesRepository.findByCountryCode(code);
		return currencies;
	}
	
public List<Currencies> getCurrencyByCountryCodeforDelete(String code) {
		// WEB3 migration changes - Fixed IndexOutOfBoundsException by checking if list is empty
		List<Currencies> currencies = new ArrayList<>();
	
		currencies = currenciesRepository.findByCountryCode(code);
		// Check if list is not empty before accessing index 0
		if(currencies != null && !currencies.isEmpty() && currencies.get(0).isDeleted() == false) {
			return currencies;
		} else {
			List<Currencies> currencies1 = new ArrayList<>();
			return currencies1;
		}
	}
	
	@Override
	public boolean isCurrencyPresent(@Valid Currencies currencies) {		
		boolean isPresent = false;	
		try {				
			Optional<Currencies> existcurrency = currenciesRepository.findByCurrencyCodeAndCountryCodeIsDeleted(currencies.getCurrencyCode(),currencies.getCountryCode(), true);
			
			if (existcurrency.isPresent()) {
				isPresent = true;
				Currencies updateCountry = existcurrency.get();
					
					updateCountry.setIsDeleted(false);
					currenciesRepository.save(updateCountry);
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
	
	public Currencies findByCurrencyCodeAndCountryCode(String currencyCode,String countryCode) {
		
		Currencies curr= null;
		curr = currenciesRepository.findByCurrencyCodeAndCountryCode(currencyCode,countryCode);
		return curr;
	}
		

	}
