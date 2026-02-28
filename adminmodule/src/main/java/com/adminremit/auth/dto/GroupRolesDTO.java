package com.adminremit.auth.dto;

import java.util.List;

public class GroupRolesDTO {

    private String groupMaster;

    private List<String> roleMaster;

    public String getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(String groupMaster) {
        this.groupMaster = groupMaster;
    }

    public List<String> getRoleMaster() {
        return roleMaster;
    }

    public void setRoleMaster(List<String> roleMaster) {
        this.roleMaster = roleMaster;
    }
}
