package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface OwnerDesignationService extends BaseService<OwnerDesignation> {
    public void deleteDesignation(Long id) throws RecordNotFoundException;
    public OwnerDesignation getById(Long id) throws RecordNotFoundException;
    public List<OwnerDesignation> listOfDesignations();
    public OwnerDesignation getOwnerDesignationByName(String designation);
}
