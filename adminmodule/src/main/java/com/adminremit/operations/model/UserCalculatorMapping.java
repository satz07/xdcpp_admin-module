package com.adminremit.operations.model;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user_calc_mapping")
public class UserCalculatorMapping extends AuditDetails{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name="uid")
    String uid;

    @Column(name="reference_id")
    String refId;

    @OneToOne(targetEntity = RemittUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "user_id")
    private RemittUser user;

    @Column(name="user_name")
    String userName;

    @Column(name="from_country_code")
    String fromCountryCode;

    @Column(name="from_country_id")
    Long fromCountryId;

    @Column(name="from_currency_code")
    String fromCurrencyCode;

    @Column(name="from_currency_value")
    BigDecimal fromCurrencyValue;

    @Column(name="to_country_code")
    String toCountryCode;

    @Column(name="to_country_id")
    Long toCountryId;

    @Column(name="to_currency_code")
    String toCurrencyCode;

    @Column(name="to_currency_value", scale = 4)
    BigDecimal toCurrencyValue;

    @Column(name="customer_rate")
    BigDecimal customerRate;

    @Column(name="transfer_amount")
    BigDecimal transferAmount;

    @Column(name="transaction_fee")
    BigDecimal transactionFee;

    @Column(name="converted_amt")
    BigDecimal totalConvertedValue;

    @Column(name="transfer_payment_code")
    Long transferPaymentCode;

    @Column(name="beneficiary_id")
    Long beneficiaryId;
    
    @Column(name="beneficiary_account_id")
   	Long beneficiaryAccountId;

    @Column(name="beneficiary_name")
    String beneficiaryName;

    @Column(name="beneficiary_email")
    String beneficiaryEmail;


    @Column(name="payment_id")
    Long paymentCodeid;

    @Column(name="status")
    String status;

    @Column(name="status_reason")
    String statusReason;

    @Column(name="payment_mode")
    String paymentMode;

    @Column(name="paymentmode_id")
    Long paymentModeId;

    @Column(name="receivemode_id")
    Long receiveModeId;

    @Column(name="receive_mode")
    String receiveMode;

    @Column(name="tat")
    int tat;
    
    @Column(name="fx_base_rate")
    private String fxBaseRate;
    
    @Column(name="margin_spread")
    private String marginSpread;
    
    @Column(name = "arrrivalDate", nullable = true)
    private Date arrivalDate;
    
    @Column(name = "pinno", nullable = true)
    private String pinno;
    
    public String getEmail() {
		return this.user.getEmail();
	}

    public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Column(name = "gurrantedRateDate", nullable = true)
    private Date gurrantedRateDate;

    public Date getGurrantedRateDate() {
		return gurrantedRateDate;
	}

	public void setGurrantedRateDate(Date gurrantedRateDate) {
		this.gurrantedRateDate = gurrantedRateDate;
	}

    @Column(name="user_account_type")
    @Enumerated(EnumType.STRING)
    private UserType userAccountType;

    @OneToMany(mappedBy = "userCalculatorMapping", fetch = FetchType.LAZY)
    private List<TransactionWorkflow> transactionWorflow;
    
    @Column(name="beneficiary_acct_num")
    private String beneficiaryAccountNumber;
    
    @Transient
    private String currentActionStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFromCountryCode() {
        return fromCountryCode;
    }

    public void setFromCountryCode(String fromCountryCode) {
        this.fromCountryCode = fromCountryCode;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public BigDecimal getFromCurrencyValue() {
        return fromCurrencyValue;
    }

    public void setFromCurrencyValue(BigDecimal fromCurrencyValue) {
        this.fromCurrencyValue = fromCurrencyValue;
    }

    public String getToCountryCode() {
        return toCountryCode;
    }

    public void setToCountryCode(String toCountryCode) {
        this.toCountryCode = toCountryCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public BigDecimal getToCurrencyValue() {
        return toCurrencyValue;
    }

    public void setToCurrencyValue(BigDecimal toCurrencyValue) {
        this.toCurrencyValue = toCurrencyValue;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public BigDecimal getTotalConvertedValue() {
        return totalConvertedValue;
    }

    public void setTotalConvertedValue(BigDecimal totalConvertedValue) {
        this.totalConvertedValue = totalConvertedValue;
    }

    public Long getTransferPaymentCode() {
        return transferPaymentCode;
    }

    public void setTransferPaymentCode(Long transferPaymentCode) {
        this.transferPaymentCode = transferPaymentCode;
    }


    public RemittUser getUser() {
        return user;
    }

    public void setUser(RemittUser user) {
        this.user = user;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public BigDecimal getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(BigDecimal customerRate) {
        this.customerRate = customerRate;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public Long getPaymentCodeid() {
        return paymentCodeid;
    }

    public void setPaymentCodeid(Long paymentCodeid) {
        this.paymentCodeid = paymentCodeid;
    }

    public Long getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(Long paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    public Long getReceiveModeId() {
        return receiveModeId;
    }

    public void setReceiveModeId(Long receiveModeId) {
        this.receiveModeId = receiveModeId;
    }

    public String getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(String receiveMode) {
        this.receiveMode = receiveMode;
    }

    public int getTat() {
        return tat;
    }

    public void setTat(int tat) {
        this.tat = tat;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Long getFromCountryId() {
        return fromCountryId;
    }

    public void setFromCountryId(Long fromCountryId) {
        this.fromCountryId = fromCountryId;
    }

    public Long getToCountryId() {
        return toCountryId;
    }

    public void setToCountryId(Long toCountryId) {
        this.toCountryId = toCountryId;
    }

    public UserType getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(UserType userAccountType) {
        this.userAccountType = userAccountType;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getBeneficiaryEmail() {
        return beneficiaryEmail;
    }

    public void setBeneficiaryEmail(String beneficiaryEmail) {
        this.beneficiaryEmail = beneficiaryEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TransactionWorkflow> getTransactionWorflow() {
        return transactionWorflow;
    }

    public void setTransactionWorflow(List<TransactionWorkflow> transactionWorflow) {
        this.transactionWorflow = transactionWorflow;
    }

	public String getCurrentActionStatus() {
		return currentActionStatus;
	}

	public void setCurrentActionStatus(String currentActionStatus) {
		this.currentActionStatus = currentActionStatus;
	}

	public String getFxBaseRate() {
		return fxBaseRate;
	}

	public void setFxBaseRate(String fxBaseRate) {
		this.fxBaseRate = fxBaseRate;
	}

	public String getMarginSpread() {
		return marginSpread;
	}

	public void setMarginSpread(String marginSpread) {
		this.marginSpread = marginSpread;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public Long getBeneficiaryAccountId() {
		return beneficiaryAccountId;
	}

	public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
		this.beneficiaryAccountId = beneficiaryAccountId;
	}

	public String getPinno() {
		return pinno;
	}

	public void setPinno(String pinno) {
		this.pinno = pinno;
	}
}
