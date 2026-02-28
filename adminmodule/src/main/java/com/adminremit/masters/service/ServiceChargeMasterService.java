package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.common.service.BaseService;
import com.adminremit.masters.models.FeeMaster;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.ServiceChargeMaster;

import java.util.List;

public interface ServiceChargeMasterService {
    public void deleteServiceCharge(Long id) throws RecordNotFoundException;
    public ServiceChargeMaster getById(Long id) throws RecordNotFoundException;
    public List<ServiceChargeMaster> listOfServiceCharge();
    public ServiceChargeMaster save(ServiceChargeMaster serviceChargeMaster) throws NoSuchFieldException;
    public List<ServiceChargeMaster> listOfFeeByPaymentReceiveMode(Long paymentReceiveModeId);
    public List<ServiceChargeMaster> listOfServiceChargeMasterByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public List<ServiceChargeMaster> listOfServiceChargeMasterByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public boolean getExistingFeeByConditions(ServiceChargeMaster serviceChargeMaster);
    public boolean getExistingFeeByConditionsById(ServiceChargeMaster serviceChargeMaster);
}
