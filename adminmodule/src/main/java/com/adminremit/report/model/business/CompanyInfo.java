package com.adminremit.report.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "company_type")
    private String companyType;
    
    @Column(name = "afsl")
    private String afsl;
    
    @Column(name = "regulator_name")
    private String regulatorName;
    
    @Column(name = "license_number")
    private String licenseNumber;
    
    @Column(name = "business_id")
    private Long businessId;
    
    @Column(name = "listed_aus_exchange")
    private String listedOnStockExchange;
    
    @Column(name = "stock_symbol")
    private String stockSymbol;
    
    @Column(name = "listed_company_name")
    private String listedCompanyName;
    
    @Column(name = "listed_stock_symbol")
    private String listedStockSymbol;
    
    @Column(name = "subsidiary_public_company")
    private String subsidiaryAusPublicCompany;
    
    @Column(name = "afsl_licensed")
    private String afslLicensed;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
    
    public String getAFSL() {
        return afsl;
    }

    public void setAFSL(String afsl) {
        this.afsl = afsl;
    }
    
    public String getRegulatorName() {
        return regulatorName;
    }

    public void setRegulatorName(String regulatorName) {
        this.regulatorName = regulatorName;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public long getBusinessId() {
    	return businessId;
    }
    
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }
    
    public String getListedOnStockExchange() {
        return listedOnStockExchange;
    }

    public void setListedOnStockExchange(String listedOnStockExchange) {
        this.listedOnStockExchange = listedOnStockExchange;
    }
    
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
    public String getListedCompanyName() {
        return listedCompanyName;
    }

    public void setListedCompanyName(String listedCompanyName) {
        this.listedCompanyName = listedCompanyName;
    }
    
    public String getListedStockSymbol() {
        return listedStockSymbol;
    }

    public void setListedStockSymbol(String listedStockSymbol) {
        this.listedStockSymbol = listedStockSymbol;
    }
    
    public String getAfslLicensed() {
        return afslLicensed;
    }

    public void setAfslLicensed(String afslLicensed) {
        this.afslLicensed = afslLicensed;
    }
    
    public String getSubsidiaryAusPublicCompany() {
        return subsidiaryAusPublicCompany;
    }

    public void setSubsidiaryAusPublicCompany(String subsidiaryAusPublicCompany) {
        this.subsidiaryAusPublicCompany = subsidiaryAusPublicCompany;
    }
}
