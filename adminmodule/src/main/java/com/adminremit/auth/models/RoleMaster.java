package com.adminremit.auth.models;

import com.adminremit.auth.enums.RoleCategory;
import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.RoleKeys;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role_master")
@Audited
public class RoleMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name")
    @NotEmpty(message = "Role name is mandatory")
    @NotNull(message = "Role name is not null")
    private String roleName;

    @Column(name = "description",columnDefinition = "TEXT")
    private String roleDescription;

    @OneToMany(mappedBy = "roleMaster",fetch = FetchType.LAZY)
    private List<GroupRoleMapping> groupRoleMappings;

    @OneToMany(mappedBy = "roleMaster",fetch = FetchType.LAZY)
    private List<RoleFeatureMapping> roleFeatureMappings;

    public RoleMaster() {}

    public RoleMaster(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<GroupRoleMapping> getGroupRoleMappings() {
        return groupRoleMappings;
    }

    public void setGroupRoleMappings(List<GroupRoleMapping> groupRoleMappings) {
        this.groupRoleMappings = groupRoleMappings;
    }

    public List<RoleFeatureMapping> getRoleFeatureMappings() {
        return roleFeatureMappings;
    }

    public void setRoleFeatureMappings(List<RoleFeatureMapping> roleFeatureMappings) {
        this.roleFeatureMappings = roleFeatureMappings;
    }
}
