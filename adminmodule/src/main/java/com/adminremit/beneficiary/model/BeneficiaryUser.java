package com.adminremit.beneficiary.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.adminremit.beneficiary.enums.BeneficiaryStatus;
import com.adminremit.beneficiary.enums.Gender;
import com.adminremit.masters.models.Currencies;


@Entity
@Table(name = "beneficiary_user")
public class BeneficiaryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "relationship")
    private Long beneficiaryRelationship;

    @Column(name = "publish")
    private boolean publish;

    @Column(name = "unit")
    private String unit;

    @Column(name = "address")
    private String address;
    
    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;


    @Column(name = "pincode")
    private String pincode;

    @Column(name = "country")
    private Long country;

    @OneToMany(fetch = FetchType.LAZY,cascade =  CascadeType.ALL, mappedBy = "beneficiaryUser")
    private List<BeneficiaryAccount> beneficiaryAccounts;
    
    @ManyToOne
    @JoinColumn(name = "receive_currency_code")
    private Currencies receiveCurrencyCode;

    @Column(name = "user_id")
    private Long user;

    @Column(name = "receiver_type")
    private Long receiverType;
    
    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt;
    
    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;

    @Column(name= "purpose")
    private String purpose;

    @Column(name = "account_status",columnDefinition = "varchar(32) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private BeneficiaryStatus beneficiaryStatus=BeneficiaryStatus.ACTIVE;
    
    @Column(name = "dob")
    private Date dob;

    /*
    @OneToOne(mappedBy = "beneficiaryUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    ReceiverTransactionModel receiverTransactionModel;
   */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public List<BeneficiaryAccount> getBeneficiaryAccounts() {
		return beneficiaryAccounts;
	}

	public void setBeneficiaryAccounts(List<BeneficiaryAccount> beneficiaryAccounts) {
		this.beneficiaryAccounts = beneficiaryAccounts;
	}

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


    public Long getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    public void setBeneficiaryRelationship(Long beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Long receiverType) {
        this.receiverType = receiverType;
    }

    public BeneficiaryStatus getBeneficiaryStatus() {
        return beneficiaryStatus;
    }

    public void setBeneficiaryStatus(BeneficiaryStatus beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

	public Currencies getReceiveCurrencyCode() {
		return receiveCurrencyCode;
	}

	public void setReceiveCurrencyCode(Currencies receiveCurrencyCode) {
		this.receiveCurrencyCode = receiveCurrencyCode;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getName() {
        return getFullName()+(getMiddleName()!=null?" "+getMiddleName():"")+(getLastName()!=null?" "+getLastName():"");
    }
}
