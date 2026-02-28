package com.adminremit.masters.converter;

import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.models.ReceiverType;
import com.adminremit.masters.repository.ReceiverTypeRepo;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.masters.service.ReceiverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReceiverTypeByIdConverter implements Converter<String, ReceiverType> {

    @Autowired
    private ReceiverTypeService receiverTypeService;

    @Override
    public ReceiverType convert(String id) {
        ReceiverType receiverType = null;
        try {
            receiverType = receiverTypeService.getReceiverTypeById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiverType;
    }

}
