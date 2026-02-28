package com.adminremit.auth.dto;

import com.adminremit.auth.models.OwnerDesignation;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

public class PartnerDTO {

    private Long id;

    @NotEmpty(message = "Partner names is mandatory")
    private String partnerName;

    private String partnerId;

    private String description;

    private String address;

    private Long product;

    private String officialEmail;

    private String phoneNumber;

    private Long locationMaster;

    private String partnerDescription;

    private String ownerName;

    private Long ownerDesignation;

    private Boolean publish;

    private String countryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(Long locationMaster) {
        this.locationMaster = locationMaster;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerDesignation() {
        return ownerDesignation;
    }

    public void setOwnerDesignation(Long ownerDesignation) {
        this.ownerDesignation = ownerDesignation;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
