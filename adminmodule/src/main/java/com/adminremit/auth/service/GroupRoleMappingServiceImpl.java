package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.GroupRoleMapping;
import com.adminremit.auth.models.GroupUserMapping;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.GroupRoleMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupRoleMappingServiceImpl implements GroupRoleMappingService{

    @Autowired
    private GroupRoleMappingRepository groupRoleMappingRepository;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private GroupMasterService groupMasterService;

    @Override
    public GroupRoleMapping save(GroupRoleMapping groupRoleMapping) {
        if(groupRoleMapping.getId() == null) {
            groupRoleMapping =groupRoleMappingRepository.save(groupRoleMapping);
            return groupRoleMapping;
        } else {
            Optional<GroupRoleMapping> groupRoleMapping1 = groupRoleMappingRepository.findById(groupRoleMapping.getId());
            if(groupRoleMapping1.isPresent()) {
                GroupRoleMapping groupRoleMapping2 = groupRoleMapping1.get();
                return groupRoleMappingRepository.save(groupRoleMapping2);
            } {
                groupRoleMapping =groupRoleMappingRepository.save(groupRoleMapping);
                return groupRoleMapping;
            }
        }
    }

    @Override
    public void deleteAllByRole(Long roleId) {
        try {
            RoleMaster roleMaster = roleMasterService.getById(roleId);
            List<GroupRoleMapping> groupRoleMappings = groupRoleMappingRepository.findAllByRoleMaster(roleMaster);
            for (GroupRoleMapping groupRoleMapping:  groupRoleMappings) {
                groupRoleMappingRepository.delete(groupRoleMapping);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GroupRoleMapping> findAllByGroupMaster(Long groupId) {
        List<GroupRoleMapping> groupRoleMappings = null;
        try {
            GroupMaster groupMaster = groupMasterService.getById(groupId);
            if(groupMaster != null) {
                groupRoleMappings = groupRoleMappingRepository.findAllByGroupMaster(groupMaster);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  groupRoleMappings;
    }

    @Override
    public List<RoleMaster> findAllRolesByGroup(Long groupId) {
        List<RoleMaster> roleMasters = new ArrayList<>();
        try {
            GroupMaster groupMaster = groupMasterService.getById(groupId);
            if(groupMaster != null) {
                List<GroupRoleMapping> groupRoleMappings = groupRoleMappingRepository.findAllByGroupMaster(groupMaster);
                for (GroupRoleMapping groupRoleMapping: groupRoleMappings) {
                    roleMasters.add(groupRoleMapping.getRoleMaster());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  roleMasters;
    }

    @Override
    public List<GroupRoleMapping> findByRoleMaster(Long roleId) throws RecordNotFoundException {
        List<GroupRoleMapping> groupRoleMappings = groupRoleMappingRepository.findAllByRoleMaster(roleMasterService.getById(roleId));
        return groupRoleMappings;
    }

    @Override
    public void deleteAllRoleByGroup(Long groupId) throws RecordNotFoundException {
        try {
            GroupMaster groupMaster = groupMasterService.getById(groupId);
            if(groupMaster != null) {
                List<GroupRoleMapping> groupRoleMappings = groupRoleMappingRepository.findAllByGroupMaster(groupMaster);
                for (GroupRoleMapping groupRoleMapping: groupRoleMappings) {
                    groupRoleMappingRepository.delete(groupRoleMapping);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
