package com.adminremit.auth.repository;

import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.models.User;
import com.adminremit.auth.models.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, Long> {

    // WEB3 migration changes - Use JPQL instead of native query so table naming strategy applies
    @Query("SELECT DISTINCT urm.groupId FROM UserRoleMapping urm WHERE urm.userRole.id = :userId")
    List<Long> getAllByGroupByUserId(@Param("userId") Long userId);

    List<UserRoleMapping> findAllByGroupIdAndUserRole(Long groupId, User userRole);
    List<UserRoleMapping> findAllByUserRole(User userRole);
    List<UserRoleMapping> findAllByRole(RoleMaster roleMaster);
}
