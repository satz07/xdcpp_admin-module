package com.adminremit.masters.service;

import com.adminremit.masters.models.CountryCurrencyMapping;

import java.util.List;

public interface CountryCurrencyMappingService {
    public CountryCurrencyMapping save(CountryCurrencyMapping currencyMapping);
    List<CountryCurrencyMapping> listOfCountryCurrency();
}
