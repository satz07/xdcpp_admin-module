package com.adminremit.common.models;

import com.adminremit.auth.models.Partner;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "publish",columnDefinition="boolean default true")
    private Boolean publish = true;

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
    /*
    @JoinColumn(name = "partner_id")
    @ManyToOne
    private Partner partner;
    */

    public Boolean isPublish() {
        return publish;
    }

    public Boolean getIsPublish() {
        return publish;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }


}
