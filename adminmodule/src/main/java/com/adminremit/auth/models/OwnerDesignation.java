package com.adminremit.auth.models;

import com.adminremit.common.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "admin_owner_designation")
@Audited
public class OwnerDesignation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "designation")
    @NotEmpty(message = "Designation name is mandatory")
    private String designation;

    @OneToMany(mappedBy = "ownerDesignation",fetch = FetchType.LAZY)
    private List<Product> product;

    @OneToMany(mappedBy = "ownerDesignation",fetch = FetchType.LAZY)
    private List<Partner> partners;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
}
