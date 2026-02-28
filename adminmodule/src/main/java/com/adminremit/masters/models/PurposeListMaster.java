package com.adminremit.masters.models;

import com.adminremit.auth.models.Partner;
import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.PurposeType;
import com.adminremit.masters.enums.UserType;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "purpose_list_master")
@Audited
public class PurposeListMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "send_country_code")
    private Countries countryCode;

    @ManyToOne
    @JoinColumn(name = "receive_country_code")
    private Currencies currencyCode;

    @ManyToOne
    @JoinColumn(name = "receive_code")
    private PaymentReceiveMode receiveCode;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "purpose_type")
    @Enumerated(EnumType.STRING)
    private PurposeType purposeType;


    @ManyToOne
    @JoinColumn(name = "receiver_type_id")
    private ReceiverType receiverTypes;

    
    @ManyToOne
    @JoinColumn(name = "partner_name")
    private Partner partnerName;


    public Partner getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(Partner partnerName) {
		this.partnerName = partnerName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Countries getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Countries countryCode) {
        this.countryCode = countryCode;
    }

    public Currencies getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Currencies currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public PaymentReceiveMode getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(PaymentReceiveMode receiveCode) {
        this.receiveCode = receiveCode;
    }

    public PurposeType getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(PurposeType purposeType) {
        this.purposeType = purposeType;
    }

    public ReceiverType getReceiverTypes() {
        return receiverTypes;
    }

    public void setReceiverTypes(ReceiverType receiverTypes) {
        this.receiverTypes = receiverTypes;
    }
}
