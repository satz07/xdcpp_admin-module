package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.GroupRoleMapping;
import com.adminremit.auth.models.GroupUserMapping;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface GroupRoleMappingService extends BaseService<GroupRoleMapping> {
    public void deleteAllByRole(Long roleId);
    public List<GroupRoleMapping> findAllByGroupMaster(Long groupId);
    public List<RoleMaster> findAllRolesByGroup(Long groupId);
    public List<GroupRoleMapping> findByRoleMaster(Long roleId) throws RecordNotFoundException;
    public void deleteAllRoleByGroup(Long groupId) throws RecordNotFoundException;
}
