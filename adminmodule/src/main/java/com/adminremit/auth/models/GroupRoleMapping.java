package com.adminremit.auth.models;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "group_role_mapping")
@Audited
public class GroupRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupMaster groupMaster;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleMaster roleMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupMaster getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(GroupMaster groupMaster) {
        this.groupMaster = groupMaster;
    }

    public RoleMaster getRoleMaster() {
        return roleMaster;
    }

    public void setRoleMaster(RoleMaster roleMaster) {
        this.roleMaster = roleMaster;
    }
}
