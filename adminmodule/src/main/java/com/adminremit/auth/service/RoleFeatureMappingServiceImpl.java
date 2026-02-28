package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.RoleFeatureMapping;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.RoleFeatureMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleFeatureMappingServiceImpl implements RoleFeatureMappingService {

    @Autowired
    private RoleFeatureMappingRepository roleFeatureMappingRepository;

    @Autowired
    private RoleMasterService roleMasterService;

    @Override
    public RoleFeatureMapping save(RoleFeatureMapping roleFeatureMapping) {
        if(roleFeatureMapping.getId() == null) {
            roleFeatureMapping = roleFeatureMappingRepository.save(roleFeatureMapping);
            return roleFeatureMapping;
        } else {
            Optional<RoleFeatureMapping> roleFeatureMapping1 = roleFeatureMappingRepository.findById(roleFeatureMapping.getId());
            if(roleFeatureMapping1.isPresent()) {
                RoleFeatureMapping roleFeatureMapping2 = roleFeatureMapping1.get();
                return roleFeatureMappingRepository.save(roleFeatureMapping2);
            } {
                roleFeatureMapping =roleFeatureMappingRepository.save(roleFeatureMapping);
                return roleFeatureMapping;
            }
        }
    }

    @Override
    public List<Long> getAllByRoleAndFunctionGroup(Long roleId) {
        List<Long> roleFeatureMappings = null;
        try {
            roleFeatureMappings = roleFeatureMappingRepository.getAllByGroupByFunctionId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleFeatureMappings;
    }

    @Override
    public List<RoleFeatureMapping> getAllFeatureByFunctionAndRole(Long functionId, Long roleId) {
        List<RoleFeatureMapping> roleFeatureMappings = null;
        try {
            RoleMaster roleMaster = roleMasterService.getById(roleId);
            roleFeatureMappings = roleFeatureMappingRepository.findAllByFunctionIdAndRoleMaster(functionId,roleMaster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleFeatureMappings;
    }

    @Override
    public void deleteAllFeatureByRole(Long roleId) throws RecordNotFoundException {
        try {
            RoleMaster roleMaster = roleMasterService.getById(roleId);
            List<RoleFeatureMapping> roleFeatureMappings = roleFeatureMappingRepository.findAllByRoleMaster(roleMaster);
            for (RoleFeatureMapping roleFeatureMapping: roleFeatureMappings) {
                roleFeatureMappingRepository.delete(roleFeatureMapping);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
