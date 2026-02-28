package com.adminremit.auth.converter;

import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.service.RoleMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class RoleByIdConverter implements Converter<String, RoleMaster> {

    @Autowired
    private RoleMasterService roleMasterService;

    @Override
    public RoleMaster convert(String id) {
        try {
                return roleMasterService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
