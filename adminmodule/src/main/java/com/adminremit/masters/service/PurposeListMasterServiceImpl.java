package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.controllers.PurposeListMasterController;
import com.adminremit.masters.dto.PurposeListDTO;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.repository.PurposeListMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurposeListMasterServiceImpl implements PurposeListMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(PurposeListMasterServiceImpl.class);

    @Autowired
    CountriesService countriesService;

    @Autowired
    CurrenciesService currenciesService;


    PaymentReceiveModeService paymentReceiveModeService;


    @Autowired
    private PurposeListMasterRepository purposeListMasterRepository;


    @Override
    public PurposeListMaster save(PurposeListMaster purposeListMaster) {
        purposeListMaster.setPublish(false);
        if(purposeListMaster.getId() == null) {
            purposeListMaster =purposeListMasterRepository.save(purposeListMaster);
            return purposeListMaster;
        } else {
            Optional<PurposeListMaster> purposeListMaster1 = purposeListMasterRepository.findById(purposeListMaster.getId());
            if(purposeListMaster1.isPresent()) {
                PurposeListMaster updateStatus = purposeListMaster1.get();
                updateStatus.setCountryCode(purposeListMaster.getCountryCode());
                updateStatus.setCurrencyCode(purposeListMaster.getCurrencyCode());
                updateStatus.setPurpose(purposeListMaster.getPurpose());
                updateStatus.setPurposeCode(purposeListMaster.getPurposeCode());
                updateStatus.setReceiveCode(purposeListMaster.getReceiveCode());
                updateStatus.setPublish(purposeListMaster.getPublish());
                updateStatus.setUserType(purposeListMaster.getUserType());
                return purposeListMasterRepository.save(updateStatus);
            } {
                purposeListMaster =purposeListMasterRepository.save(purposeListMaster);
                return purposeListMaster;
            }
        }
    }

    @Override
    public List<PurposeListMaster> listOfPurposeList() {
        List<PurposeListMaster> purposeListMasters = null;
        try {
            purposeListMasters = purposeListMasterRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purposeListMasters;
    }

    @Override
    public PurposeListMaster getById(Long id) throws RecordNotFoundException {
        PurposeListMaster purposeListMaster = null;
        try {
            purposeListMaster = purposeListMasterRepository.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purposeListMaster;
    }

    @Override
    public void deletePurposeList(Long purposeId) throws RecordNotFoundException {
        Optional<PurposeListMaster> purposeListMaster = purposeListMasterRepository.findById(purposeId);
        if(purposeListMaster.isPresent()) {
            PurposeListMaster purposeListMaster1 = purposeListMaster.get();
            purposeListMaster1.setIsDeleted(true);
            purposeListMasterRepository.save(purposeListMaster1);
        }
    }

    @Override
    public Optional<PurposeListMaster> getPurposeById(Long purposeId) {
        return purposeListMasterRepository.findById(purposeId);
    }

    @Override
    public void approvePurpose(PurposeListMaster purpose) {
        Optional<PurposeListMaster> optionalPurpose = purposeListMasterRepository.findById(purpose.getId());
        if (optionalPurpose.isPresent()) {
            purposeListMasterRepository.save(purpose);
        }
    }

    @Override
    public void rejectPurpose(PurposeListMaster purpose) {
        Optional<PurposeListMaster> optionalPurpose = purposeListMasterRepository.findById(purpose.getId());
        if (optionalPurpose.isPresent()) {
            purposeListMasterRepository.save(purpose);
        }
    }
}
