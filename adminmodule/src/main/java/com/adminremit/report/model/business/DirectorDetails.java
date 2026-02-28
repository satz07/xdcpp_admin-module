package com.adminremit.report.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "director_details")
public class DirectorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "type")
    private String directorType;
    
    @Column(name = "is_senior")
    private boolean isSenior;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "role")
    private String roleName;
    
    @Column(name = "dob")
    private String dob;
    
    @Column(name = "is_beneficiary")
    private String isBeneficiary;
    
    @Column(name = "is_beneficiary_owner")
    private String isBeneficiaryOwner;
    
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIsBeneficiary() {
        return isBeneficiary;
    }

    public void setIsBeneficiaryOwner(String isBeneficiaryOwner) {
        this.isBeneficiaryOwner = isBeneficiaryOwner;
    }
    
    public String getIsBeneficiaryOwner() {
        return isBeneficiaryOwner;
    }

    public void setIsBeneficiary(String isBeneficiary) {
        this.isBeneficiary = isBeneficiary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getDirectorType() {
        return directorType;
    }

    public void setDirectorType(String directorType) {
        this.directorType = directorType;
    }
    
    public Boolean getIsSenior() {
        return isSenior;
    }

    public void setIsSenior(Boolean isSenior) {
        this.isSenior = isSenior;
    }
    
    
}
