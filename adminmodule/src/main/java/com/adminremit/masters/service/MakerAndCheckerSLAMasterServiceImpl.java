package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.MakerAndCheckerSLAMaster;
import com.adminremit.masters.repository.MakerAndCheckerSLAMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MakerAndCheckerSLAMasterServiceImpl implements MakerAndCheckerSLAMasterService{

    @Autowired
    private MakerAndCheckerSLAMasterRepository makerAndCheckerSLAMasterRepository;

    @Override
    public List<MakerAndCheckerSLAMaster> listOfMakerChecker() {
        List<MakerAndCheckerSLAMaster> makerAndCheckerSLAMasters = null;
        try {
            makerAndCheckerSLAMasters = makerAndCheckerSLAMasterRepository.findAllByPublish(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        return makerAndCheckerSLAMasters;
    }

    @Override
    public List<MakerAndCheckerSLAMaster> getMakerCheckerByFunction(MakerCheckerFunction makerCheckerFunction) throws RecordNotFoundException {
        List<MakerAndCheckerSLAMaster> makerAndCheckerSLAMasters = null;
        try {
            makerAndCheckerSLAMasters = makerAndCheckerSLAMasterRepository.findByMakerCheckerFunction(makerCheckerFunction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makerAndCheckerSLAMasters;
    }

    @Override
    public List<MakerAndCheckerSLAMaster> getMakerCheckerByField(String field) throws RecordNotFoundException {
        List<MakerAndCheckerSLAMaster> makerAndCheckerSLAMasters = null;
        try {
            makerAndCheckerSLAMasters = makerAndCheckerSLAMasterRepository.findByMakerCheckerField(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makerAndCheckerSLAMasters;
    }
}
