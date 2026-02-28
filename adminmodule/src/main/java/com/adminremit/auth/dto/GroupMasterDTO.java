package com.adminremit.auth.dto;

public class GroupMasterDTO {

    private Long id;

    private String groupName;

    private String groupDescription;

    private Long locationMaster;

    private Boolean publish;

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

    public Long getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(Long locationMaster) {
        this.locationMaster = locationMaster;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}
