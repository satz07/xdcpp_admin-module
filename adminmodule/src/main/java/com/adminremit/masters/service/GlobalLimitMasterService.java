package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.common.service.BaseService;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.GlobalLimitMaster;

import java.util.List;
import java.util.Optional;

public interface GlobalLimitMasterService {
    public void deleteGlobalLimits(Long id) throws RecordNotFoundException;
    public GlobalLimitMaster getById(Long id) throws RecordNotFoundException;
    public List<GlobalLimitMaster> listOfGlobalLimits();
    public GlobalLimitMaster save(GlobalLimitMaster globalLimitMaster) throws NoSuchFieldException;
    public List<GlobalLimitMaster> listOfGlobalLimitMasterByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public List<GlobalLimitMaster> listOfGlobalLimitMasterByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public boolean getExistingFeeByConditions(GlobalLimitMaster globalLimitMaster);
    public boolean getExistingFeeByConditionsById(GlobalLimitMaster globalLimitMaster);

    Optional<GlobalLimitMaster> getGlobalLimitById(Long globalId);

    void approveGlobalLimit(GlobalLimitMaster globalLimit);

    void rejectGlobalLimits(GlobalLimitMaster globalLimits);
}
