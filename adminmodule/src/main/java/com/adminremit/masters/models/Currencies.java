package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.CurrencyType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "admin_currencies")
@Audited
public class Currencies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_code")
    @NotEmpty(message = "Currency code is mandatory")
    private String currencyCode;

    @Column(name = "country_code",length = 10)
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "currency_description")
    private String currencyDescription;

    @Column(name = "nationality")
    private String nationality;

    @OneToMany(mappedBy = "currencies", fetch = FetchType.LAZY)
    private List<PaymentReceiveMode> paymentReceiveModes;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type")
    private CurrencyType currencyType;

    @Column(name = "currency_symbol")
    private String currencySymbol;
    
    @Column(name = "country_description")
    private String countryDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<PaymentReceiveMode> getPaymentReceiveModes() {
        return paymentReceiveModes;
    }

    public void setPaymentReceiveModes(List<PaymentReceiveMode> paymentReceiveModes) {
        this.paymentReceiveModes = paymentReceiveModes;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

	public String getCountryDescription() {
		return countryDescription;
	}

	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	
    
}
