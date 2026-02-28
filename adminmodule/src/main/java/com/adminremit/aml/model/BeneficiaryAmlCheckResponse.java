package com.adminremit.aml.model;

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
@Table(name="beneficiary_aml_check_response")
public class BeneficiaryAmlCheckResponse {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "uid",length = 1000)
    private String uid;    
    
    @Column(name = "beneficiary_id",length = 1000)
    private long beneficiaryId; 
    
    @Column(name = "user_id",length = 1000)
    private String  userId;
    
    @Column(name = "applicant_app_id",length = 1000)
    private String applicantAppId;
    
    @Column(name = "check_id",length = 1000)
    private String  checkId;
    
    @Column(name = "check_result",length = 1000)
    private String  checkResult;
    
    @Column(name = "check_status",length = 1000)
    private String  checkStatus;
    
    @Column(name = "report_id",length = 1000)
    private String  reportId;
    
    @Column(name = "report_name",length = 1000)
    private String  reportName;
    
    @Column(name = "report_result",length = 1000)
    private String  reportResult;
    
    @Column(name = "report_status",length = 1000)
    private String  reportStatus;
    
    @Column(name = "check_api_response", columnDefinition = "text")
    private String checkApiResponse;
    
    @Column(name = "report_api_response", columnDefinition = "text")
    private String reportApiResponse;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_aml_check_request_id", referencedColumnName = "id")
    private BeneficiaryAmlCheckRequest beneficiaryAmlCheckRequest;

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

	public String getApplicantAppId() {
		return applicantAppId;
	}

	public void setApplicantAppId(String applicantAppId) {
		this.applicantAppId = applicantAppId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getCheckApiResponse() {
		return checkApiResponse;
	}

	public void setCheckApiResponse(String checkApiResponse) {
		this.checkApiResponse = checkApiResponse;
	}

	public String getReportApiResponse() {
		return reportApiResponse;
	}

	public void setReportApiResponse(String reportApiResponse) {
		this.reportApiResponse = reportApiResponse;
	}

	public long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public BeneficiaryAmlCheckRequest getBeneficiaryAmlCheckRequest() {
		return beneficiaryAmlCheckRequest;
	}

	public void setBeneficiaryAmlCheckRequest(BeneficiaryAmlCheckRequest beneficiaryAmlCheckRequest) {
		this.beneficiaryAmlCheckRequest = beneficiaryAmlCheckRequest;
	}
}
