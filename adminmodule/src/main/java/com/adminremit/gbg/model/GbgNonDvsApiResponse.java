package com.adminremit.gbg.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="gbg_non_dvs_api_response")
public class GbgNonDvsApiResponse {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "api_name")
    private String apiName;
    
    @Column(name = "personal_details_id")
    private long personalDetailsId;
    
    @Column(name = "verificationId", length = 1000)
    private String verificationId;
    
    @Column(name = "verification_status", length = 1000)
    private String verificationStatus;
    
    @Column(name = "verification_token", length = 1000)
    private String verificationToken;
    
    @Column(name = "api_response", columnDefinition = "text")
    private String apiResponse;
    
    @Column(name = "exception", columnDefinition = "text")
    private String exception;
    
    @Column(name = "error_returned")
    private boolean errorReturned;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gbg_non_dvs_api_request_id", referencedColumnName = "id")
    private GbgNonDvsApiRequest gbgNonDvsApiRequest;

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

	public String getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(String apiResponse) {
		this.apiResponse = apiResponse;
	}

	public GbgNonDvsApiRequest getGbgNonDvsApiRequest() {
		return gbgNonDvsApiRequest;
	}

	public void setGbgNonDvsApiRequest(GbgNonDvsApiRequest gbgNonDvsApiRequest) {
		this.gbgNonDvsApiRequest = gbgNonDvsApiRequest;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public boolean isErrorReturned() {
		return errorReturned;
	}

	public void setErrorReturned(boolean errorReturned) {
		this.errorReturned = errorReturned;
	}	
}
