package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.enums.MarkerCheckerStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "maker_checker")
@Audited
public class MakerAndChecker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MarkerCheckerStatus markerCheckerStatus;

    @Column(name = "maker_checker_function")
    @Enumerated(EnumType.STRING)
    private MakerCheckerFunction makerCheckerFunction;

    @Column(name = "record_id")
    private Long recordId;

    @OneToMany(mappedBy = "makerCheckerId",fetch = FetchType.LAZY)
    private List<MakerAndCheckerValues> makerAndCheckerValues;

    @Column(name = "publish",columnDefinition="boolean default true")
    private Boolean publish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public MarkerCheckerStatus getMarkerCheckerStatus() {
        return markerCheckerStatus;
    }

    public void setMarkerCheckerStatus(MarkerCheckerStatus markerCheckerStatus) {
        this.markerCheckerStatus = markerCheckerStatus;
    }

    public MakerCheckerFunction getMakerCheckerFunction() {
        return makerCheckerFunction;
    }

    public void setMakerCheckerFunction(MakerCheckerFunction makerCheckerFunction) {
        this.makerCheckerFunction = makerCheckerFunction;
    }

    public List<MakerAndCheckerValues> getMakerAndCheckerValues() {
        return makerAndCheckerValues;
    }

    public void setMakerAndCheckerValues(List<MakerAndCheckerValues> makerAndCheckerValues) {
        this.makerAndCheckerValues = makerAndCheckerValues;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Boolean isPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Boolean getPublish() {
        return publish;
    }
}
