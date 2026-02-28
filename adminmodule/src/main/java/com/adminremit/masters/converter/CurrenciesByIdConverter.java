package com.adminremit.masters.converter;

import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.service.CurrenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesByIdConverter implements Converter<String, Currencies> {

    @Autowired
    private CurrenciesService currenciesService;

    @Override
    public Currencies convert(String id) {
        try {
            return currenciesService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
