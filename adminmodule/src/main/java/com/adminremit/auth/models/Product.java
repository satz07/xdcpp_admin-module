package com.adminremit.auth.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.models.LocationMaster;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "admin_products")
@Audited
public class Product extends BaseEntity {

    @Id
    @SequenceGenerator(name = "productCode", sequenceName = "productCodeSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productCode")
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    @NotEmpty(message = "Product Name is mandatory")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "official_email")
    private String officialEmail;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private OwnerDesignation ownerDesignation;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationMaster locationMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public OwnerDesignation getOwnerDesignation() {
        return ownerDesignation;
    }

    public void setOwnerDesignation(OwnerDesignation ownerDesignation) {
        this.ownerDesignation = ownerDesignation;
    }

    public LocationMaster getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(LocationMaster locationMaster) {
        this.locationMaster = locationMaster;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
