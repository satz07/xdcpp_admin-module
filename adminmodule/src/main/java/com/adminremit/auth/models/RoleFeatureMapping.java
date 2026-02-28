package com.adminremit.auth.models;

import com.adminremit.common.models.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "role_feature_mapping")
@Audited
public class RoleFeatureMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "function_id")
    private Long functionId;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private FeaturesMaster featuresMaster;

    @ManyToOne
    @JoinColumn(name = "role_master_id")
    private RoleMaster roleMaster;

    @Column(name = "item_order")
    private Integer itemOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeaturesMaster getFeaturesMaster() {
        return featuresMaster;
    }

    public void setFeaturesMaster(FeaturesMaster featuresMaster) {
        this.featuresMaster = featuresMaster;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public RoleMaster getRoleMaster() {
        return roleMaster;
    }

    public void setRoleMaster(RoleMaster roleMaster) {
        this.roleMaster = roleMaster;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }
}
