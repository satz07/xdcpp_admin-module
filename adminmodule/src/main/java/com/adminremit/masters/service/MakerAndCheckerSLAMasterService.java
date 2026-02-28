package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.MakerAndCheckerSLAMaster;

import java.util.List;

public interface MakerAndCheckerSLAMasterService {
    public List<MakerAndCheckerSLAMaster> listOfMakerChecker();
    public List<MakerAndCheckerSLAMaster> getMakerCheckerByFunction(MakerCheckerFunction makerCheckerFunction) throws RecordNotFoundException;
    public List<MakerAndCheckerSLAMaster> getMakerCheckerByField(String field) throws RecordNotFoundException;
}
