package com.adminremit.report.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ownership_details")
public class OwnershipDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_director")
    private boolean isDirector;

    @Column(name = "is_shareholder")
    private boolean isShareHolder;
    
    @Column(name = "is_senior_official")
    private boolean isSeniorOfficial;
    
    @Column(name = "is_beneficial_owner")
    private boolean isBeneficialOwner;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private String dob;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private BusinessInfo businessInfo;

    @Column(name = "user_id")
    private Long userId;
    
    /*private List<DirectorDetailsDTO> directorDetailsDTOS;
    private List<ShareHolderDetailsDTO> shareHolderDetailsDTOS;

    public List<DirectorDetailsDTO> getDirectorDetailsDTOS() {
        return directorDetailsDTOS;
    }

    public void setDirectorDetailsDTOS(List<DirectorDetailsDTO> directorDetailsDTOS) {
        this.directorDetailsDTOS = directorDetailsDTOS;
    }
    
    public List<ShareHolderDetailsDTO> getShareHolderDetailsDTOS() {
        return this.shareHolderDetailsDTOS;
    }

    public void setShareHolderDetailsDTOS(List<ShareHolderDetailsDTO> shareHolderDetailsDTOS) {
        this.shareHolderDetailsDTOS = shareHolderDetailsDTOS;
    }*/
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDirector() {
        return isDirector;
    }

    public boolean getIsDirector() {
        return isDirector;
    }

    public void setDirector(boolean director) {
        isDirector = director;
    }

    public boolean getIsShareHolder() {
        return isShareHolder;
    }

    public void setShareHolder(boolean shareHolder) {
        isShareHolder = shareHolder;
    }
    
    public boolean getIsBeneficialOwner() {
        return this.isBeneficialOwner;
    }

    public void setIsBeneficialOwner(boolean isBeneficialOwner) {
        this.isBeneficialOwner = isBeneficialOwner;
    }

    public String getFirstsName() {
        return firstName;
    }

    public void setFirstsName(String firstsName) {
        this.firstName = firstsName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsSeniorOfficial() {
        return isSeniorOfficial;
    }

    public void setIsSeniorOfficial(boolean isSeniorOfficial) {
        this.isSeniorOfficial = isSeniorOfficial;
    }
    
    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
