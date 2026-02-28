package com.adminremit.aml.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="remitter_aml_check_request")
public class RemitterAmlCheckRequest {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "onfido_app_id",length = 1000)
    private String onfidoAppId;
    
    @Column(name = "uid",length = 1000)
    private String uid;    
    
    @Column(name = "user_id",length = 1000)
    private String  userId;
    
    @Column(name = "report_Names")
    private String  reportNames;
    
    @Column(name = "api_request", columnDefinition = "text")
    private String apiRequest;
    
    @Column(name = "first_name",length = 1000)
    private String  firstName;
    
    @Column(name = "last_name",length = 1000)
    private String  lastName;
    
    @Column(name = "dob",length = 1000)
    private String  dob;
    
    @Column(name = "applicant_app_id",length = 1000)
    private String applicantAppId;
    
    @OneToOne(mappedBy = "remitterAmlCheckRequest")
    private RemitterAmlCheckResponse remitterAmlCheckResponse;

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

	public String getOnfidoAppId() {
		return onfidoAppId;
	}

	public void setOnfidoAppId(String onfidoAppId) {
		this.onfidoAppId = onfidoAppId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApiRequest() {
		return apiRequest;
	}

	public void setApiRequest(String apiRequest) {
		this.apiRequest = apiRequest;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getApplicantAppId() {
		return applicantAppId;
	}

	public void setApplicantAppId(String applicantAppId) {
		this.applicantAppId = applicantAppId;
	}

	public RemitterAmlCheckResponse getRemitterAmlCheckResponse() {
		return remitterAmlCheckResponse;
	}

	public void setRemitterAmlCheckResponse(RemitterAmlCheckResponse remitterAmlCheckResponse) {
		this.remitterAmlCheckResponse = remitterAmlCheckResponse;
	}

	public String getReportNames() {
		return reportNames;
	}

	public void setReportNames(String reportNames) {
		this.reportNames = reportNames;
	}	
}
