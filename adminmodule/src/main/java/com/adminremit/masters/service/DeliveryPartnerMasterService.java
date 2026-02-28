package com.adminremit.masters.service;

import java.util.List;

import org.springframework.session.FindByIndexNameSessionRepository;

import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.DeliveryPartnerMaster;

public interface DeliveryPartnerMasterService {
	
	 public DeliveryPartnerMaster save(DeliveryPartnerMaster dpm);
	 public List<DeliveryPartnerMaster> listOfdpm();
	 public DeliveryPartnerMaster getByCountryName(String countryName);
	 public DeliveryPartnerMaster getByPaymentMethod(String paymentMethod);
	 public DeliveryPartnerMaster getById(Long id);
	 
 
}
