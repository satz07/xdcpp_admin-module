package com.adminremit.masters.service;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.common.service.BaseService;
import com.adminremit.masters.models.MakerAndChecker;

import java.util.List;

public interface MakerAndCheckerService extends BaseService<MakerAndChecker> {
    public List<MakerAndChecker> listOfMakerCheckerRequest();
    public MakerAndChecker getMakerAndCheckerById(Long id) throws RecordNotFoundException;
    public void deleteMakerAndChecker(Long id) throws RecordNotFoundException;
}
