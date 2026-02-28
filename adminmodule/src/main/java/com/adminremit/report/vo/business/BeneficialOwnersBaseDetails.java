package com.adminremit.report.vo.business;

import java.util.List;

public class BeneficialOwnersBaseDetails {
	
	private String companyIssuedShareCapital;
	private String registrationDoneByBeneficialOwner;
	private List<BeneficialOwner> beneficialOwnerList;
	private String registrationDoneBySeniorMgmtOfficial;
	
	public String getCompanyIssuedShareCapital() {
		return companyIssuedShareCapital;
	}
	public void setCompanyIssuedShareCapital(String companyIssuedShareCapital) {
		this.companyIssuedShareCapital = companyIssuedShareCapital;
	}
	public String getRegistrationDoneByBeneficialOwner() {
		return registrationDoneByBeneficialOwner;
	}
	public void setRegistrationDoneByBeneficialOwner(String registrationDoneByBeneficialOwner) {
		this.registrationDoneByBeneficialOwner = registrationDoneByBeneficialOwner;
	}
	public List<BeneficialOwner> getBeneficialOwnerList() {
		return beneficialOwnerList;
	}
	public void setBeneficialOwnerList(List<BeneficialOwner> beneficialOwnerList) {
		this.beneficialOwnerList = beneficialOwnerList;
	}
	public String getRegistrationDoneBySeniorMgmtOfficial() {
		return registrationDoneBySeniorMgmtOfficial;
	}
	public void setRegistrationDoneBySeniorMgmtOfficial(String registrationDoneBySeniorMgmtOfficial) {
		this.registrationDoneBySeniorMgmtOfficial = registrationDoneBySeniorMgmtOfficial;
	}	
}
