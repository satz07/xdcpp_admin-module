package com.adminremit.auth.dto;

public class RemitterEDDDTO {
	
	private String eDD;
	private String eDDCountry;
	private String profession;
	private String professionType;
	private String otherProfession;
	private String sourceOfIncome;
	private String annualIncomeSlab;
	
	public String geteDD() {
		return eDD;
	}
	public void seteDD(String eDD) {
		this.eDD = eDD;
	}
	public String geteDDCountry() {
		return eDDCountry;
	}
	public void seteDDCountry(String eDDCountry) {
		this.eDDCountry = eDDCountry;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	public String getOtherProfession() {
		return otherProfession;
	}
	public void setOtherProfession(String otherProfession) {
		this.otherProfession = otherProfession;
	}
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	public String getAnnualIncomeSlab() {
		return annualIncomeSlab;
	}
	public void setAnnualIncomeSlab(String annualIncomeSlab) {
		this.annualIncomeSlab = annualIncomeSlab;
	}


}
