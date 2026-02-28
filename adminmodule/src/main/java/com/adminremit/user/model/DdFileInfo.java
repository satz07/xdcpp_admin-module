package com.adminremit.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.adminremit.operations.model.AuditDetails;
import com.adminremit.user.enums.DocumentTypeEnum;

@Entity
    @Table(name = "dd_files_info")
    public class DdFileInfo  extends AuditDetails{

        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        private String id;

        private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DocumentTypeEnum documentType;

        @Column(name="image_url")
        private String imageUrl;

        @Column(name="user_id")
        private Long userId;


        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



        public void setId(String id) {
            this.id = id;
        }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DocumentTypeEnum getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeEnum documentType) {
        this.documentType = documentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}