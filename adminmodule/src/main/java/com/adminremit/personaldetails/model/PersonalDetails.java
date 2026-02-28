package com.adminremit.personaldetails.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.adminremit.auth.models.User;
import com.adminremit.user.model.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.adminremit.beneficiary.enums.Gender;

@Entity
@Table(name = "personal_details")
public class PersonalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "flat_number")
    private String flatNumber;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_type")
    private String streetType;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "province")
    private String province;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();

    @Column(name = "create_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "is_delete",columnDefinition="boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "personalDetails")
    private List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponse;

    @OneToMany(mappedBy = "personalDetails")
    private List<PersonalDocuments> personalDocuments;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "uid")
    private String uId;
    
    @Column(name = "is_consent_checked", columnDefinition="boolean default true")
    private boolean isConsentChecked;
    
    @Column(name = "consent_checked_at")
    private Date consentCheckedAt;
    
    @Column(name = "risk_profile", columnDefinition="varchar(255) default 'low'")
    private String riskProfile;

    public String getRiskProfile() {
		return riskProfile;
	}

	public void setRiskProfile(String riskProfile) {
		this.riskProfile = riskProfile;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<GBGVerificationRegisterVerificationResponse> getGbgVerificationRegisterVerificationResponse() {
        return gbgVerificationRegisterVerificationResponse;
    }

    public void setGbgVerificationRegisterVerificationResponse(List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponse) {
        this.gbgVerificationRegisterVerificationResponse = gbgVerificationRegisterVerificationResponse;
    }

    public List<PersonalDocuments> getPersonalDocuments() {
        return personalDocuments;
    }

    public void setPersonalDocuments(List<PersonalDocuments> personalDocuments) {
        this.personalDocuments = personalDocuments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

	public boolean isConsentChecked() {
		return isConsentChecked;
	}

	public void setConsentChecked(boolean isConsentChecked) {
		this.isConsentChecked = isConsentChecked;
	}

	public Date getConsentCheckedAt() {
		return consentCheckedAt;
	}

	public void setConsentCheckedAt(Date consentCheckedAt) {
		this.consentCheckedAt = consentCheckedAt;
	}

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
