package com.adminremit.masters.models;

import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.models.User;
import com.adminremit.common.models.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_location_master")
@Audited
public class LocationMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "location")
    private String locationName;

    //mappedBy = "locationMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> product;

    //mappedBy = "locationMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<Partner> partner;

    //mappedBy = "locationMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<GroupMaster> groupMasters;

    //mappedBy = "locationMaster",
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> userList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Partner> getPartner() {
        return partner;
    }

    public void setPartner(List<Partner> partner) {
        this.partner = partner;
    }

    public List<GroupMaster> getGroupMasters() {
        return groupMasters;
    }

    public void setGroupMasters(List<GroupMaster> groupMasters) {
        this.groupMasters = groupMasters;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
