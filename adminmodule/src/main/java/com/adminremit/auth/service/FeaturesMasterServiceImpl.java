package com.adminremit.auth.service;

import com.adminremit.auth.enums.FeatureType;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.models.FunctionMaster;
import com.adminremit.auth.repository.FeaturesMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeaturesMasterServiceImpl implements FeaturesMasterService{

    @Autowired
    private FeaturesMasterRepository featuresMasterRepository;

    @Autowired
    private FunctionMasterService functionMasterService;

    @Override
    public List<FeaturesMaster> listOfFeaturesByFunction(Long functionId) {
        List<FeaturesMaster> featuresMasters = null;
        try {
            FunctionMaster functionMaster = functionMasterService.findById(functionId);
            featuresMasters = featuresMasterRepository.findAllByFunctionMasterAndPublish(functionMaster,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featuresMasters;
    }

    @Override
    public List<FeaturesMaster> listOfFeatures() {
        List<FeaturesMaster> featuresMasters = null;
        try {
            featuresMasters = featuresMasterRepository.findAllByIsDeletedOrderByIdAsc(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featuresMasters;
    }

    @Override
    public List<FeaturesMaster> listOfFeaturesByFeatureType(FeatureType featureType) {
        List<FeaturesMaster> featuresMasters = null;
        try {
            featuresMasters = featuresMasterRepository.findAllByFeatureTypeAndPublish(featureType,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featuresMasters;
    }

    @Override
    public FeaturesMaster getById(Long id) throws RecordNotFoundException {
        FeaturesMaster featuresMaster = null;
        Optional<FeaturesMaster> featuresMaster1 = featuresMasterRepository.findById(id);
        return featuresMaster1.get();
    }

    @Override
    public void deleteAllByRole(Long roleId) {

    }
}
