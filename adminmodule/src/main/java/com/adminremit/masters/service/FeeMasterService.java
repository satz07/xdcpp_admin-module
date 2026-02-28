package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.common.service.BaseService;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.models.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FeeMasterService {
    public void deleteFeeMaster(Long id) throws RecordNotFoundException;
    public FeeMaster getById(Long id) throws RecordNotFoundException;
    public List<FeeMaster> listOfFees();
    public FeeMaster saveFeeMaster(FeeMaster feeMaster) throws NoSuchFieldException;
    public List<FeeMaster> listAll();
    public List<FeeMaster> listOfFeeByPaymentMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public List<FeeMaster> listOfFeeByReceiveMode(Long paymentReceiveModeId) throws RecordNotFoundException;
    public boolean getExistingFeeByConditions(FeeMaster feeMaster);
    public boolean isBetween(BigDecimal value, BigDecimal fromValue, BigDecimal toValue);
    public boolean getExistingFeeByConditionsById(FeeMaster feeMaster);

    Optional<FeeMaster> getFeeById(Long feeId);

    void approveFee(FeeMaster fee);

    void rejectFee(FeeMaster fee);
}
