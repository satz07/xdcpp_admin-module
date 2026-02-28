package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.User;
import com.adminremit.auth.models.UserRoleMapping;
import com.adminremit.common.service.BaseService;

import java.util.List;

public interface UserRoleMappingService extends BaseService<UserRoleMapping> {
    public List<Long> getAllByUserId(Long userId);
    public List<UserRoleMapping> findAllByGroupIdAndUserRole(Long groupId, Long userId);
    public void deleteRoleMapping(Long userId);
    public List<UserRoleMapping> findAllByUserRole(Long roleId) throws RecordNotFoundException;
}
