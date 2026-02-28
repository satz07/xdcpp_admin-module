package com.adminremit.auth.converter;

import com.adminremit.auth.models.FunctionMaster;
import com.adminremit.auth.service.FunctionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class FunctionMasterConverter implements Converter<String, FunctionMaster> {

    @Autowired
    private FunctionMasterService functionMasterService;

    @Override
    public FunctionMaster convert(String id) {
        try {
            return functionMasterService.findById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
