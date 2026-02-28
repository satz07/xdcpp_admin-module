package com.adminremit.masters.converter;

import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.service.PaymentCodeMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentCodeMasterByIdConverter implements Converter<String, PaymentCodeMaster> {

    @Autowired
    private PaymentCodeMasterService paymentCodeMasterService;

    @Override
    public PaymentCodeMaster convert(String id) {
        PaymentCodeMaster paymentCodeMaster = null;
        try {
            paymentCodeMaster = paymentCodeMasterService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentCodeMaster;
    }
}
