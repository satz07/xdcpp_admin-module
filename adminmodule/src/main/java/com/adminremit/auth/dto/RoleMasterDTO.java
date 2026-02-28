package com.adminremit.auth.dto;

import java.util.List;

public class RoleMasterDTO {

    private Long id;

    private String roleName;

    private Boolean publish;

    private String roleDescription;

    private List<String> groupRoleMappings;

    private List<String> roleFeatureMappings;

    private List<FunctionMastersList> functionMastersLists;

    private List<FeaturesMastersList> featuresMastersLists;

    private List<FeatureFunctionDTO> featureFunctionDTOS;

    private int count;

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

    public Boolean isPublish() {
        return publish;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<String> getGroupRoleMappings() {
        return groupRoleMappings;
    }

    public void setGroupRoleMappings(List<String> groupRoleMappings) {
        this.groupRoleMappings = groupRoleMappings;
    }

    public List<String> getRoleFeatureMappings() {
        return roleFeatureMappings;
    }

    public void setRoleFeatureMappings(List<String> roleFeatureMappings) {
        this.roleFeatureMappings = roleFeatureMappings;
    }

    public List<FunctionMastersList> getFunctionMastersLists() {
        return functionMastersLists;
    }

    public void setFunctionMastersLists(List<FunctionMastersList> functionMastersLists) {
        this.functionMastersLists = functionMastersLists;
    }

    public List<FeaturesMastersList> getFeaturesMastersLists() {
        return featuresMastersLists;
    }

    public void setFeaturesMastersLists(List<FeaturesMastersList> featuresMastersLists) {
        this.featuresMastersLists = featuresMastersLists;
    }

    public List<FeatureFunctionDTO> getFeatureFunctionDTOS() {
        return featureFunctionDTOS;
    }

    public void setFeatureFunctionDTOS(List<FeatureFunctionDTO> featureFunctionDTOS) {
        this.featureFunctionDTOS = featureFunctionDTOS;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
