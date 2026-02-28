package com.adminremit.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.adminremit.user.enums.UserLoginAuditStatus;

@Entity
@Table(name="user_login_details")
public class UserLoginAudit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(name = "email")
    String email;
    @Column(name = "source_ip")
    String sourceIp;
    @Column(name = "reg_ip")
    String registerIp;
    @Column(name = "session_ip")
    String sessionIp;
    @Column(name = "auth_type")
    String loginType;

    @Column(name="timestamp")
    @CreationTimestamp
    private Date auditTimeStamp = new Date();

    @Column(name="exception")
    String exception;
    
    @Column(name="audit_type")
    @Enumerated(EnumType.STRING)
    private UserLoginAuditStatus auditType;
    
    @Column(name="uuid")
    private String uuid;
    
    @Column(name="refId")
    private String refId;
    
    @Column(name="beneficiary_id")
    private long beneficiaryId;
    
    @Column(name = "bene_id_add_ip")
    String beneficiaryIdAddIp;
    
    @Column(name = "bene_id_edit_ip")
    String beneficiaryIdEditIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getSessionIp() {
        return sessionIp;
    }

    public void setSessionIp(String sessionIp) {
        this.sessionIp = sessionIp;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }    

    public Date getAuditTimeStamp() {
		return auditTimeStamp;
	}

	public void setAuditTimeStamp(Date auditTimeStamp) {
		this.auditTimeStamp = auditTimeStamp;
	}

	public UserLoginAuditStatus getAuditType() {
		return auditType;
	}

	public void setAuditType(UserLoginAuditStatus auditType) {
		this.auditType = auditType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryIdAddIp() {
		return beneficiaryIdAddIp;
	}

	public void setBeneficiaryIdAddIp(String beneficiaryIdAddIp) {
		this.beneficiaryIdAddIp = beneficiaryIdAddIp;
	}

	public String getBeneficiaryIdEditIp() {
		return beneficiaryIdEditIp;
	}

	public void setBeneficiaryIdEditIp(String beneficiaryIdEditIp) {
		this.beneficiaryIdEditIp = beneficiaryIdEditIp;
	}		
}

