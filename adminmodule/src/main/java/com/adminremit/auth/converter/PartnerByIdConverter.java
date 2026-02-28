package com.adminremit.auth.converter;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.repository.PartnerRepository;
import com.adminremit.auth.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;

@Component
public class PartnerByIdConverter implements Converter<String, Partner> {

    private PartnerService partnerService;

    @Autowired
    public PartnerByIdConverter(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Override
    public Partner convert(String id) {
        try {
            return partnerService.getById(Long.parseLong(id));
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
