package com.adminremit.report.vo;

import java.util.Date;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.enums.BeneficiaryStatus;
import com.adminremit.beneficiary.enums.VerificationStatus;

public class ReceipientVO {
	
	private String email;
	private String receipientName;
	private String nickName;
	private String relationship;
	private Date dob;
	private String ifscCode;
	private String bankName;
	private String accountNumber;
	private String acountTitle;
	private String vpaHandle;
	private Date dateAdded;
	private Date dateUpdated;
	private String accountStatus;
	private String verificationStatus;	
	
	public ReceipientVO(String email, String receipientName, String nickName, String relationship, Date dob,
			String ifscCode, String bankName, String accountNumber, AccountType accountType, String vpaHandle, Date dateAdded,
			Date dateUpdated, BeneficiaryStatus beneficiaryStatus, VerificationStatus verificationStatus) {
		super();
		this.email = email;
		this.receipientName = receipientName;
		this.nickName = nickName;
		this.relationship = relationship;
		this.dob = dob;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.acountTitle = accountType.getCode();
		this.vpaHandle = vpaHandle;
		this.dateAdded = dateAdded;
		this.dateUpdated = dateUpdated;
		this.accountStatus = beneficiaryStatus.name();
		this.verificationStatus = verificationStatus.name();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReceipientName() {
		return receipientName;
	}
	public void setReceipientName(String receipientName) {
		this.receipientName = receipientName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAcountTitle() {
		return acountTitle;
	}
	public void setAcountTitle(String acountTitle) {
		this.acountTitle = acountTitle;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getVpaHandle() {
		return vpaHandle;
	}
	public void setVpaHandle(String vpaHandle) {
		this.vpaHandle = vpaHandle;
	}
}
