package com.adminremit.auth.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.adminremit.user.model.Users;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.operations.model.ReconStatus;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.personaldetails.model.PersonalDetails;

public class TransactionDTO {

private long id;
private WorkflowStatus workflowStatus;
private String referenceNo;
private UserCalculatorMapping userCalculatorMapping;
private TransferAccountDetails transferAccountDetails;
private boolean isCompleted = false;
private ReconStatus reconStatus;
private String actionStatus;
private BigDecimal fixBaseRate;
private BigDecimal margin;
private Date disbursementDateTime;
private String disbursementBank;
private String disbursementBankStatus;
private String utrNumber;
private String utrTime;
private Boolean isGuarantedRateApplicable;
private String purpose;
private String status;
private String vpaHandle;
private String relationShip;
private String utrDate;


UserCalculatorMapping userCal = new UserCalculatorMapping();
Users user = new Users();
BeneficiaryAccount account = new BeneficiaryAccount();
BeneficiaryUser beneficiaryUser = new BeneficiaryUser();
BigDecimal fxBaseRate;
PersonalDetails personalDetails=new PersonalDetails();
private String bookingDate;
private String bookingTime;
private String remitterFullname="";

public TransactionDTO(TransactionWorkflow tr) {
super();
this.id = tr.getId();
this.workflowStatus = tr.getWorkflowStatus();
this.referenceNo = tr.getReferenceNo();
this.userCalculatorMapping = tr.getUserCalculatorMapping();
this.isCompleted = tr.isCompleted();
this.reconStatus = tr.getReconStatus();
this.actionStatus = tr.getActionStatus();
this.fixBaseRate = tr.getFixBaseRate();
this.margin = tr.getMargin();
this.disbursementDateTime = tr.getDisbursementDateTime();
this.disbursementBankStatus = tr.getDisbursementBankStatus();
this.utrNumber = tr.getUtrNumber();
this.isGuarantedRateApplicable = tr.isGuarantedRateApplicable();
this.disbursementBank ="YESBANK";
if(tr.getReferenceNo().endsWith("NP")) {
	this.disbursementBank ="CITY Express";	
}

}

public String getBookingDate() {
return bookingDate;
}

public void setBookingDate(String bookingDate) {
this.bookingDate = bookingDate;
}

public String getBookingTime() {
return bookingTime;
}

public void setBookingTime(String bookingTime) {
this.bookingTime = bookingTime;
}

public String getRemitterFullname() {
return remitterFullname;
}

public void setRemitterFullname(String remitterFullname) {
this.remitterFullname = remitterFullname;
}

public BeneficiaryAccount getAccount() {
return account;
}

public void setAccount(BeneficiaryAccount account) {
this.account = account;
}

public BeneficiaryUser getBeneficiaryUser() {
return beneficiaryUser;
}

public void setBeneficiaryUser(BeneficiaryUser beneficiaryUser) {
this.beneficiaryUser = beneficiaryUser;
}

public TransferAccountDetails getTransferAccountDetails() {
return transferAccountDetails;
}

public void setTransferAccountDetails(TransferAccountDetails transferAccountDetails) {
this.transferAccountDetails = transferAccountDetails;
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

public String getUtrTime() {
return utrTime;
}

public void setUtrTime(String utrTime) {
this.utrTime = utrTime;
}

public Boolean getIsGuarantedRateApplicable() {
return isGuarantedRateApplicable;
}

public void setIsGuarantedRateApplicable(Boolean isGuarantedRateApplicable) {
this.isGuarantedRateApplicable = isGuarantedRateApplicable;
}

public long getId() {
return id;
}

public void setId(long id) {
this.id = id;
}

public BigDecimal getFxBaseRate() {
return fxBaseRate;
}

public void setFxBaseRate(BigDecimal fxBaseRate) {
this.fxBaseRate = fxBaseRate;
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

public UserCalculatorMapping getUserCalculatorMapping() {
return userCalculatorMapping;
}

public void setUserCalculatorMapping(UserCalculatorMapping userCalculatorMapping) {
this.userCalculatorMapping = userCalculatorMapping;
}

public boolean isCompleted() {
return isCompleted;
}

public void setCompleted(boolean isCompleted) {
this.isCompleted = isCompleted;
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

public UserCalculatorMapping getUserCal() {
return userCal;
}

public void setUserCal(UserCalculatorMapping userCal) {
this.userCal = userCal;
}

public Users getUser() {
return user;
}

public void setUser(Users user) {
this.user = user;
}

public PersonalDetails getPersonalDetails() {
return personalDetails;
}

public void setPersonalDetails(PersonalDetails personalDetails) {
this.personalDetails = personalDetails;
}

public String getDisbursementBank() {
	return disbursementBank;
}

public void setDisbursementBank(String disbursementBank) {
	this.disbursementBank = disbursementBank;
}

public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getVpaHandle() {
	return vpaHandle;
}

public void setVpaHandle(String vpaHandle) {
	this.vpaHandle = vpaHandle;
}

public String getRelationShip() {
	return relationShip;
}

public void setRelationShip(String relationShip) {
	this.relationShip = relationShip;
}

public String getUtrDate() {
	return utrDate;
}

public void setUtrDate(String utrDate) {
	this.utrDate = utrDate;
}

}