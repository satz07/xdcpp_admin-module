package com.adminremit.auth.dto;

import java.util.Date;
import java.util.List;

import com.adminremit.auth.models.User;
import com.adminremit.beneficiary.enums.Gender;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.personaldetails.model.GBGVerificationRegisterVerificationResponse;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;

public class PersonalDetailsDTO {

	private Long id;
	private String fullName;
	private String lastName;
	private String middleName;
	private Date dob;
	private Gender gender;
	private String nationality;
	private String phoneNumber;
	private String flatNumber;
	private String streetNumber;
	private String streetName;
	private String streetType;
	private String suburb;
	private String postalCode;
	private String country;
	private String province;
	private Date createAt = new Date();
	private Date updateAt = new Date();
	private String createdBy;
	private String updatedBy;
	private boolean isDeleted;
	private List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponse;
	private List<PersonalDocuments> personalDocuments;
	private Long userId;
	private String uId;
	private String email;
	private String address1;
	private String address2;
	private User user;
    private String riskProfile;

	public PersonalDetailsDTO(PersonalDetails pd) {
		super();
		this.id = pd.getId();
		this.fullName = pd.getFullName();
		this.lastName = pd.getLastName();
		this.middleName = pd.getMiddleName();
		this.dob = pd.getDob();
		this.gender = pd.getGender();
		this.nationality = pd.getNationality();
		this.phoneNumber = pd.getPhoneNumber();
		this.flatNumber = pd.getFlatNumber();
		this.streetNumber = pd.getStreetNumber();
		this.streetName = pd.getStreetName();
		this.streetType = pd.getStreetType();
		this.suburb = pd.getSuburb();
		this.postalCode = pd.getPostalCode();
		this.country = pd.getCountry();
		this.province = pd.getProvince();
		this.createAt = pd.getCreateAt();
		this.updateAt = pd.getUpdateAt();
		this.createdBy = pd.getCreatedBy();
		this.updatedBy = pd.getUpdatedBy();
		this.isDeleted = pd.isDeleted();
		this.gbgVerificationRegisterVerificationResponse = pd.getGbgVerificationRegisterVerificationResponse();
		this.personalDocuments = pd.getPersonalDocuments();
		this.userId = pd.getUserId();
		this.uId = pd.getuId();
		this.email = pd.getUsers().getEmail();
		this.riskProfile = pd.getRiskProfile();
		this.user = new User();
		
	}
	
    public String getRiskProfile() {
		return riskProfile;
	}

	public void setRiskProfile(String riskProfile) {
		this.riskProfile = riskProfile;
	}
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<GBGVerificationRegisterVerificationResponse> getGbgVerificationRegisterVerificationResponse() {
		return gbgVerificationRegisterVerificationResponse;
	}

	public void setGbgVerificationRegisterVerificationResponse(
			List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponse) {
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
