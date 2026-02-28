package com.adminremit.report.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.adminremit.report.enums.business.CompanyCategory;
import com.adminremit.report.enums.business.CompanySubCategory;
import com.adminremit.report.enums.business.CompanyType;

@Entity
@Table(name = "business_info")
public class BusinessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "abn")
    private String abn;
    
    @Column(name = "acn")
    private String acn;
    
    @Column(name = "abn_status")
    private String abnStatus;
    
    @Column(name = "registered_unit_number")
    private String registeredUnitNumber;
    
    @Column(name = "registered_street_number")
    private String registeredStreetNumber;
    
    @Column(name = "registered_street_name")
    private String registeredStreetName;
    
    @Column(name = "registered_suburb")
    private String registeredSuburb;
    
    @Column(name = "registered_post_code")
    private String registeredPostcode;
    
    @Column(name = "registered_state")
    private String registeredState;
    
    @Column(name = "registered_country")
    private String registeredCountry;
    
    @Column(name = "registered_business_address_same")
    private boolean registeredBusinessAddressSame;
    
    @Column(name = "business_unit_number")
    private String businessUnitNumber;
    
    @Column(name = "business_street_number")
    private String businessStreetNumber;
    
    @Column(name = "business_street_name")
    private String businessStreetName;
    
    @Column(name = "business_suburb")
    private String businessSuburb;
    
    @Column(name = "business_post_code")
    private String businessPostcode;
    
    @Column(name = "business_state")
    private String businessState;
    
    @Column(name = "business_country1")
    private String businessCountry1;
    
    @Column(name = "business_country2")
    private String businessCountry2;
    
    @Column(name = "business_structure")
    private String businessStructure;
    
    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "company_category")
    @Enumerated(EnumType.STRING)
    private CompanyCategory companyCategory;

    @Column(name = "company_sub_category")
    @Enumerated(EnumType.STRING)
    private CompanySubCategory companySubCategory;
    
    @Column(name = "business_activity")
    private String businessActivity;
    
    @Column(name = "business_subactivity")
    private String businessSubActivity;

    @Column(name = "company_website")
    private String companyWebSite;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address")
    private String address;

    @Column(name = "company_turnover")
    private String companyTurnover;
    
    @Column(name = "company_employees")
    private String companyEmployees;
    
    @Column(name = "social_links")
    private String socialMediaLinks;
    
    @Column(name = "online_presence")
    private String onlinePresence;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }
    
    public String getAbnStatus() {
        return abnStatus;
    }

    public void setAbnStatus(String abnStatus) {
        this.abnStatus = abnStatus;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public CompanyCategory getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(CompanyCategory companyCategory) {
        this.companyCategory = companyCategory;
    }

    public CompanySubCategory getCompanySubCategory() {
        return companySubCategory;
    }

    public void setCompanySubCategory(CompanySubCategory companySubCategory) {
        this.companySubCategory = companySubCategory;
    }

    public String getCompanyWebSite() {
        return companyWebSite;
    }

    public void setCompanyWebSite(String companyWebSite) {
        this.companyWebSite = companyWebSite;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAcn() {
        return acn;
    }

    public void setAcn(String acn) {
        this.acn = acn;
    }
    
    public String getRegisteredUnitNumber() {
        return registeredUnitNumber;
    }

    public void setRegisteredUnitNumber(String registeredUnitNumber) {
        this.registeredUnitNumber = registeredUnitNumber;
    }
    
    public String getRegisteredStreetNumber() {
        return registeredStreetNumber;
    }

    public void setRegisteredStreetNumber(String registeredStreetNumber) {
        this.registeredStreetNumber = registeredStreetNumber;
    }
    
    public String getRegisteredStreetName() {
        return registeredStreetName;
    }

    public void setRegisteredStreetName(String registeredStreetName) {
        this.registeredStreetName = registeredStreetName;
    }
    
    public String getRegisteredSuburb() {
        return registeredSuburb;
    }

    public void setRegisteredSuburb(String registeredSuburb) {
        this.registeredSuburb = registeredSuburb;
    }
    
    public String getRegisteredState() {
        return registeredState;
    }

    public void setRegisteredState(String registeredState) {
        this.registeredState = registeredState;
    }
    
    public String getRegisteredPostCode() {
        return registeredPostcode;
    }

    public void setRegisteredPostcode(String registeredPostcode) {
        this.registeredPostcode = registeredPostcode;
    }

    public String getRegisteredCountry() {
        return registeredCountry;
    }

    public void setRegisteredCountry(String registeredCountry) {
        this.registeredCountry = registeredCountry;
    }
    
    public boolean getRegisteredBusinessAddressSame() {
    	return registeredBusinessAddressSame;
    }
    
    public void setRegisteredBusinessAddressSame(boolean registeredBusinessAddressSame) {
        this.registeredBusinessAddressSame = registeredBusinessAddressSame;
    }
    
    
    public String getBusinessUnitNumber() {
        return businessUnitNumber;
    }

    public void setBusinessUnitNumber(String businessUnitNumber) {
        this.businessUnitNumber = businessUnitNumber;
    }
    
    public String getBusinessStreetNumber() {
        return businessStreetNumber;
    }

    public void setBusinessStreetNumber(String businessStreetNumber) {
        this.businessStreetNumber = businessStreetNumber;
    }
    
    public String getBusinessStreetName() {
        return businessStreetName;
    }

    public void setBusinessStreetName(String businessStreetName) {
        this.businessStreetName = businessStreetName;
    }
    
    public String getBusinessSuburb() {
        return businessSuburb;
    }

    public void setBusinessSuburb(String businessSuburb) {
        this.businessSuburb = businessSuburb;
    }
    
    public String getBusinessState() {
        return businessState;
    }

    public void setBusinessState(String businessState) {
        this.businessState = businessState;
    }
    
    public String getBusinessPostCode() {
        return businessPostcode;
    }

    public void setBusinessPostcode(String businessPostcode) {
        this.businessPostcode = businessPostcode;
    }

    public String getBusinessCountry1() {
        return businessCountry1;
    }

    public void setBusinessCountry1(String businessCountry1) {
        this.businessCountry1 = businessCountry1;
    }
    
    public String getBusinessCountry2() {
        return businessCountry2;
    }

    public void setBusinessCountry2(String businessCountry2) {
        this.businessCountry2 = businessCountry2;
    }
    
    public String getBusinessStructure() {
        return businessStructure;
    }

    public void setBusinessStructure(String businessStructure) {
        this.businessStructure = businessStructure;
    }
    
    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }
    
    public String getBusinessSubActivity() {
        return businessSubActivity;
    }

    public void setBusinessSubActivity(String businessSubActivity) {
        this.businessSubActivity = businessSubActivity;
    }
    
    public String getCompanyTurnover() {
        return companyTurnover;
    }

    public void setCompanyTurnover(String companyTurnover) {
        this.companyTurnover = companyTurnover;
    }
    
    public String getCompanyEmployees() {
        return companyEmployees;
    }

    public void setCompanyEmployees(String companyEmployees) {
        this.companyEmployees = companyEmployees;
    }
    
    public String getSocialMediaLinks() {
        return socialMediaLinks;
    }

    public void setSocialMediaLinks(String socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }
    
    public String getOnlinePresence() {
        return onlinePresence;
    }

    public void setOnlinePresence(String onlinePresence) {
        this.onlinePresence = onlinePresence;
    }
}
