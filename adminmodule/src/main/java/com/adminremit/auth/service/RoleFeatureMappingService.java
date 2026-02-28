package com.adminremit.auth.service;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.RoleFeatureMapping;

import java.util.List;

public interface RoleFeatureMappingService {
    public RoleFeatureMapping save(RoleFeatureMapping roleFeatureMapping);
    public List<Long> getAllByRoleAndFunctionGroup(Long roleId);
    public List<RoleFeatureMapping> getAllFeatureByFunctionAndRole(Long functionId,Long roleId);
    public void deleteAllFeatureByRole(Long roleId) throws RecordNotFoundException;
}
