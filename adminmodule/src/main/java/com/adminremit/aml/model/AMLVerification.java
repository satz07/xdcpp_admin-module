package com.adminremit.aml.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aml_details")
public class AMLVerification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name = "session_id")
    String sessionid;

    @Column(name = "user_id")
    String  userId;
    
    @Column(name = "beneficiary_id")
    String  beneficiaryId;

    @Column(name = "app_id")
    String applicationId;

    @Column(name = "key")
    String key;

    @Column(name = "value")
    String value;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
}
