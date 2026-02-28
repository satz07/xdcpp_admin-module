package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "street_type")
public class StreetType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Countries countries;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "street_type_name")
    private String streetTypeName;

    @Column(name = "street_type_key")
    private String streetTypeKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Countries getCountries() {
        return countries;
    }

    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreetTypeName() {
        return streetTypeName;
    }

    public void setStreetTypeName(String streetTypeName) {
        this.streetTypeName = streetTypeName;
    }

    public String getStreetTypeKey() {
        return streetTypeKey;
    }

    public void setStreetTypeKey(String streetTypeKey) {
        this.streetTypeKey = streetTypeKey;
    }
}
