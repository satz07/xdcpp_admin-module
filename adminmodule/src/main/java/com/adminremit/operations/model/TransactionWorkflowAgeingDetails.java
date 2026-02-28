package com.adminremit.operations.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction_worflow_ageing")
public class TransactionWorkflowAgeingDetails {
	
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    
    @Column(name = "booking_date")
    private Date bookingDate;
    
    @Column(name = "last_update_at")
    private Date lastUpdatedAt;
    
    @Column(name="workflow_state")
    @Enumerated(EnumType.STRING)
    private WorkflowStatus workflowStatus;
    
    @Column(name="reference_id")
    private String  referenceNo;
    
    @Column(name="job_type")
    @Enumerated(EnumType.STRING)
    private JobType  jobType;
    
    @Column(name="minutes_spent")
    private long  minutesSpent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public WorkflowStatus getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(WorkflowStatus workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}	

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public long getMinutesSpent() {
		return minutesSpent;
	}

	public void setMinutesSpent(long minutesSpent) {
		this.minutesSpent = minutesSpent;
	}
}
