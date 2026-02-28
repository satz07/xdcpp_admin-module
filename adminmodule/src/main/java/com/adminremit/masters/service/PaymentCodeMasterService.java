package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Partner;
import com.adminremit.common.service.BaseService;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;

import java.util.List;

public interface PaymentCodeMasterService extends BaseService<PaymentCodeMaster> {
    public void deletePaymentCode(Long id) throws RecordNotFoundException;
    public PaymentCodeMaster getById(Long id) throws RecordNotFoundException;
    public List<PaymentCodeMaster> listOfPaymentCode();
    public List<PaymentCodeMaster> listOfPaymentCodeByType(PaymentModeType codeType);
    public List<PaymentCodeMaster> listOfAllPaymentCodeByType(PaymentModeType codeType);
}
