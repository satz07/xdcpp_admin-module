package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.RoleMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleMasterServiceImpl implements RoleMasterService{

    @Autowired
    RoleMasterRepository roleMasterRepository;

    public RoleMaster findByRoleName(String roleName) {
        RoleMaster roleMaster = roleMasterRepository.findByRoleName(roleName);
        return roleMaster;
    }

    public RoleMaster save(RoleMaster roleMaster) {
        if(roleMaster.getId() == null) {
            roleMaster.setPublish(true);
            roleMaster =roleMasterRepository.save(roleMaster);
            return roleMaster;
        } else {
            Optional<RoleMaster> designation = roleMasterRepository.findById(roleMaster.getId());
            if(designation.isPresent()) {
                RoleMaster updateRolemaster = designation.get();
                updateRolemaster.setPublish(roleMaster.getPublish());
                updateRolemaster.setRoleName(roleMaster.getRoleName());
                updateRolemaster.setRoleDescription(roleMaster.getRoleDescription());
                return roleMasterRepository.save(updateRolemaster);
            } {
                roleMaster.setPublish(true);
                roleMaster =roleMasterRepository.save(roleMaster);
                return roleMaster;
            }
        }
    }

    public void deleteRole(Long id) throws RecordNotFoundException {
        Optional<RoleMaster> roleMaster = roleMasterRepository.findById(id);
        if(roleMaster.isPresent()) {
            RoleMaster roleMaster1 = roleMaster.get();
            roleMaster1.setIsDeleted(true);
            roleMasterRepository.save(roleMaster1);
        }
    }

    public RoleMaster getById(Long id) throws RecordNotFoundException {
        Optional<RoleMaster> roleMaster = roleMasterRepository.findById(id);
        RoleMaster roleMaster1 = null;
        if(roleMaster.isPresent()) {
            roleMaster1 = roleMaster.get();
        }
        return roleMaster1;
    }

    public List<RoleMaster> listOfRoles() {
        List<RoleMaster> roleMasters = null;
        roleMasters = roleMasterRepository.findAllByIsDeleted(false);
        return roleMasters;
    }

    public List<RoleMaster> listOfAllRoles() {
        List<RoleMaster> roleMasters = null;
        roleMasters = roleMasterRepository.findAllByIsDeleted(false);
        return roleMasters;
    }

    public RoleMaster get(Long id) {
        return roleMasterRepository.getOne(id);
    }

}
