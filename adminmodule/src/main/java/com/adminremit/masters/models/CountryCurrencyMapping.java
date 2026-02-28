package com.adminremit.masters.models;
import javax.persistence.*;

@Entity
@Table(name = "admin_country_curreny_mapping")
public class CountryCurrencyMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Countries countries;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currencies currencies;

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

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currencies) {
        this.currencies = currencies;
    }
}
