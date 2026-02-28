package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.repository.GroupMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupMasterServiceImpl implements GroupMasterService{

    @Autowired
    private GroupMasterRepository groupMasterRepository;

    public GroupMaster save(GroupMaster groupMaster) {
        if(groupMaster.getId() == null) {
            groupMaster.setPublish(true);
            groupMaster =groupMasterRepository.save(groupMaster);
            return groupMaster;
        } else {
            Optional<GroupMaster> groupMaster1 = groupMasterRepository.findById(groupMaster.getId());
            if(groupMaster1.isPresent()) {
                GroupMaster groupMaster2 = groupMaster1.get();
                groupMaster2.setGroupName(groupMaster.getGroupName());
                groupMaster2.setGroupDescription(groupMaster.getGroupDescription());
                groupMaster2.setLocationMaster(groupMaster.getLocationMaster());
                groupMaster2.setPublish(groupMaster.getPublish());
                return groupMasterRepository.save(groupMaster2);
            } {
                groupMaster.setPublish(true);
                groupMaster =groupMasterRepository.save(groupMaster);
                return groupMaster;
            }
        }
    }

    public void deleteGroup(Long id) throws RecordNotFoundException {
        Optional<GroupMaster> groupMaster  = groupMasterRepository.findById(id);
        if(groupMaster.isPresent()) {
            GroupMaster groupMaster1 = groupMaster.get();
            groupMaster1.setIsDeleted(true);
            groupMasterRepository.save(groupMaster1);
        }
    }

    public GroupMaster getById(Long id) throws RecordNotFoundException {
        Optional<GroupMaster> groupMaster = groupMasterRepository.findById(id);
        GroupMaster groupMaster1 = null;
        try {
            if(groupMaster.isPresent()) {
                groupMaster1 = groupMaster.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupMaster1;
    }

    public List<GroupMaster> listOfGroups() {
        List<GroupMaster> groupMasters = new ArrayList<>();
        try {
            List<GroupMaster> groupMasters1 = groupMasterRepository.findAllByIsDeleted(false);
            groupMasters1.forEach((GroupMaster groupMaster) -> {
                if(!groupMaster.getGroupName().equals("Super Admin Group")) {
                    groupMasters.add(groupMaster);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupMasters;
    }

    @Override
    public List<GroupMaster> listOfAllGroups() {
        List<GroupMaster> groupMasters = new ArrayList<>();
        try {
            List<GroupMaster> groupMasters1 = groupMasterRepository.findAllByIsDeleted(false);
            groupMasters1.forEach((GroupMaster groupMaster) -> {
                if(!groupMaster.getGroupName().equals("Super Admin Group")) {
                    groupMasters.add(groupMaster);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupMasters;
    }
}
