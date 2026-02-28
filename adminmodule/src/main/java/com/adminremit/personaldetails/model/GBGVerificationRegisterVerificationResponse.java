package com.adminremit.personaldetails.model;

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.adminremit.personaldetails.enums.GBGApiNames;

@Entity
@Table(name = "gbg_verification_result")
public class GBGVerificationRegisterVerificationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "verification_token")
    private String localVerificationToken;

    @Column(name = "verification_status")
    private String localOverallVerificationStatus;

    @Column(name = "verification_id")
    private String localVerificationId;
    
    @Column(name = "source_list")
    private String sourceList;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();

    @Column(name = "create_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "is_delete",columnDefinition="boolean default false")
    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_details_id", referencedColumnName = "id")
    private PersonalDetails personalDetails;

    @Column(name = "api_name")
    @Enumerated(EnumType.STRING)
    private GBGApiNames gbgApiNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalVerificationToken() {
        return localVerificationToken;
    }

    public void setLocalVerificationToken(String localVerificationToken) {
        this.localVerificationToken = localVerificationToken;
    }

    public String getLocalOverallVerificationStatus() {
        return localOverallVerificationStatus;
    }

    public void setLocalOverallVerificationStatus(String localOverallVerificationStatus) {
        this.localOverallVerificationStatus = localOverallVerificationStatus;
    }

    public String getLocalVerificationId() {
        return localVerificationId;
    }

    public void setLocalVerificationId(String localVerificationId) {
        this.localVerificationId = localVerificationId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public GBGApiNames getGbgApiNames() {
        return gbgApiNames;
    }

    public void setGbgApiNames(GBGApiNames gbgApiNames) {
        this.gbgApiNames = gbgApiNames;
    }

	public String getSourceList() {
		return sourceList;
	}

	public void setSourceList(String sourceList) {
		this.sourceList = sourceList;
	}
}
