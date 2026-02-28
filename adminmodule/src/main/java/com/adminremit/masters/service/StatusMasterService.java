package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.StatusMaster;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StatusMasterService {
    public StatusMaster save(StatusMaster countries);
    public List<StatusMaster> listOfStatus();
    public StatusMaster getById(Long id) throws RecordNotFoundException;
    public void deleteStatus(Long statusId) throws RecordNotFoundException;
}
