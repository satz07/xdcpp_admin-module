package com.adminremit.auth.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.models.LocationMaster;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_partner")
@Audited
public class Partner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "description")
    private String description;

    @Column(name = "adress")
    private String address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "official_email")
    private String officialEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationMaster locationMaster;

    @OneToMany(mappedBy = "partner",fetch = FetchType.LAZY)
    private List<User> userList;

    @Column(name = "partner_description")
    private String partnerDescription;

    @Column(name = "owner_name")
    private String ownerName;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private OwnerDesignation ownerDesignation;

    @Column(name = "dialing_code")
    private String dialingCode;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public LocationMaster getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(LocationMaster locationMaster) {
        this.locationMaster = locationMaster;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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

    public OwnerDesignation getOwnerDesignation() {
        return ownerDesignation;
    }

    public void setOwnerDesignation(OwnerDesignation ownerDesignation) {
        this.ownerDesignation = ownerDesignation;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }
}
