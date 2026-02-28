package com.adminremit.auth.dto;

import java.util.List;

public class FeatureFunctionDTO {

    private String functionMaster;

    private List<String> featureMasters;

    public String getFunctionMaster() {
        return functionMaster;
    }

    public void setFunctionMaster(String functionMaster) {
        this.functionMaster = functionMaster;
    }

    public List<String> getFeatureMasters() {
        return featureMasters;
    }

    public void setFeatureMasters(List<String> featureMasters) {
        this.featureMasters = featureMasters;
    }
}
