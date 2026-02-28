package com.adminremit.auth.service;

import com.adminremit.auth.enums.FeatureType;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.models.FunctionMaster;

import java.util.List;

public interface FeaturesMasterService {
    public List<FeaturesMaster> listOfFeaturesByFunction(Long functionId);
    public List<FeaturesMaster> listOfFeaturesByFeatureType(FeatureType featureType);
    public List<FeaturesMaster> listOfFeatures();
    public FeaturesMaster getById(Long id) throws RecordNotFoundException;
    public void deleteAllByRole(Long roleId);
}
