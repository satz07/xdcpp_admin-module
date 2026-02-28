package com.adminremit.auth.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.models.LocationMaster;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_group_master")
@Audited
public class GroupMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description",columnDefinition = "TEXT")
    private String groupDescription;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationMaster locationMaster;

    //mappedBy = "groupMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<GroupUserMapping> groupUserMappings;

    //mappedBy = "groupMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<GroupRoleMapping> groupRoleMappings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public LocationMaster getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(LocationMaster locationMaster) {
        this.locationMaster = locationMaster;
    }

    public List<GroupUserMapping> getGroupUserMappings() {
        return groupUserMappings;
    }

    public void setGroupUserMappings(List<GroupUserMapping> groupUserMappings) {
        this.groupUserMappings = groupUserMappings;
    }

    public List<GroupRoleMapping> getGroupRoleMappings() {
        return groupRoleMappings;
    }

    public void setGroupRoleMappings(List<GroupRoleMapping> groupRoleMappings) {
        this.groupRoleMappings = groupRoleMappings;
    }
}
