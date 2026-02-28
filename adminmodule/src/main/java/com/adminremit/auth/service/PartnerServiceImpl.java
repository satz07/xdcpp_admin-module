package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.repository.OwnerDesignationRepository;
import com.adminremit.auth.repository.PartnerRepository;
import com.github.javaparser.utils.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService{

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ProductService productService;

    public Partner save(Partner partner) {
        if(partner.getId() == null) {
            partner.setPublish(true);
            partner =partnerRepository.save(partner);
            return partner;
        } else {
            Optional<Partner> partner1 = partnerRepository.findById(partner.getId());
            if(partner1.isPresent()) {
                Partner updatePartner = partner1.get();
                updatePartner.setPublish(partner.getPublish());
                updatePartner.setAddress(partner.getAddress());
                updatePartner.setProduct(partner.getProduct());
                updatePartner.setDescription(partner.getDescription());
                updatePartner.setLocationMaster(partner.getLocationMaster());
                updatePartner.setOfficialEmail(partner.getOfficialEmail());
                updatePartner.setPartnerId(partner.getPartnerId());
                updatePartner.setPhoneNumber(partner.getPhoneNumber());
                updatePartner.setPartnerName(partner.getPartnerName());
                updatePartner.setDialingCode(partner.getDialingCode());
                updatePartner.setOwnerDesignation(partner.getOwnerDesignation());
                return partnerRepository.save(updatePartner);
            } {
                partner.setPublish(true);
                partner =partnerRepository.save(partner);
                return partner;
            }
        }
    }

    public void deletePartner(Long id) throws RecordNotFoundException {
        Optional<Partner> partner = partnerRepository.findById(id);
        if(partner.isPresent()) {
            Partner partner1 = partner.get();
            partner1.setIsDeleted(true);
            partnerRepository.save(partner1);
        }
    }

    public Partner getById(Long id) throws RecordNotFoundException {
        Optional<Partner> partner = partnerRepository.findById(id);
        Partner partner1 = null;
        if(partner.isPresent()) {
            partner1 = partner.get();
        }
        return partner1;
    }

    public List<Partner> listOfPartners() {
        List<Partner> partners = null;
        partners = partnerRepository.findAllByIsDeleted(false);
        return partners;
    }

    @Override
    public List<Partner> listOfPartnersByProduct(Long id) throws RecordNotFoundException{
        List<Partner> partners = partnerRepository.findAllByProductAndIsDeleted(productService.getProductById(id),false);
        return partners;
    }

	@Override
	public List<Partner> listOfPartnersPartnerName() {
		List<Partner> partners = null;
        partners = partnerRepository.findAllByIsDeletedAndOrderByPartnerNameAsc(false);
    
        return partners;
	}
}
