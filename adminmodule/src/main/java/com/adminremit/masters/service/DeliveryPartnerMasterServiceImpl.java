package com.adminremit.masters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.masters.models.DeliveryPartnerMaster;
import com.adminremit.masters.repository.DeliveryPartnerMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryPartnerMasterServiceImpl implements DeliveryPartnerMasterService {
	
	@Autowired
	private DeliveryPartnerMasterRepository dpmRepository;

	@Override
	public DeliveryPartnerMaster save(DeliveryPartnerMaster dpm) {
		// TODO Auto-generated method stub
		DeliveryPartnerMaster dpmRes = dpmRepository.save(dpm);
		return dpmRes;
	}

	@Override
	public List<DeliveryPartnerMaster> listOfdpm() {
		// TODO Auto-generated method stub
		
		return dpmRepository.findAll();
	}

	@Override
	public DeliveryPartnerMaster getByCountryName(String countryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryPartnerMaster getByPaymentMethod(String paymentMethod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryPartnerMaster getById(Long id) {
		// TODO Auto-generated method stub
		return dpmRepository.getOne(id);
	}
	
	

}
