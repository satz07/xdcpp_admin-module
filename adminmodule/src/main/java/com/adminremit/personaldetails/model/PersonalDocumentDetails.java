package com.adminremit.personaldetails.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.adminremit.personaldetails.enums.MediCareCardTypes;

@Entity
@Table(name = "personal_document_details")
public class PersonalDocumentDetails {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private PersonalDocuments documents;

    @Column(name = "document_expire_date")
    private String documentExpDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "medicare_card_type")
    private MediCareCardTypes mediCareCardTypes;

    public PersonalDocuments getDocuments() {
        return documents;
    }

    public void setDocuments(PersonalDocuments documents) {
        this.documents = documents;
    }

    public String getDocumentExpDate() {
        return documentExpDate;
    }

    public void setDocumentExpDate(String documentExpDate) {
        this.documentExpDate = documentExpDate;
    }

    public MediCareCardTypes getMediCareCardTypes() {
        return mediCareCardTypes;
    }

    public void setMediCareCardTypes(MediCareCardTypes mediCareCardTypes) {
        this.mediCareCardTypes = mediCareCardTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
