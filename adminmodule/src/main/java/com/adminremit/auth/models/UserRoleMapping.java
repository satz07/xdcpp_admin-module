package com.adminremit.auth.models;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin_user_role_mapping")
@Audited
public class UserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userRole;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleMaster role;

    @Column(name = "group_id")
    private Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserRole() {
        return userRole;
    }

    public void setUserRole(User userRole) {
        this.userRole = userRole;
    }

    public RoleMaster getRole() {
        return role;
    }

    public void setRole(RoleMaster role) {
        this.role = role;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
