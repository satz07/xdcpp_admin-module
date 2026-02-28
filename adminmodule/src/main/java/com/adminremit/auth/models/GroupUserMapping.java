package com.adminremit.auth.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "group_user_mapping")
@Audited
public class GroupUserMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupMaster groupMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GroupMaster getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(GroupMaster groupMaster) {
        this.groupMaster = groupMaster;
    }
}
