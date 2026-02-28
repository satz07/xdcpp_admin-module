package com.adminremit.auth.repository;

import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.GroupRoleMapping;
import com.adminremit.auth.models.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRoleMappingRepository extends JpaRepository<GroupRoleMapping,Long> {
    public List<GroupRoleMapping> findAllByRoleMaster(RoleMaster roleMaster);
    public List<GroupRoleMapping> findAllByGroupMaster(GroupMaster groupMaster);
}
