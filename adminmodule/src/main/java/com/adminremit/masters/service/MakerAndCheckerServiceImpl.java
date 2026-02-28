package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.service.AuthService;
import com.adminremit.masters.enums.MarkerCheckerStatus;
import com.adminremit.masters.models.MakerAndChecker;
import com.adminremit.masters.models.MakerAndCheckerValues;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.repository.MakerAndCheckerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakerAndCheckerServiceImpl implements MakerAndCheckerService{

    @Autowired
    private MakerAndCheckerRepository makerAndCheckerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MakerAndCheckerValuesService makerAndCheckerValuesService;

    @Override
    public MakerAndChecker save(MakerAndChecker makerAndChecker) {
        if(makerAndChecker.getId() == null) {
            makerAndChecker.setMarkerCheckerStatus(MarkerCheckerStatus.CREATED);
            makerAndChecker.setCreatedBy(authService.getUser().getId());
            makerAndChecker.setPublish(true);
            makerAndChecker =makerAndCheckerRepository.save(makerAndChecker);
            for (MakerAndCheckerValues makerAndCheckerValues : makerAndChecker.getMakerAndCheckerValues()) {
                makerAndCheckerValues.setMakerCheckerId(makerAndChecker);
                makerAndCheckerValuesService.save(makerAndCheckerValues);
            }
            return makerAndChecker;
        } else {
            Optional<MakerAndChecker> makerAndChecker1 = makerAndCheckerRepository.findById(makerAndChecker.getId());
            if(makerAndChecker1.isPresent()) {
                return makerAndCheckerRepository.save(makerAndChecker);
            } {
                makerAndChecker =makerAndCheckerRepository.save(makerAndChecker);
                return makerAndChecker;
            }
        }
    }

    @Override
    public List<MakerAndChecker> listOfMakerCheckerRequest() {
        List<MakerAndChecker> makerAndCheckerList = null;
        makerAndCheckerList = makerAndCheckerRepository.findAll();
        return makerAndCheckerList;
    }

    @Override
    public MakerAndChecker getMakerAndCheckerById(Long id) throws RecordNotFoundException {
        Optional<MakerAndChecker> makerAndChecker = makerAndCheckerRepository.findById(id);
        MakerAndChecker makerAndChecker1 = null;
        try {
            if(makerAndChecker.isPresent()) {
                makerAndChecker1 = makerAndChecker.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makerAndChecker1;
    }

    @Override
    public void deleteMakerAndChecker(Long id) throws RecordNotFoundException {
        Optional<MakerAndChecker> makerAndChecker  = makerAndCheckerRepository.findById(id);
        if(makerAndChecker.isPresent()) {
            MakerAndChecker makerAndChecker1 = makerAndChecker.get();
            makerAndChecker1.setPublish(false);
            makerAndCheckerRepository.save(makerAndChecker1);
        }
    }
}
