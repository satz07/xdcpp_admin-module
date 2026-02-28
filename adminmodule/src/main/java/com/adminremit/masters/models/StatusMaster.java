package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "status_master")
@Audited
public class StatusMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status_code")
    private int statusCode;

    @Column(name = "status_description")
    private String statusDescription;

    @Column(name = "customer_status_description")
    private String customerStatusDesc;

    @Column(name = "sub_status_desc")
    private String subStatusDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getCustomerStatusDesc() {
        return customerStatusDesc;
    }

    public void setCustomerStatusDesc(String customerStatusDesc) {
        this.customerStatusDesc = customerStatusDesc;
    }

    public String getSubStatusDesc() {
        return subStatusDesc;
    }

    public void setSubStatusDesc(String subStatusDesc) {
        this.subStatusDesc = subStatusDesc;
    }
}
