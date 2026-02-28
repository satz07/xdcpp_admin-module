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
@Table(name="beneficiary_aml_check_request")
public class BeneficiaryAmlCheckRequest {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "beneficiary_id",length = 1000)
    private long beneficiaryId; 
    
    @Column(name = "uid",length = 1000)
    private String uid;    
    
    @Column(name = "user_id",length = 1000)
    private String  userId;
    
    @Column(name = "api_request", columnDefinition = "text")
    private String apiRequest;
    
    @Column(name = "first_name",length = 1000)
    private String  firstName;
    
    @Column(name = "last_name",length = 1000)
    private String  lastName;
    
    @Column(name = "applicant_app_id",length = 1000)
    private String applicantAppId;
    
    @Column(name = "report_Names")
    private String  reportNames;
    
    @OneToOne(mappedBy = "beneficiaryAmlCheckRequest")
    private BeneficiaryAmlCheckResponse beneficiaryAmlCheckResponse;

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

	public String getApplicantAppId() {
		return applicantAppId;
	}

	public void setApplicantAppId(String applicantAppId) {
		this.applicantAppId = applicantAppId;
	}

	public long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public BeneficiaryAmlCheckResponse getBeneficiaryAmlCheckResponse() {
		return beneficiaryAmlCheckResponse;
	}

	public void setBeneficiaryAmlCheckResponse(BeneficiaryAmlCheckResponse beneficiaryAmlCheckResponse) {
		this.beneficiaryAmlCheckResponse = beneficiaryAmlCheckResponse;
	}

	public String getReportNames() {
		return reportNames;
	}

	public void setReportNames(String reportNames) {
		this.reportNames = reportNames;
	}	
}
