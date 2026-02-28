package com.adminremit.masters.service;

import java.util.List;
import java.util.Optional;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.MarginMaster;

public interface MarginMasterService {
    public void deleteMargin(Long id) throws RecordNotFoundException;
    public MarginMaster getById(Long id) throws RecordNotFoundException;
    public List<MarginMaster> listOfMargins();
    public MarginMaster save(MarginMaster marginMaster) throws NoSuchFieldException;
    public List<MarginMaster> listAll();
    public List<MarginMaster> listOfMarginMasterByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public List<MarginMaster> listOfMarginMasterByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public boolean getExistingFeeByConditions(MarginMaster marginMaster);
    public boolean getExistingFeeByConditionsById(MarginMaster marginMaster);

    Optional<MarginMaster> getMarginById(Long marginId);

    void approveMargin(MarginMaster margin);

    void rejectMargin(MarginMaster margin);
}
