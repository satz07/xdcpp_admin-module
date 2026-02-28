package com.adminremit.auth.converter;

import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.service.GroupMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class GroupByIdConverter implements Converter<String, GroupMaster> {

    @Autowired
    private GroupMasterService groupMasterService;

    @Override
    public GroupMaster convert(String id) {
        try {
            return groupMasterService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
