package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.enums.UserCategory;
import com.adminremit.masters.enums.UserType;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "payment_recevie_mode_master")
@Audited
public class PaymentReceiveMode extends BaseEntity {

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
    @JoinColumn(name = "country_code")
    private Countries countries;

    @ManyToOne
    @JoinColumn(name = "currency_code")
    private Currencies currencies;

    @ManyToOne
    @JoinColumn(name = "payment_code")
    private PaymentCodeMaster paymentCodeMaster;
    
    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "comments",columnDefinition = "TEXT")
    private String comments;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "payment_mode_type")
    @Enumerated(EnumType.STRING)
    private PaymentModeType paymentModeType;

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

    public PaymentCodeMaster getPaymentCodeMaster() {
        return paymentCodeMaster;
    }

    public void setPaymentCodeMaster(PaymentCodeMaster paymentCodeMaster) {
        this.paymentCodeMaster = paymentCodeMaster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PaymentModeType getPaymentModeType() {
        return paymentModeType;
    }

    public void setPaymentModeType(PaymentModeType paymentModeType) {
        this.paymentModeType = paymentModeType;
    }
}
