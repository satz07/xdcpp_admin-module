package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.repository.GroupMasterRepository;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.repository.PaymentCodeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentCodeMasterServiceImpl implements PaymentCodeMasterService {

    @Autowired
    private PaymentCodeMasterRepository paymentCodeMasterRepository;

    public PaymentCodeMaster save(PaymentCodeMaster paymentCodeMaster) {
        if(paymentCodeMaster.getId() == null) {
            paymentCodeMaster =paymentCodeMasterRepository.save(paymentCodeMaster);
            return paymentCodeMaster;
        } else {
            Optional<PaymentCodeMaster> paymentCodeMaster1 = paymentCodeMasterRepository.findById(paymentCodeMaster.getId());
            if(paymentCodeMaster1.isPresent()) {
                PaymentCodeMaster paymentCodeMaster2 = paymentCodeMaster1.get();
                paymentCodeMaster2.setPaymentCode(paymentCodeMaster.getPaymentCode());
                return paymentCodeMasterRepository.save(paymentCodeMaster2);
            } {
                paymentCodeMaster =paymentCodeMasterRepository.save(paymentCodeMaster);
                return paymentCodeMaster;
            }
        }
    }

    public void deletePaymentCode(Long id) throws RecordNotFoundException {
        Optional<PaymentCodeMaster> paymentCodeMaster  = paymentCodeMasterRepository.findById(id);
        if(paymentCodeMaster.isPresent()) {
            PaymentCodeMaster paymentCodeMaster1 = paymentCodeMaster.get();
            paymentCodeMaster1.setIsDeleted(true);
            paymentCodeMasterRepository.save(paymentCodeMaster1);
        }
    }

    public PaymentCodeMaster getById(Long id) throws RecordNotFoundException {
        Optional<PaymentCodeMaster> paymentCodeMaster = paymentCodeMasterRepository.findById(id);
        PaymentCodeMaster paymentCodeMaster1 = null;
        try {
            if(paymentCodeMaster.isPresent()) {
                paymentCodeMaster1 = paymentCodeMaster.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentCodeMaster1;
    }

    public List<PaymentCodeMaster> listOfPaymentCode() {
        List<PaymentCodeMaster> paymentCodeMasters = null;
        paymentCodeMasters = paymentCodeMasterRepository.findAllByIsDeleted(false);
        return paymentCodeMasters;
    }

    public List<PaymentCodeMaster> listOfPaymentCodeByType(PaymentModeType paymentCode) {
        List<PaymentCodeMaster> paymentCodeMasters = null;
        paymentCodeMasters = paymentCodeMasterRepository.findAllByPaymentModeType(paymentCode);
        return paymentCodeMasters;
    }

    @Override
    public List<PaymentCodeMaster> listOfAllPaymentCodeByType(PaymentModeType paymentCode) {
        List<PaymentCodeMaster> paymentCodeMasters = null;
        paymentCodeMasters = paymentCodeMasterRepository.findAllByPaymentModeType(paymentCode);
        return paymentCodeMasters;
    }
}
