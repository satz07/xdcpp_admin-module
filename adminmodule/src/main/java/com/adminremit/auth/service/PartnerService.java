package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Partner;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface PartnerService extends BaseService<Partner> {
    public void deletePartner(Long id) throws RecordNotFoundException;
    public Partner getById(Long id) throws RecordNotFoundException;
    public List<Partner> listOfPartners();
    public List<Partner> listOfPartnersByProduct(Long productId) throws RecordNotFoundException;
	public List<Partner> listOfPartnersPartnerName();

}
