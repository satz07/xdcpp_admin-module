package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.repository.OwnerDesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerDesignationServiceImpl implements OwnerDesignationService{

    @Autowired
    private OwnerDesignationRepository ownerDesignationRepository;

    public OwnerDesignation save(OwnerDesignation ownerDesignation) {
        if(ownerDesignation.getId() == null) {
            ownerDesignation.setPublish(true);
            ownerDesignation =ownerDesignationRepository.save(ownerDesignation);
            return ownerDesignation;
        } else {
            Optional<OwnerDesignation> designation = ownerDesignationRepository.findById(ownerDesignation.getId());
            if(designation.isPresent()) {
                OwnerDesignation updateDesignation = designation.get();
                updateDesignation.setPublish(ownerDesignation.getPublish());
                updateDesignation.setDesignation(ownerDesignation.getDesignation());
                updateDesignation.setProduct(ownerDesignation.getProduct());
                return ownerDesignationRepository.save(updateDesignation);
            } {
                ownerDesignation =ownerDesignationRepository.save(ownerDesignation);
                return ownerDesignation;
            }
        }
    }

    public void deleteDesignation(Long id) throws RecordNotFoundException {
        Optional<OwnerDesignation> ownerDesignation = ownerDesignationRepository.findById(id);
        if(ownerDesignation.isPresent()) {
            OwnerDesignation ownerDesignation1 = ownerDesignation.get();
            ownerDesignation1.setIsDeleted(true);
            ownerDesignationRepository.save(ownerDesignation1);
        }
    }

    public OwnerDesignation getById(Long id) throws RecordNotFoundException {
        Optional<OwnerDesignation> ownerDesignation = ownerDesignationRepository.findById(id);
        OwnerDesignation designation = null;
        if(ownerDesignation.isPresent()) {
            designation = ownerDesignation.get();
        }
        return designation;
    }

    public List<OwnerDesignation> listOfDesignations() {
        List<OwnerDesignation> ownerDesignations = null;
        ownerDesignations = ownerDesignationRepository.findAllByIsDeleted(false);
        return ownerDesignations;
    }

    @Override
    public OwnerDesignation getOwnerDesignationByName(String designation) {
        OwnerDesignation ownerDesignation = null;
        try {
            ownerDesignation = ownerDesignationRepository.findFirstByDesignationAndIsDeleted(designation,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownerDesignation;
    }
}
