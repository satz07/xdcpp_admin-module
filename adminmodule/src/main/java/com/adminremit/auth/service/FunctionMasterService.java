package com.adminremit.auth.service;

import com.adminremit.auth.models.FunctionMaster;

import java.util.List;

public interface FunctionMasterService {
    public List<FunctionMaster> listOfFunctions();
    public FunctionMaster findById(Long id);
}
