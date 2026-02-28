package com.adminremit.austrac.report;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;

public class AustracReportHelper {

	public String getAddress(PersonalDetails personalDetails) {
		String address ="";
		
		if(!StringUtils.isEmpty(personalDetails.getFlatNumber())) {
			address = personalDetails.getFlatNumber();
		}else {
			address = "";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getStreetNumber())) {
			address = address+" "+personalDetails.getStreetNumber();
		}else {
			address = address+" ";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getStreetName())) {
			address = address+" "+personalDetails.getStreetName();
		}else {
			address = address+" ";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getStreetType())) {
			address = address+" "+personalDetails.getStreetType();
		}else {
			address = address+" ";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getSuburb())) {
			address = address+" "+personalDetails.getSuburb();
		}else {
			address = address+" ";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getProvince())) {
			address = address+", "+personalDetails.getProvince();
		}else {
			address = address+" ";
		}
		
		if(!StringUtils.isEmpty(personalDetails.getCountry())) {
			address = address+", "+personalDetails.getCountry();
		}else {
			address = address+" ";
		}
		
		return address;
	}

	public String getIssuer(PersonalDocuments personalDocuments){
		if(personalDocuments.getDocumentTypes() != null ? ((personalDocuments.getDocumentTypes().toString().equals("PASSPORT") || personalDocuments.getDocumentTypes().toString().equals("VISA")) && personalDocuments.getPersonalDetails().getCountry().equals("IN")) : false){
			return "Indian Ministry of External Affairs";
		}else if(personalDocuments.getDocumentTypes() != null ? ((personalDocuments.getDocumentTypes().toString().equals("PASSPORT") || personalDocuments.getDocumentTypes().toString().equals("VISA")) && personalDocuments.getPersonalDetails().getCountry().equals("NP")) : false){
			return  "The Department of Passports";
		}else if(personalDocuments.getDocumentTypes() != null ? (personalDocuments.getDocumentTypes().toString().equals("DRIVERS_LICENCE") && personalDocuments.getPersonalDetails().getProvince().equals("NSW")) : false){
			return "Roads and Maritime Services else remitter state is Victoria, Issuer to be Vicroads";
		}else if(personalDocuments.getDocumentTypes() != null ? personalDocuments.getDocumentTypes().toString().equals("MEDICARE") : false){
			return "Australian Government";
		}
		return "";
	}

	public void personToRecivingTranAmt(AustracReportResponceDTO austracReportResponceDTO, UserCalculatorMapping userCalculatorMapping) {
		if(userCalculatorMapping.getToCountryCode() != null){
			if(userCalculatorMapping.getToCountryCode().equals("NP")){
				austracReportResponceDTO.setOrgRecTranInstrutFullName("City Express Money Transfer Pvt. Ltd.");
				austracReportResponceDTO.setOrgRecTranInstrutAdress("City Express Complex, 3rd floor, Bank Road, Kamaladi");
				austracReportResponceDTO.setOrgRecTranInstrutCity("Kathmandu");
				austracReportResponceDTO.setOrgRecTranInstrutState("Kathmandu");
				austracReportResponceDTO.setOrgRecTranInstrutPostCode("44600");
				austracReportResponceDTO.setOrgRecTranInstrutCountry("Nepal");
				austracReportResponceDTO.setOrgRecTranInstrutBusiLocMoneyBeingDistributed("Y");
				austracReportResponceDTO.setOrgRecTranInstrutDistributeMoney("Y");
			}else if(userCalculatorMapping.getToCountryCode().equals("IN")){
				austracReportResponceDTO.setOrgRecTranInstrutFullName("YES BANK LTD");
				austracReportResponceDTO.setOrgRecTranInstrutAdress("YES BANK Tower, IFC 2, 15th Floor, Senapati Bapat Marg, Elphinstone");
				austracReportResponceDTO.setOrgRecTranInstrutCity("Mumbai");
				austracReportResponceDTO.setOrgRecTranInstrutState("Maharashtra");
				austracReportResponceDTO.setOrgRecTranInstrutPostCode("400013");
				austracReportResponceDTO.setOrgRecTranInstrutCountry("India");
				austracReportResponceDTO.setOrgRecTranInstrutBusiLocMoneyBeingDistributed("Y");
				austracReportResponceDTO.setOrgRecTranInstrutDistributeMoney("Y");
			}
		}
	}

	public static Date getStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public String setDocType(String docType) {
		if(docType.equals("PASSPORT")){
			return "P";
		}else if(docType.equals("DRIVERS_LICENCE")){
			return "D";
		}else if(docType.equals("MEDICARE")){
			return "BENE";
		}else if(docType.equals("VISA")){
			return "C";
		}
		return "";
	}
}
