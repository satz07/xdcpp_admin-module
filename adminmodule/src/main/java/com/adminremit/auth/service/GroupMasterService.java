package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface GroupMasterService extends BaseService<GroupMaster> {
    public void deleteGroup(Long id) throws RecordNotFoundException;
    public GroupMaster getById(Long id) throws RecordNotFoundException;
    public List<GroupMaster> listOfGroups();
    public List<GroupMaster> listOfAllGroups();
}
