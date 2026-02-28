package com.adminremit.auth.service;

import com.adminremit.auth.models.FunctionMaster;
import com.adminremit.auth.repository.FunctionMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionMasterServiceImpl implements FunctionMasterService{

    @Autowired
    private FunctionMasterRepository functionMasterRepository;

    @Override
    public List<FunctionMaster> listOfFunctions() {
        List<FunctionMaster> functionMasterList = null;
        try {
            functionMasterList = functionMasterRepository.findAllByIsDeletedOrderByIdAsc(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return functionMasterList;
    }

    @Override
    public FunctionMaster findById(Long id) {
        FunctionMaster functionMaster = null;
        try {
            functionMaster = functionMasterRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return functionMaster;
    }
}
