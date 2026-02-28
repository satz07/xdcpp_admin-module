package com.adminremit.auth.dto;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.LocationMaster;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class ProductDTO {

    private Long id;

    private String productCode;

    @NotEmpty(message = "Product Name is mandatory")
    private String productName;

    private String productDescription;

    private String ownerName;

    private String officialEmail;

    private String countryCode;

    private String phoneNumber;

    private Long ownerDesignation;

    private Long locationMaster;

    private Boolean publish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Long getOwnerDesignation() {
        return ownerDesignation;
    }

    public void setOwnerDesignation(Long ownerDesignation) {
        this.ownerDesignation = ownerDesignation;
    }

    public Long getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(Long locationMaster) {
        this.locationMaster = locationMaster;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}
