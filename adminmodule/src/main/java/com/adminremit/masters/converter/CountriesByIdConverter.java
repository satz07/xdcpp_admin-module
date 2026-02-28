package com.adminremit.masters.converter;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CountriesByIdConverter implements Converter<String, Countries> {

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
