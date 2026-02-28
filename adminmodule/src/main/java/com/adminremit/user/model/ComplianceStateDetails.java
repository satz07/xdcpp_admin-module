package com.adminremit.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adminremit.masters.enums.ComplianceStatus;
import com.adminremit.operations.model.AuditDetails;

@Entity
@Table(name="compliance_status")

public class ComplianceStateDetails extends AuditDetails{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "compliance_status")
    private ComplianceStatus complianceStatus;

    @Column(name="description")
    private String desc;

    @ManyToOne
    @JoinColumn(name="iptagging_id", nullable=false)
    private IPTagging ipTagging;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IPTagging getIpTagging() {
        return ipTagging;
    }

    public void setIpTagging(IPTagging ipTagging) {
        this.ipTagging = ipTagging;
    }

    public ComplianceStatus getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(ComplianceStatus complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
