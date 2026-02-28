package com.adminremit.gbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="gbg_non_dvs_api_request")
public class GbgNonDvsApiRequest {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "api_name", length = 1000)
    private String apiName;
    
    @Column(name = "personal_details_id")
    private long personalDetailsId;
    
    @Column(name = "rule_id", length = 1000)
    private String ruleId;
    
    @Column(name = "given_name", length = 1000)
    private String givenName;
    
    @Column(name = "honorific", length = 1000)
    private String honorific;
    
    @Column(name = "middle_name", length = 1000)
    private String middleName;
    
    @Column(name = "surname", length = 1000)
    private String surname;	
    
    @Column(name = "email", length = 1000)
    private String email;
    
    @Column(name = "country", length = 1000)
    private String country;
    
    @Column(name = "flatnumber", length = 1000)
    private String flatNumber;
    
    @Column(name = "postcode", length = 1000)
    private String postCode;
    
    @Column(name = "state", length = 1000)
    private String state;    

    @Column(name = "street_name", length = 1000)
    private String streetName;
    
    @Column(name = "street_number", length = 1000)
    private String streetNumber;
    
    @Column(name = "street_type", length = 1000)
    private String streetType;
    
    @Column(name = "suburb", length = 1000)
    private String suburb;
    
    @Column(name = "dob", length = 1000)
    private Date dob;
    
    @Column(name = "dob_day", length = 1000)
    private long dobDay;
    
    @Column(name = "dob_month", length = 1000)
    private long dobMonth;
    
    @Column(name = "dob_year", length = 1000)
    private long dobYear;
    
    @Column(name = "generate_verification_token")
    private boolean generateVerificationToken;
    
    @Column(name = "home_phone", length = 1000)
    private String homePhone;
    
    @Column(name = "api_request", columnDefinition = "text")
    private String apiRequest;
    
    @Column(name = "verification_id", length = 1000)
    private String verificationId;
    
    @OneToMany(mappedBy = "gbgNonDvsApiRequest", cascade = CascadeType.ALL)
    private List<GbgNonDvsApiNameValuePair> gbgNonDvsApiNameValuePair = new ArrayList<>();
    
    @OneToOne(mappedBy = "gbgNonDvsApiRequest")
    private GbgNonDvsApiResponse gbgNonDvsApiResponse;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public long getPersonalDetailsId() {
		return personalDetailsId;
	}

	public void setPersonalDetailsId(long personalDetailsId) {
		this.personalDetailsId = personalDetailsId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getHonorific() {
		return honorific;
	}

	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getDobDay() {
		return dobDay;
	}

	public void setDobDay(long dobDay) {
		this.dobDay = dobDay;
	}

	public long getDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(long dobMonth) {
		this.dobMonth = dobMonth;
	}

	public long getDobYear() {
		return dobYear;
	}

	public void setDobYear(long dobYear) {
		this.dobYear = dobYear;
	}

	public boolean isGenerateVerificationToken() {
		return generateVerificationToken;
	}

	public void setGenerateVerificationToken(boolean generateVerificationToken) {
		this.generateVerificationToken = generateVerificationToken;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public List<GbgNonDvsApiNameValuePair> getGbgNonDvsApiNameValuePair() {
		return gbgNonDvsApiNameValuePair;
	}

	public void setGbgNonDvsApiNameValuePair(List<GbgNonDvsApiNameValuePair> gbgNonDvsApiNameValuePair) {
		this.gbgNonDvsApiNameValuePair = gbgNonDvsApiNameValuePair;
	}

	public String getApiRequest() {
		return apiRequest;
	}

	public void setApiRequest(String apiRequest) {
		this.apiRequest = apiRequest;
	}

	public String getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}

	public GbgNonDvsApiResponse getGbgNonDvsApiResponse() {
		return gbgNonDvsApiResponse;
	}

	public void setGbgNonDvsApiResponse(GbgNonDvsApiResponse gbgNonDvsApiResponse) {
		this.gbgNonDvsApiResponse = gbgNonDvsApiResponse;
	}	
}
