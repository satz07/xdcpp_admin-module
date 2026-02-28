package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.User;
import com.adminremit.auth.models.UserRoleMapping;
import com.adminremit.auth.repository.UserRoleMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleMappingServiceImpl implements UserRoleMappingService {

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMasterService roleMasterService;

    public UserRoleMapping save(UserRoleMapping userRole) {
        return userRoleMappingRepository.save(userRole);
    }

    @Override
    public List<Long> getAllByUserId(Long userId) {
        List<Long> groupIds = null;
        try {
            groupIds = userRoleMappingRepository.getAllByGroupByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupIds;
    }

    @Override
    public List<UserRoleMapping> findAllByGroupIdAndUserRole(Long groupId, Long userId) {
        List<UserRoleMapping> userRoleMappings = null;
        try {
            User user = userService.getByUserId(userId);
            if(user != null) {
                userRoleMappings = userRoleMappingRepository.findAllByGroupIdAndUserRole(groupId,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRoleMappings;
    }

    @Override
    public void deleteRoleMapping(Long userId) {
        try {
            User user = userService.getByUserId(userId);
            List<UserRoleMapping> userRoleMappings = userRoleMappingRepository.findAllByUserRole(user);
            for (UserRoleMapping userRoleMapping: userRoleMappings) {
                userRoleMappingRepository.delete(userRoleMapping);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserRoleMapping> findAllByUserRole(Long roleId) throws RecordNotFoundException {
        return userRoleMappingRepository.findAllByRole(roleMasterService.getById(roleId));
    }
}
