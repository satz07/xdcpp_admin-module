package com.adminremit.masters.service;

import com.adminremit.masters.models.MakerAndCheckerValues;
import com.adminremit.masters.repository.MakerAndCheckerValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MakerAndCheckerValuesServiceImpl implements MakerAndCheckerValuesService {

    @Autowired
    private MakerAndCheckerValuesRepository makerAndCheckerValuesRepository;

    @Override
    public MakerAndCheckerValues save(MakerAndCheckerValues makerAndCheckerValues) {

        if(makerAndCheckerValues.getId() == null) {
            makerAndCheckerValues =makerAndCheckerValuesRepository.save(makerAndCheckerValues);
            return makerAndCheckerValues;
        } else {
            Optional<MakerAndCheckerValues> makerAndCheckerValues1 = makerAndCheckerValuesRepository.findById(makerAndCheckerValues.getId());
            if(makerAndCheckerValues1.isPresent()) {
                return makerAndCheckerValuesRepository.save(makerAndCheckerValues);
            } {
                makerAndCheckerValues =makerAndCheckerValuesRepository.save(makerAndCheckerValues);
                return makerAndCheckerValues;
            }
        }
    }
}
