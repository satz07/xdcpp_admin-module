package com.adminremit.onfido.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="onfido_check_api_response")
public class OnfidoCheckApiResponse {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
	
	@Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt = new Date();
    
    @Column(name = "app_id")
    private String appId;
    
    @Column(name = "uid")
    private String uid;    
    
    @Column(name = "user_id")
    private String  userId;
    
    @Column(name = "check_id")
    private String  checkId;
    
    @Column(name = "check_result")
    private String  checkResult;
    
    @Column(name = "check_status")
    private String  checkStatus;
    
    @Column(name = "report_id")
    private String  reportId;
    
    @Column(name = "report_name")
    private String  reportName;
    
    @Column(name = "report_result")
    private String  reportResult;
    
    @Column(name = "report_status")
    private String  reportStatus;
    
    @Column(name = "check_api_response", columnDefinition = "text")
    private String checkApiResponse;
    
    @Column(name = "report_api_response", columnDefinition = "text")
    private String reportApiResponse;
    
    @Column(name = "onfido_check_api_request_id")
    private Long  onfidoCheckApiRequestId;    

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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
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

	public String getCheckApiResponse() {
		return checkApiResponse;
	}

	public void setCheckApiResponse(String checkApiResponse) {
		this.checkApiResponse = checkApiResponse;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

	public String getReportApiResponse() {
		return reportApiResponse;
	}

	public void setReportApiResponse(String reportApiResponse) {
		this.reportApiResponse = reportApiResponse;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getOnfidoCheckApiRequestId() {
		return onfidoCheckApiRequestId;
	}

	public void setOnfidoCheckApiRequestId(Long onfidoCheckApiRequestId) {
		this.onfidoCheckApiRequestId = onfidoCheckApiRequestId;
	}
}
