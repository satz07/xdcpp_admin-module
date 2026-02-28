package com.adminremit.auth.repository;

import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.OwnerDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMasterRepository extends JpaRepository<GroupMaster,Long> {
    public List<GroupMaster> findAllByPublishAndIsDeleted(Boolean publish,Boolean isDeleted);
    public List<GroupMaster> findAll();
    public List<GroupMaster> findAllByIsDeleted(Boolean isDeleted);
}
