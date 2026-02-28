package com.adminremit.auth.models;

import com.adminremit.auth.enums.FunctionMasterKey;
import com.adminremit.auth.enums.FunctionMasterType;
import com.adminremit.common.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "function_master")
@Audited
public class FunctionMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "function_master_name")
    private String functionMasterName;

    @Column(name = "function_master_type")
    @Enumerated(EnumType.STRING)
    private FunctionMasterType functionMasterType;

    @Column(name = "function_master_key")
    @Enumerated(EnumType.STRING)
    private FunctionMasterKey functionMasterKey;

    @OneToMany(fetch = FetchType.LAZY)
    private List<FeaturesMaster> featuresMasters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctionMasterName() {
        return functionMasterName;
    }

    public void setFunctionMasterName(String functionMasterName) {
        this.functionMasterName = functionMasterName;
    }

    public FunctionMasterType getFunctionMasterType() {
        return functionMasterType;
    }

    public void setFunctionMasterType(FunctionMasterType functionMasterType) {
        this.functionMasterType = functionMasterType;
    }

    public FunctionMasterKey getFunctionMasterKey() {
        return functionMasterKey;
    }

    public void setFunctionMasterKey(FunctionMasterKey functionMasterKey) {
        this.functionMasterKey = functionMasterKey;
    }

    public List<FeaturesMaster> getFeaturesMasters() {
        return featuresMasters;
    }

    public void setFeaturesMasters(List<FeaturesMaster> featuresMasters) {
        this.featuresMasters = featuresMasters;
    }
}
