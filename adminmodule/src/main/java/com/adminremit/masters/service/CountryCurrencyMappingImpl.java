package com.adminremit.masters.service;

import com.adminremit.masters.models.CountryCurrencyMapping;
import com.adminremit.masters.repository.CountryCurrencyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryCurrencyMappingImpl implements CountryCurrencyMappingService {

    @Autowired
    private CountryCurrencyMappingRepository countryCurrencyMappingRepository;

    public CountryCurrencyMapping save(CountryCurrencyMapping currencyMapping) {
        return countryCurrencyMappingRepository.save(currencyMapping);
    }

    @Override
    public List<CountryCurrencyMapping> listOfCountryCurrency() {
        return countryCurrencyMappingRepository.findAll();
    }
}
