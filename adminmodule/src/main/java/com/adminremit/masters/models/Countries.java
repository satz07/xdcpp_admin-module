package com.adminremit.masters.models;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.Audited;

import com.adminremit.common.models.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_countries")
@Audited
@Getter
@Setter
public class Countries extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "country_name")
    @NotEmpty(message = "Country name is mandatory")
    private String countryName;

    @Column(name = "country_code",length = 20)
    @NotEmpty(message = "Country code is mandatory")
    private String countryCode;

    @Column(name = "dialing_code")
    private Integer dialingCode;

    @OneToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<PaymentReceiveMode> paymentReceiveModes;

    @Column(name = "default_language")
    private String defaultLanguage;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_restricted",columnDefinition="BOOLEAN DEFAULT false")
    private boolean isRestricted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode.toUpperCase();
    }

    public Integer getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(Integer dialingCode) {
        this.dialingCode = dialingCode;
    }

    public List<PaymentReceiveMode> getPaymentReceiveModes() {
        return paymentReceiveModes;
    }

    public void setPaymentReceiveModes(List<PaymentReceiveMode> paymentReceiveModes) {
        this.paymentReceiveModes = paymentReceiveModes;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode.toUpperCase();
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public boolean getIsRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }
}
