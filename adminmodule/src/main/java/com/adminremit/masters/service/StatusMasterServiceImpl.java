package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.StatusMaster;
import com.adminremit.masters.repository.StatusMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusMasterServiceImpl implements StatusMasterService {

    @Autowired
    private StatusMasterRepository statusMasterRepository;

    @Override
    public StatusMaster save(StatusMaster statusMaster) {
        if(statusMaster.getId() == null) {
            statusMaster.setPublish(true);
            statusMaster =statusMasterRepository.save(statusMaster);
            return statusMaster;
        } else {
            Optional<StatusMaster> statusMaster1 = statusMasterRepository.findById(statusMaster.getId());
            if(statusMaster1.isPresent()) {
                StatusMaster updateStatus = statusMaster1.get();
                updateStatus.setCustomerStatusDesc(statusMaster.getCustomerStatusDesc());
                updateStatus.setStatusCode(statusMaster.getStatusCode());
                updateStatus.setStatusDescription(statusMaster.getStatusDescription());
                updateStatus.setPublish(statusMaster.getPublish());
                updateStatus.setSubStatusDesc(statusMaster.getSubStatusDesc());
                return statusMasterRepository.save(updateStatus);
            } {
                statusMaster =statusMasterRepository.save(statusMaster);
                return statusMaster;
            }
        }
    }

    @Override
    public StatusMaster getById(Long id) throws RecordNotFoundException {
        StatusMaster statusMaster = null;
        try {
            statusMaster = statusMasterRepository.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusMaster;
    }

    @Override
    public List<StatusMaster> listOfStatus() {
        List<StatusMaster> statusMasters = null;
        try {
            statusMasters = statusMasterRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusMasters;
    }

    @Override
    public void deleteStatus(Long statusId) throws RecordNotFoundException {
        Optional<StatusMaster> statusMaster = statusMasterRepository.findById(statusId);
        if(statusMaster.isPresent()) {
            StatusMaster statusMaster1 = statusMaster.get();
            statusMaster1.setIsDeleted(true);
            statusMasterRepository.save(statusMaster1);
        }
    }
}
