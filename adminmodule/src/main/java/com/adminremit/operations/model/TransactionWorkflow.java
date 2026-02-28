package com.adminremit.operations.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="transaction_worflow")
public class TransactionWorkflow extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name="workflow_state")
    @Enumerated(EnumType.STRING)
    private WorkflowStatus workflowStatus;

    @Column(name="reference_id")
    private String  referenceNo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_refno", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserCalculatorMapping userCalculatorMapping;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_ref", nullable = true, insertable=false, updatable=false)
    private TransferAccountDetails transferAccountDetails;

    @Column(name="is_completed")
    private boolean isCompleted = false;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ReconStatus reconStatus;
    
    @Column(name="action_status")
    private String actionStatus;
    @Column(name="fx_base_rate")
    private BigDecimal fixBaseRate;
    
    @Column(name="margin")
    private BigDecimal margin;
    
    @Column(name = "disbursement_date_time")
    @CreationTimestamp
    private Date disbursementDateTime;
    
    @Column(name="disbursement_bank_status")
    private String  disbursementBankStatus;
    
    @Column(name="utr_number")
    private String  utrNumber;
    
    @Column(name = "utr_time", nullable = true)
    private Date utrTime;
    
    @Column(name="is_guaranted_rate_applicable")
    private Boolean isGuarantedRateApplicable;
    
    @Column(name = "sub_status_code", nullable = true)
    private String subStatusCode;

	public String getSubStatusCode() {
		return subStatusCode;
	}

	public void setSubStatusCode(String subStatusCode) {
		this.subStatusCode = subStatusCode;
	}

	public String getSubStatusText() {
		return subStatusText;
	}

	public void setSubStatusText(String subStatusText) {
		this.subStatusText = subStatusText;
	}

	@Column(name = "sub_status_text", nullable = true)
    private String subStatusText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatus workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public UserCalculatorMapping getUserCalculatorMapping() {
        return userCalculatorMapping;
    }

    public void setUserCalculatorMapping(UserCalculatorMapping userCalculatorMapping) {
        this.userCalculatorMapping = userCalculatorMapping;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public ReconStatus getReconStatus() {
        return reconStatus;
    }

    public void setReconStatus(ReconStatus reconStatus) {
        this.reconStatus = reconStatus;
    }

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public BigDecimal getFixBaseRate() {
		return fixBaseRate;
	}

	public void setFixBaseRate(BigDecimal fixBaseRate) {
		this.fixBaseRate = fixBaseRate;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public Date getDisbursementDateTime() {
		return disbursementDateTime;
	}

	public void setDisbursementDateTime(Date disbursementDateTime) {
		this.disbursementDateTime = disbursementDateTime;
	}

	public String getDisbursementBankStatus() {
		return disbursementBankStatus;
	}

	public void setDisbursementBankStatus(String disbursementBankStatus) {
		this.disbursementBankStatus = disbursementBankStatus;
	}

	public String getUtrNumber() {
		return utrNumber;
	}

	public void setUtrNumber(String utrNumber) {
		this.utrNumber = utrNumber;
	}

	public Date getUtrTime() {
		return utrTime;
	}

	public void setUtrTime(Date utrTime) {
		this.utrTime = utrTime;
	}

	public Boolean isGuarantedRateApplicable() {
		return isGuarantedRateApplicable;
	}

	public void setGuarantedRateApplicable(Boolean isGuarantedRateApplicable) {
		this.isGuarantedRateApplicable = isGuarantedRateApplicable;
	}

	public TransferAccountDetails getTransferAccountDetails() {
		return transferAccountDetails;
	}

	public void setTransferAccountDetails(TransferAccountDetails transferAccountDetails) {
		this.transferAccountDetails = transferAccountDetails;
	}

	public Boolean getIsGuarantedRateApplicable() {
		return isGuarantedRateApplicable;
	}

	public void setIsGuarantedRateApplicable(Boolean isGuarantedRateApplicable) {
		this.isGuarantedRateApplicable = isGuarantedRateApplicable;
	}	
	
	
}
