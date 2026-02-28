package com.adminremit.austrac.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AustracReportResponceDTO {

	//file Name
	private String fileName = "IFTI-DRA";
	//Transaction details
	private String tranBookingDate; //*
	private String tranCompletedDate; //*
	private String tranCurrencyCode; //*
	private BigDecimal transferAmount; //*
	private String tranType = ""; //*
	private String tranDescOfMoney;
	private String tranRefNo;
	//Ordering customer
	private String ordrCustFullName; //*
	private String ordrCustOtherName;
	private String ordrCustDOB; //*
	//Ordering customer contact details
	private String ordrCustAddress; //*
	private String ordrCustCity; //*
	private String ordrCustState; //*
	private String ordrCustPostCode; //*
	private String ordrCustCountry; //*
	private String ordrCustPostAdress; 
	private String ordrCustPhone; 
	private String ordrCustEmail; 
	//Ordering customer business details
	private String ordrCustBusiOccupation;
	private String ordrCustBusiABN;
	private Long ordrCustBusiCustNo; //*
	private String ordrCustBusiAccNo;
	private String ordrCustBusiStruc;
	//Ordering customer identification details
	private String docType1; //*
	private String docType1Other; 
	private String docNumber1; //*
	private String docIssuer1; //*
	private String docType2;
	private String docType2Other;
	private String docNumber2;
	private String docIssuer2;
	private String docElectronicData="AUSTRALIAN CLAIMS DATABASE, ILLION CREDIT HEADER"; //*
	//Beneficiary customer
	private String benfullName; //*
	private String benDOB;
	private String benBussName;
	private String benAdress; //*
	private String benCity; //*
	private String benState; //*
	private String benPostCode; //*
	private String benCountry; //*
	private String benPostalAdres;
	private String benPhoneNo;
	private String benEmail;
	//Beneficiary customer account details
	private String benAccNo; //*
	private String benBankName; //*
	private String benBankCityName; //*
	private String benBankCountryName; //*
	//Person/organisation accepting the transfer instruction from the ordering customer
	private String orgAccIdentificationNumber = "IND100725863-001"; //*
	private String orgAccFullName = "FYNTE PTY. LTD."; //*
	private String orgAccAdress = "UNIT 10, 2-8 HILL STREET"; //*
	private String orgAccCity = "Baulkham Hills"; //*
	private String orgAccState = "New South Wales"; //*
	private String orgAccPostCode = "2153"; //*
	private String orgAccMoney = "Y"; //*
	private String orgAccSendingTranAmt = "Y"; //*
	//Person/organisation accepting the money or property from the ordering customer (if different)
	private String orgAccMoneyFrOrdCustFullName;
	private String orgAccMoneyFrOrdCustCity;
	private String orgAccMoneyFrOrdCustState;
	private String orgAccMoneyFrOrdCustpostCode;
	//Person/organisation sending the transfer instruction (if different)
	private String orgSendTranInstrucFullNanel;
	private String orgSendTranInstrucOtherName;
	private Date orgSendTranInstrucDOB;
	private String orgSendTranInstrucAdres;
	private String orgSendTranInstrucCity;
	private String orgSendTranInstrucState;
	private String orgSendTranInstrucPostCode;
	private String orgSendTranInstrucPostalAdres;
	private String orgSendTranInstrucPhoneNo;
	private String orgSendTranInstrucEmail;
	private String orgSendTranInstrucOcupation;
	private String orgSendTranInstrucABN;
	private String orgSendTranInstrucBusiStruct;
	//Person/organisation receiving the transfer instruction
	private String orgRecTranInstrutFullName; //*
	private String orgRecTranInstrutAdress; //*
	private String orgRecTranInstrutCity; //*
	private String orgRecTranInstrutState; //*
	private String orgRecTranInstrutPostCode; //*
	private String orgRecTranInstrutCountry; //*
	private String orgRecTranInstrutDistributeMoney; //*
	private String orgRecTranInstrutBusiLocMoneyBeingDistributed; //*
	//Reason
	private String purpose;
	

	public String getFileName() {		
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTranBookingDate() {
		return tranBookingDate;
	}
	public void setTranBookingDate(String tranBookingDate) {
		this.tranBookingDate = tranBookingDate;
	}
	public String getTranCompletedDate() {
		return tranCompletedDate;
	}
	public void setTranCompletedDate(String tranCompletedDate) {
		this.tranCompletedDate = tranCompletedDate;
	}
	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}
	public void setTranCurrencyCode(String tranCurrencyCode) {
		this.tranCurrencyCode = tranCurrencyCode;
	}
	public BigDecimal getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getTranDescOfMoney() {
		return tranDescOfMoney;
	}
	public void setTranDescOfMoney(String tranDescOfMoney) {
		this.tranDescOfMoney = tranDescOfMoney;
	}
	public String getTranRefNo() {
		return tranRefNo;
	}
	public void setTranRefNo(String tranRefNo) {
		this.tranRefNo = tranRefNo;
	}
	public String getOrdrCustFullName() {
		return ordrCustFullName;
	}
	public void setOrdrCustFullName(String ordrCustFullName) {
		this.ordrCustFullName = ordrCustFullName;
	}
	public String getOrdrCustOtherName() {
		return ordrCustOtherName;
	}
	public void setOrdrCustOtherName(String ordrCustOtherName) {
		this.ordrCustOtherName = ordrCustOtherName;
	}
	public String getOrdrCustDOB() {
		return ordrCustDOB;
	}
	public void setOrdrCustDOB(String ordrCustDOB) {
		this.ordrCustDOB = ordrCustDOB;
	}
	public String getOrdrCustAddress() {
		return ordrCustAddress;
	}
	public void setOrdrCustAddress(String ordrCustAddress) {
		this.ordrCustAddress = ordrCustAddress;
	}
	public String getOrdrCustCity() {
		return ordrCustCity;
	}
	public void setOrdrCustCity(String ordrCustCity) {
		this.ordrCustCity = ordrCustCity;
	}
	public String getOrdrCustState() {
		return ordrCustState;
	}
	public void setOrdrCustState(String ordrCustState) {
		this.ordrCustState = ordrCustState;
	}
	public String getOrdrCustPostCode() {
		return ordrCustPostCode;
	}
	public void setOrdrCustPostCode(String ordrCustPostCode) {
		this.ordrCustPostCode = ordrCustPostCode;
	}
	public String getOrdrCustCountry() {
		return ordrCustCountry;
	}
	public void setOrdrCustCountry(String ordrCustCountry) {
		this.ordrCustCountry = ordrCustCountry;
	}
	public String getOrdrCustPostAdress() {
		return ordrCustPostAdress;
	}
	public void setOrdrCustPostAdress(String ordrCustPostAdress) {
		this.ordrCustPostAdress = ordrCustPostAdress;
	}
	public String getOrdrCustPhone() {
		return ordrCustPhone;
	}
	public void setOrdrCustPhone(String ordrCustPhone) {
		this.ordrCustPhone = ordrCustPhone;
	}
	public String getOrdrCustEmail() {
		return ordrCustEmail;
	}
	public void setOrdrCustEmail(String ordrCustEmail) {
		this.ordrCustEmail = ordrCustEmail;
	}
	public String getOrdrCustBusiOccupation() {
		return ordrCustBusiOccupation;
	}
	public void setOrdrCustBusiOccupation(String ordrCustBusiOccupation) {
		this.ordrCustBusiOccupation = ordrCustBusiOccupation;
	}
	public String getOrdrCustBusiABN() {
		return ordrCustBusiABN;
	}
	public void setOrdrCustBusiABN(String ordrCustBusiABN) {
		this.ordrCustBusiABN = ordrCustBusiABN;
	}
	public Long getOrdrCustBusiCustNo() {
		return ordrCustBusiCustNo;
	}
	public void setOrdrCustBusiCustNo(Long ordrCustBusiCustNo) {
		this.ordrCustBusiCustNo = ordrCustBusiCustNo;
	}
	public String getOrdrCustBusiAccNo() {
		return ordrCustBusiAccNo;
	}
	public void setOrdrCustBusiAccNo(String ordrCustBusiAccNo) {
		this.ordrCustBusiAccNo = ordrCustBusiAccNo;
	}
	public String getOrdrCustBusiStruc() {
		return ordrCustBusiStruc;
	}
	public void setOrdrCustBusiStruc(String ordrCustBusiStruc) {
		this.ordrCustBusiStruc = ordrCustBusiStruc;
	}
	public String getDocType1() {
		return docType1;
	}
	public void setDocType1(String docType1) {
		this.docType1 = docType1;
	}
	public String getDocType1Other() {
		return docType1Other;
	}
	public void setDocType1Other(String docType1Other) {
		this.docType1Other = docType1Other;
	}
	public String getDocNumber1() {
		return docNumber1;
	}
	public void setDocNumber1(String docNumber1) {
		this.docNumber1 = docNumber1;
	}
	public String getDocIssuer1() {
		return docIssuer1;
	}
	public void setDocIssuer1(String docIssuer1) {
		this.docIssuer1 = docIssuer1;
	}
	public String getDocType2() {
		return docType2;
	}
	public void setDocType2(String docType2) {
		this.docType2 = docType2;
	}
	public String getDocType2Other() {
		return docType2Other;
	}
	public void setDocType2Other(String docType2Other) {
		this.docType2Other = docType2Other;
	}
	public String getDocNumber2() {
		return docNumber2;
	}
	public void setDocNumber2(String docNumber2) {
		this.docNumber2 = docNumber2;
	}
	public String getDocIssuer2() {
		return docIssuer2;
	}
	public void setDocIssuer2(String docIssuer2) {
		this.docIssuer2 = docIssuer2;
	}
	public String getDocElectronicData() {
		return docElectronicData;
	}
	public void setDocElectronicData(String docElectronicData) {
		this.docElectronicData = docElectronicData;
	}
	public String getBenfullName() {
		return benfullName;
	}
	public void setBenfullName(String benfullName) {
		this.benfullName = benfullName;
	}
	public String getBenDOB() {
		return benDOB;
	}
	public void setBenDOB(String benDOB) {
		this.benDOB = benDOB;
	}
	public String getBenBussName() {
		return benBussName;
	}
	public void setBenBussName(String benBussName) {
		this.benBussName = benBussName;
	}
	public String getBenAdress() {
		return benAdress;
	}
	public void setBenAdress(String benAdress) {
		this.benAdress = benAdress;
	}
	public String getBenCity() {
		return benCity;
	}
	public void setBenCity(String benCity) {
		this.benCity = benCity;
	}
	public String getBenState() {
		return benState;
	}
	public void setBenState(String benState) {
		this.benState = benState;
	}
	public String getBenPostCode() {
		return benPostCode;
	}
	public void setBenPostCode(String benPostCode) {
		this.benPostCode = benPostCode;
	}
	public String getBenCountry() {
		return benCountry;
	}
	public void setBenCountry(String benCountry) {
		this.benCountry = benCountry;
	}
	public String getBenPostalAdres() {
		return benPostalAdres;
	}
	public void setBenPostalAdres(String benPostalAdres) {
		this.benPostalAdres = benPostalAdres;
	}
	public String getBenPhoneNo() {
		return benPhoneNo;
	}
	public void setBenPhoneNo(String benPhoneNo) {
		this.benPhoneNo = benPhoneNo;
	}
	public String getBenEmail() {
		return benEmail;
	}
	public void setBenEmail(String benEmail) {
		this.benEmail = benEmail;
	}
	public String getBenAccNo() {
		return benAccNo;
	}
	public void setBenAccNo(String benAccNo) {
		this.benAccNo = benAccNo;
	}
	public String getBenBankName() {
		return benBankName;
	}
	public void setBenBankName(String benBankName) {
		this.benBankName = benBankName;
	}
	public String getBenBankCityName() {
		return benBankCityName;
	}
	public void setBenBankCityName(String benBankCityName) {
		this.benBankCityName = benBankCityName;
	}
	public String getBenBankCountryName() {
		return benBankCountryName;
	}
	public void setBenBankCountryName(String benBankCountryName) {
		this.benBankCountryName = benBankCountryName;
	}
	public String getOrgAccIdentificationNumber() {
		return orgAccIdentificationNumber;
	}
	public void setOrgAccIdentificationNumber(String orgAccIdentificationNumber) {
		this.orgAccIdentificationNumber = orgAccIdentificationNumber;
	}
	public String getOrgAccFullName() {
		return orgAccFullName;
	}
	public void setOrgAccFullName(String orgAccFullName) {
		this.orgAccFullName = orgAccFullName;
	}
	public String getOrgAccAdress() {
		return orgAccAdress;
	}
	public void setOrgAccAdress(String orgAccAdress) {
		this.orgAccAdress = orgAccAdress;
	}
	public String getOrgAccCity() {
		return orgAccCity;
	}
	public void setOrgAccCity(String orgAccCity) {
		this.orgAccCity = orgAccCity;
	}
	public String getOrgAccState() {
		return orgAccState;
	}
	public void setOrgAccState(String orgAccState) {
		this.orgAccState = orgAccState;
	}
	public String getOrgAccPostCode() {
		return orgAccPostCode;
	}
	public void setOrgAccPostCode(String orgAccPostCode) {
		this.orgAccPostCode = orgAccPostCode;
	}
	public String getOrgAccMoney() {
		return orgAccMoney;
	}
	public void setOrgAccMoney(String orgAccMoney) {
		this.orgAccMoney = orgAccMoney;
	}
	public String getOrgAccSendingTranAmt() {
		return orgAccSendingTranAmt;
	}
	public void setOrgAccSendingTranAmt(String orgAccSendingTranAmt) {
		this.orgAccSendingTranAmt = orgAccSendingTranAmt;
	}
	public String getOrgAccMoneyFrOrdCustFullName() {
		return orgAccMoneyFrOrdCustFullName;
	}
	public void setOrgAccMoneyFrOrdCustFullName(String orgAccMoneyFrOrdCustFullName) {
		this.orgAccMoneyFrOrdCustFullName = orgAccMoneyFrOrdCustFullName;
	}
	public String getOrgAccMoneyFrOrdCustCity() {
		return orgAccMoneyFrOrdCustCity;
	}
	public void setOrgAccMoneyFrOrdCustCity(String orgAccMoneyFrOrdCustCity) {
		this.orgAccMoneyFrOrdCustCity = orgAccMoneyFrOrdCustCity;
	}
	public String getOrgAccMoneyFrOrdCustState() {
		return orgAccMoneyFrOrdCustState;
	}
	public void setOrgAccMoneyFrOrdCustState(String orgAccMoneyFrOrdCustState) {
		this.orgAccMoneyFrOrdCustState = orgAccMoneyFrOrdCustState;
	}
	public String getOrgAccMoneyFrOrdCustpostCode() {
		return orgAccMoneyFrOrdCustpostCode;
	}
	public void setOrgAccMoneyFrOrdCustpostCode(String orgAccMoneyFrOrdCustpostCode) {
		this.orgAccMoneyFrOrdCustpostCode = orgAccMoneyFrOrdCustpostCode;
	}
	public String getOrgSendTranInstrucFullNanel() {
		return orgSendTranInstrucFullNanel;
	}
	public void setOrgSendTranInstrucFullNanel(String orgSendTranInstrucFullNanel) {
		this.orgSendTranInstrucFullNanel = orgSendTranInstrucFullNanel;
	}
	public String getOrgSendTranInstrucOtherName() {
		return orgSendTranInstrucOtherName;
	}
	public void setOrgSendTranInstrucOtherName(String orgSendTranInstrucOtherName) {
		this.orgSendTranInstrucOtherName = orgSendTranInstrucOtherName;
	}
	public Date getOrgSendTranInstrucDOB() {
		return orgSendTranInstrucDOB;
	}
	public void setOrgSendTranInstrucDOB(Date orgSendTranInstrucDOB) {
		this.orgSendTranInstrucDOB = orgSendTranInstrucDOB;
	}
	public String getOrgSendTranInstrucAdres() {
		return orgSendTranInstrucAdres;
	}
	public void setOrgSendTranInstrucAdres(String orgSendTranInstrucAdres) {
		this.orgSendTranInstrucAdres = orgSendTranInstrucAdres;
	}
	public String getOrgSendTranInstrucCity() {
		return orgSendTranInstrucCity;
	}
	public void setOrgSendTranInstrucCity(String orgSendTranInstrucCity) {
		this.orgSendTranInstrucCity = orgSendTranInstrucCity;
	}
	public String getOrgSendTranInstrucState() {
		return orgSendTranInstrucState;
	}
	public void setOrgSendTranInstrucState(String orgSendTranInstrucState) {
		this.orgSendTranInstrucState = orgSendTranInstrucState;
	}
	public String getOrgSendTranInstrucPostCode() {
		return orgSendTranInstrucPostCode;
	}
	public void setOrgSendTranInstrucPostCode(String orgSendTranInstrucPostCode) {
		this.orgSendTranInstrucPostCode = orgSendTranInstrucPostCode;
	}
	public String getOrgSendTranInstrucPostalAdres() {
		return orgSendTranInstrucPostalAdres;
	}
	public void setOrgSendTranInstrucPostalAdres(String orgSendTranInstrucPostalAdres) {
		this.orgSendTranInstrucPostalAdres = orgSendTranInstrucPostalAdres;
	}
	public String getOrgSendTranInstrucPhoneNo() {
		return orgSendTranInstrucPhoneNo;
	}
	public void setOrgSendTranInstrucPhoneNo(String orgSendTranInstrucPhoneNo) {
		this.orgSendTranInstrucPhoneNo = orgSendTranInstrucPhoneNo;
	}
	public String getOrgSendTranInstrucEmail() {
		return orgSendTranInstrucEmail;
	}
	public void setOrgSendTranInstrucEmail(String orgSendTranInstrucEmail) {
		this.orgSendTranInstrucEmail = orgSendTranInstrucEmail;
	}
	public String getOrgSendTranInstrucOcupation() {
		return orgSendTranInstrucOcupation;
	}
	public void setOrgSendTranInstrucOcupation(String orgSendTranInstrucOcupation) {
		this.orgSendTranInstrucOcupation = orgSendTranInstrucOcupation;
	}
	public String getOrgSendTranInstrucABN() {
		return orgSendTranInstrucABN;
	}
	public void setOrgSendTranInstrucABN(String orgSendTranInstrucABN) {
		this.orgSendTranInstrucABN = orgSendTranInstrucABN;
	}
	public String getOrgSendTranInstrucBusiStruct() {
		return orgSendTranInstrucBusiStruct;
	}
	public void setOrgSendTranInstrucBusiStruct(String orgSendTranInstrucBusiStruct) {
		this.orgSendTranInstrucBusiStruct = orgSendTranInstrucBusiStruct;
	}
	public String getOrgRecTranInstrutFullName() {
		return orgRecTranInstrutFullName;
	}
	public void setOrgRecTranInstrutFullName(String orgRecTranInstrutFullName) {
		this.orgRecTranInstrutFullName = orgRecTranInstrutFullName;
	}
	public String getOrgRecTranInstrutAdress() {
		return orgRecTranInstrutAdress;
	}
	public void setOrgRecTranInstrutAdress(String orgRecTranInstrutAdress) {
		this.orgRecTranInstrutAdress = orgRecTranInstrutAdress;
	}
	public String getOrgRecTranInstrutCity() {
		return orgRecTranInstrutCity;
	}
	public void setOrgRecTranInstrutCity(String orgRecTranInstrutCity) {
		this.orgRecTranInstrutCity = orgRecTranInstrutCity;
	}
	public String getOrgRecTranInstrutState() {
		return orgRecTranInstrutState;
	}
	public void setOrgRecTranInstrutState(String orgRecTranInstrutState) {
		this.orgRecTranInstrutState = orgRecTranInstrutState;
	}
	public String getOrgRecTranInstrutPostCode() {
		return orgRecTranInstrutPostCode;
	}
	public void setOrgRecTranInstrutPostCode(String orgRecTranInstrutPostCode) {
		this.orgRecTranInstrutPostCode = orgRecTranInstrutPostCode;
	}
	public String getOrgRecTranInstrutCountry() {
		return orgRecTranInstrutCountry;
	}
	public void setOrgRecTranInstrutCountry(String orgRecTranInstrutCountry) {
		this.orgRecTranInstrutCountry = orgRecTranInstrutCountry;
	}
	public String getOrgRecTranInstrutDistributeMoney() {
		return orgRecTranInstrutDistributeMoney;
	}
	public void setOrgRecTranInstrutDistributeMoney(String orgRecTranInstrutDistributeMoney) {
		this.orgRecTranInstrutDistributeMoney = orgRecTranInstrutDistributeMoney;
	}
	public String getOrgRecTranInstrutBusiLocMoneyBeingDistributed() {
		return orgRecTranInstrutBusiLocMoneyBeingDistributed;
	}
	public void setOrgRecTranInstrutBusiLocMoneyBeingDistributed(String orgRecTranInstrutBusiLocMoneyBeingDistributed) {
		this.orgRecTranInstrutBusiLocMoneyBeingDistributed = orgRecTranInstrutBusiLocMoneyBeingDistributed;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	
	
}
