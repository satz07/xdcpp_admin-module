package com.adminremit.auth.repository;

import com.adminremit.auth.models.RoleFeatureMapping;
import com.adminremit.auth.models.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
public interface RoleFeatureMappingRepository extends JpaRepository<RoleFeatureMapping,Long> {
    public List<RoleFeatureMapping> findAllByFunctionIdAndRoleMaster(Long functionId, RoleMaster roleMaster);
    @Query(value = "SELECT function_id FROM role_feature_mapping WHERE role_master_id=:roleId GROUP BY function_id", nativeQuery = true)
    public List<Long> getAllByGroupByFunctionId(Long roleId);
    public List<RoleFeatureMapping> findAllByRoleMaster(RoleMaster roleMaster);
}
