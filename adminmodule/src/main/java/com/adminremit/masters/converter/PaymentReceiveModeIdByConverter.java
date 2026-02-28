package com.adminremit.masters.converter;

import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.masters.service.PaymentReceiveModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceiveModeIdByConverter implements Converter<String, PaymentReceiveMode> {

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Override
    public PaymentReceiveMode convert(String id) {
        PaymentReceiveMode paymentReceiveMode = null;
        try {
            paymentReceiveMode = paymentReceiveModeService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentReceiveMode;
    }
}
