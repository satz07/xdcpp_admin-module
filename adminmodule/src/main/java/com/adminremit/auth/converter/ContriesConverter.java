package com.adminremit.auth.converter;

import com.adminremit.masters.models.Countries;
import com.adminremit.masters.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

class CountriesConverter implements Converter<String, Countries> {

    @Autowired
    private CountriesService countriesService;

    @Override
    public Countries convert(String id) {
        try {
            return countriesService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
