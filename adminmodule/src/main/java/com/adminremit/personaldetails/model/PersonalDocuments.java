package com.adminremit.personaldetails.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "personal_documents")
public class PersonalDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentTypes documentTypes;

    @OneToOne(mappedBy = "documents")
    private PersonalDocumentDetails personalDocumentDetails;

    @ManyToOne
    @JoinColumn(name = "personal_details")
    private PersonalDetails personalDetails;

    @Column(name = "province")
    private String province;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentTypes getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(DocumentTypes documentTypes) {
        this.documentTypes = documentTypes;
    }

    public PersonalDocumentDetails getPersonalDocumentDetails() {
        return personalDocumentDetails;
    }

    public void setPersonalDocumentDetails(PersonalDocumentDetails personalDocumentDetails) {
        this.personalDocumentDetails = personalDocumentDetails;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
