package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.MakerCheckerFunction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "maker_check_sla_master")
@Audited
public class MakerAndCheckerSLAMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sla_name")
    private String slaName;

    @Column(name = "maker_checker_function")
    @Enumerated(EnumType.STRING)
    private MakerCheckerFunction makerCheckerFunction;

    @Column(name = "sla_time")
    private int slaTime;

    @Column(name = "maker_checker_field")
    private String makerCheckerField;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlaName() {
        return slaName;
    }

    public void setSlaName(String slaName) {
        this.slaName = slaName;
    }

    public MakerCheckerFunction getMakerCheckerFunction() {
        return makerCheckerFunction;
    }

    public void setMakerCheckerFunction(MakerCheckerFunction makerCheckerFunction) {
        this.makerCheckerFunction = makerCheckerFunction;
    }

    public int getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
        this.slaTime = slaTime;
    }

    public String getMakerCheckerField() {
        return makerCheckerField;
    }

    public void setMakerCheckerField(String makerCheckerField) {
        this.makerCheckerField = makerCheckerField;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
