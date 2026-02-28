package com.adminremit.masters.service;

import com.adminremit.masters.models.ReceiverType;

import java.util.List;

public interface ReceiverTypeService {

    public List<ReceiverType> getAllReceiverType();
    public ReceiverType getReceiverTypeById(Long id);
}
