package com.adminremit.auth.service;

import com.adminremit.auth.models.GroupUserMapping;
import com.adminremit.auth.models.User;
import com.adminremit.auth.repository.GroupUserMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupUserMappingServiceImpl implements GroupUserMappingService{

    @Autowired
    private GroupUserMappingRepository groupUserMappingRepository;

    public GroupUserMapping save(GroupUserMapping groupUserMapping) {
        if(groupUserMapping.getId() == null) {
            groupUserMapping = groupUserMappingRepository.save(groupUserMapping);
            return groupUserMapping;
        } else {
            Optional<GroupUserMapping> groupUserMapping1 = groupUserMappingRepository.findById(groupUserMapping.getId());
            if(groupUserMapping1.isPresent()) {
                GroupUserMapping updateGroupUser = groupUserMapping1.get();
                return groupUserMappingRepository.save(updateGroupUser);
            } {
                groupUserMapping =groupUserMappingRepository.save(groupUserMapping);
                return groupUserMapping;
            }
        }
    }
}
