package com.adminremit.auth.models;

import com.adminremit.auth.enums.FeatureType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "features_master")
@Audited
public class FeaturesMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "feature_name")
    private String featureName;

    @Column(name = "feature_end_point")
    private String featureEndPoint;

    @Column(name = "publish")
    private boolean publish;

    @Column(name = "read_access")
    private boolean readAccess;

    @Column(name = "write_access")
    private boolean writeAccess;

    @Column(name = "delete_access")
    private boolean deleteAccess;

    @Column(name = "approve_access")
    private boolean approveAccess;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private FunctionMaster functionMaster;

    @Column(name = "feature_type")
    @Enumerated(EnumType.STRING)
    private FeatureType featureType;

    @Column(name = "is_delete",columnDefinition="boolean default false")
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public boolean isReadAccess() {
        return readAccess;
    }

    public void setReadAccess(boolean readAccess) {
        this.readAccess = readAccess;
    }

    public boolean isWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(boolean writeAccess) {
        this.writeAccess = writeAccess;
    }

    public boolean isDeleteAccess() {
        return deleteAccess;
    }

    public void setDeleteAccess(boolean deleteAccess) {
        this.deleteAccess = deleteAccess;
    }

    public boolean isApproveAccess() {
        return approveAccess;
    }

    public void setApproveAccess(boolean approveAccess) {
        this.approveAccess = approveAccess;
    }

    public FunctionMaster getFunctionMaster() {
        return functionMaster;
    }

    public void setFunctionMaster(FunctionMaster functionMaster) {
        this.functionMaster = functionMaster;
    }

    public String getFeatureEndPoint() {
        return featureEndPoint;
    }

    public void setFeatureEndPoint(String featureEndPoint) {
        this.featureEndPoint = featureEndPoint;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
