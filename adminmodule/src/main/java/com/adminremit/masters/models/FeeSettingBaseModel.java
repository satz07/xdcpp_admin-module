package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;

import javax.persistence.*;

@MappedSuperclass
public class FeeSettingBaseModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "user_category")
    @Enumerated(EnumType.STRING)
    private UserCategory userCategory;

    @ManyToOne
    @JoinColumn(name = "send_country_code")
    private Countries sendCountryCode;

    @ManyToOne
    @JoinColumn(name = "receive_country_code")
    private Countries receiveCountryCode;

    @ManyToOne
    @JoinColumn(name = "send_currency_code")
    private Currencies sendCurrencyCode;

    @ManyToOne
    @JoinColumn(name = "receive_currency_code")
    private Currencies receiveCurrencyCode;

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

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public Countries getSendCountryCode() {
        return sendCountryCode;
    }

    public void setSendCountryCode(Countries sendCountryCode) {
        this.sendCountryCode = sendCountryCode;
    }

    public Countries getReceiveCountryCode() {
        return receiveCountryCode;
    }

    public void setReceiveCountryCode(Countries receiveCountryCode) {
        this.receiveCountryCode = receiveCountryCode;
    }

    public Currencies getSendCurrencyCode() {
        return sendCurrencyCode;
    }

    public void setSendCurrencyCode(Currencies sendCurrencyCode) {
        this.sendCurrencyCode = sendCurrencyCode;
    }

    public Currencies getReceiveCurrencyCode() {
        return receiveCurrencyCode;
    }

    public void setReceiveCurrencyCode(Currencies receiveCurrencyCode) {
        this.receiveCurrencyCode = receiveCurrencyCode;
    }

}
