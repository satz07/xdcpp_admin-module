package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface RoleMasterService extends BaseService<RoleMaster> {
    public RoleMaster findByRoleName(String roleName);
    public void deleteRole(Long id) throws RecordNotFoundException;
    public RoleMaster getById(Long id) throws RecordNotFoundException;
    public List<RoleMaster> listOfRoles();
    public List<RoleMaster> listOfAllRoles();
}
