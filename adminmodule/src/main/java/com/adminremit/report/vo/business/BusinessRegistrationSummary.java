package com.adminremit.report.vo.business;

public class BusinessRegistrationSummary {
	
	private BusinessDetails businessDetails;
	private CompanyType companyType;
	private BusinessActivity businessActivity;
	private Director director;	
	private BeneficialOwnersBaseDetails beneficialOwnersBaseDetails;
	
	public BusinessDetails getBusinessDetails() {
		return businessDetails;
	}
	public void setBusinessDetails(BusinessDetails businessDetails) {
		this.businessDetails = businessDetails;
	}
	public CompanyType getCompanyType() {
		return companyType;
	}
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}
	public BusinessActivity getBusinessActivity() {
		return businessActivity;
	}
	public void setBusinessActivity(BusinessActivity businessActivity) {
		this.businessActivity = businessActivity;
	}	
	public Director getDirector() {
		return director;
	}
	public void setDirector(Director director) {
		this.director = director;
	}
	public BeneficialOwnersBaseDetails getBeneficialOwnersBaseDetails() {
		return beneficialOwnersBaseDetails;
	}
	public void setBeneficialOwnersBaseDetails(BeneficialOwnersBaseDetails beneficialOwnersBaseDetails) {
		this.beneficialOwnersBaseDetails = beneficialOwnersBaseDetails;
	}	
}
