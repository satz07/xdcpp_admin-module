package com.adminremit.beneficiary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.enums.VerificationStatus;
import com.adminremit.masters.models.PaymentReceiveMode;

@Entity
@Table(name = "beneficiary_account_details")
public class BeneficiaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @OneToOne
    @JoinColumn(name = "beneficiary_user")
    private BeneficiaryUser beneficiaryUser;

    @Column(name = "account_title")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "verification_status",columnDefinition = "varchar(32) default 'VERIFICATION_PENDING'")
    @Enumerated(value = EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.VERIFICATION_PENDING;
    
    @ManyToOne
    @JoinColumn(name = "receive_code")
    private PaymentReceiveMode receiveMode;

    @Column(name = "insta_available", columnDefinition = "boolean default false")
    private Boolean instaAvailable = false;
    
    @Column(name="insta_code")
    private String instaCode;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public BeneficiaryUser getBeneficiaryUser() {
        return beneficiaryUser;
    }

    public void setBeneficiaryUser(BeneficiaryUser beneficiaryUser) {
        this.beneficiaryUser = beneficiaryUser;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

	public PaymentReceiveMode getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(PaymentReceiveMode receiveMode) {
		this.receiveMode = receiveMode;
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
}
