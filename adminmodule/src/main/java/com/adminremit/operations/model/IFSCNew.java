package com.adminremit.operations.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "IFSC_New")
public class IFSCNew {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
     private Long id;

     @Column(name="ifsc_code")
     private String ifscCode;

     @Column(name="bank_name") 
     private String bankName;
     
     @Column(name="branch_name")
     private String branchName;

     @Column(name="city")
     private String city;
     
     @Column(name = "country_code")
     private String countryCode;
     
     //@Column(name="insta_available")
    // private String instaAvailable;
     
     @Column(name = "insta_available", columnDefinition = "boolean default false")
     private Boolean instaAvailable = false;
     
     @Column(name="insta_code")
     private String instaCode;
     
     public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Boolean getInstaAvailable() {
		return instaAvailable;
	}

	public void setInstaAvailable(Boolean instaAvailable) {
		this.instaAvailable = instaAvailable;
	}

	public String getInstaCode() {
		return instaCode;
	}

	public void setInstaCode(String instaCode) {
		this.instaCode = instaCode;
	}

	@Column(name="status")
     private String status="active";

     @Column(name="createdon")
     @CreationTimestamp
     private Date createdon=new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
	
}
