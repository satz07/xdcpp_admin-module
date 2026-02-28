package com.adminremit.masters.service;

import com.adminremit.masters.models.ReceiverType;
import com.adminremit.masters.repository.ReceiverTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiverTypeServiceImpl implements ReceiverTypeService{

    @Autowired
    ReceiverTypeRepo receiverTypeRepo;

    @Override
    public List<ReceiverType> getAllReceiverType() {
      return receiverTypeRepo.findAll();
    }

    @Override
    public ReceiverType getReceiverTypeById(Long id){
          return receiverTypeRepo.findById(id).get();
    }
}
