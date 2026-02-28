package com.adminremit.austrac.report;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.user.repository.UserCalculatorMappingRepository;
import com.adminremit.user.service.UserCalculatorMappingService;


public class AustracReportCommonService {
	
	@Autowired
	private UserCalculatorMappingRepository userCalculatorMappingRepository;

	public String buildXmlTemplateForAustracReport(AustracReportResponceDTO austracReportResponceDTO) {

		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<ifti-draList xmlns=\"http://austrac.gov.au/schema/reporting/IFTI-DRA-1-2\">\r\n" + 
				"<reNumber>71501</reNumber>\r\n" +
				"<fileName>"+austracReportResponceDTO.getFileName()+"</fileName>\r\n" + 
				"<reportCount>1</reportCount>\r\n" + 
				"<ifti-dra id=\"ID_R\">\r\n" + 
				"<header id=\"ID_1R01\">\r\n" + 
				"	<txnRefNo>"+austracReportResponceDTO.getTranRefNo()+"</txnRefNo>\r\n" + 
				"</header>\r\n" + 
				"<transaction id=\"ID_1R02\">\r\n" + 
				"	<txnDate>"+austracReportResponceDTO.getTranBookingDate()+"</txnDate>\r\n" + 
				"	<currencyAmount id=\"ID_1R03\">\r\n" + 
				"		<currency>"+austracReportResponceDTO.getTranCurrencyCode()+"</currency>\r\n" + 
				"		<amount>"+austracReportResponceDTO.getTransferAmount()+"</amount>\r\n" + 
				"	</currencyAmount>\r\n" + 
				"	<direction>I</direction>\r\n" + 
				"	<tfrType id=\"ID_1R04\">\r\n" + 
				"		<money>"+austracReportResponceDTO.getTranType()+"</money>\r\n" +
				"	</tfrType>\r\n" + 
				"	<valueDate>"+austracReportResponceDTO.getTranCompletedDate()+"</valueDate>\r\n" + 
				"</transaction>\r\n" + 
				"<transferor id=\"ID_1R05\">\r\n" + 
				"	<fullName>"+austracReportResponceDTO.getOrdrCustFullName()+"</fullName>\r\n" + 
				"	<mainAddress id=\"ID_1R06\">\r\n" + 
				"		<addr>"+austracReportResponceDTO.getOrdrCustAddress()+"</addr>\r\n" + 
				"		<suburb>"+austracReportResponceDTO.getOrdrCustCity()+"</suburb>\r\n" + 
				"		<state>"+austracReportResponceDTO.getOrdrCustState()+"</state>\r\n" + 
				"		<postcode>"+austracReportResponceDTO.getOrdrCustPostCode()+"</postcode>\r\n" + 
				"		<country>"+austracReportResponceDTO.getOrdrCustCountry()+"</country>\r\n" + 
				"	</mainAddress>\r\n" + 
				"	<custNo>"+austracReportResponceDTO.getOrdrCustBusiCustNo()+"</custNo>\r\n" + 
				"	<dob>"+austracReportResponceDTO.getOrdrCustDOB()+"</dob>\r\n" + 
				"	<identification id=\"ID_1R07\">\r\n" + 
				"		<type>"+austracReportResponceDTO.getDocType1()+"</type>\r\n" + 
				"		<number>"+austracReportResponceDTO.getDocNumber1()+"</number>\r\n" + 
				"		<issuer>"+austracReportResponceDTO.getDocIssuer1()+"</issuer>\r\n" + 
				"	</identification>\r\n" + 
				"	<electDataSrc>"+austracReportResponceDTO.getDocElectronicData()+"</electDataSrc>\r\n" + 
				"</transferor>\r\n" + 
				"<orderingInstn id=\"ID_1R8\">\r\n" + 
				"	 <branch id=\"ID_1R9\">\r\n" + 
				"	 	<branchId>"+austracReportResponceDTO.getOrgAccIdentificationNumber()+"</branchId>\r\n" + 
				"		<fullName>"+austracReportResponceDTO.getOrgAccFullName()+"</fullName>\r\n" + 
				"		<mainAddress id=\"ID_1R10\">\r\n" + 
				"			<addr>"+austracReportResponceDTO.getOrgAccAdress()+"</addr>\r\n" + 
				"			<suburb>"+austracReportResponceDTO.getOrgAccCity()+"</suburb>\r\n" + 
				"			<state>"+austracReportResponceDTO.getOrgAccState()+"</state>\r\n" + 
				"			<postcode>"+austracReportResponceDTO.getOrgAccPostCode()+"</postcode>\r\n" + 
				"		</mainAddress>\r\n" + 
				"	</branch>\r\n" + 
				"	<foreignBased>N</foreignBased>\r\n" + 
				"</orderingInstn>\r\n" + 
				"<initiatingInstn id=\"ID_1R11\">\r\n" + 
				"	<sameAsOrderingInstn>"+austracReportResponceDTO.getOrgAccMoney()+"</sameAsOrderingInstn>\r\n" + 
				"</initiatingInstn>\r\n" + 
				"<sendingInstn id=\"ID_1R12\">\r\n" + 
				"	<sameAsOrderingInstn>"+austracReportResponceDTO.getOrgAccSendingTranAmt()+"</sameAsOrderingInstn>\r\n" + 
				"</sendingInstn>\r\n" + 
				"<receivingInstn id=\"ID_1R13\">\r\n" + 
				"	<fullName>"+austracReportResponceDTO.getOrgRecTranInstrutFullName()+"</fullName>\r\n" + 
				"	<mainAddress id=\"ID_1R14\">\r\n" + 
				"		<addr>"+austracReportResponceDTO.getOrgRecTranInstrutAdress()+"</addr>\r\n" + 
				"		<suburb>"+austracReportResponceDTO.getOrgRecTranInstrutCity()+"</suburb>\r\n" + 
				"		<state>"+austracReportResponceDTO.getOrgRecTranInstrutState()+"</state>\r\n" + 
				"		<postcode>"+austracReportResponceDTO.getOrgRecTranInstrutPostCode()+"</postcode>\r\n" + 
				"		<country>"+austracReportResponceDTO.getOrgRecTranInstrutCountry()+"</country>\r\n" + 
				"	</mainAddress>\r\n" + 
				"</receivingInstn>\r\n" + 
				"<beneficiaryInstn id=\"ID_1R15\">\r\n" + 
				"	<sameAsReceivingInstn>"+austracReportResponceDTO.getOrgRecTranInstrutDistributeMoney()+"</sameAsReceivingInstn>\r\n" + 
				"</beneficiaryInstn>\r\n" + 
				"<transferee id=\"ID_1R16\">\r\n" + 
				"	<fullName>"+austracReportResponceDTO.getBenfullName()+"</fullName>\r\n" + 
				"	<mainAddress id=\"ID_1R17\">\r\n" + 
				"		<addr>"+austracReportResponceDTO.getBenAdress()+"</addr>\r\n" + 
				"		<suburb>"+austracReportResponceDTO.getBenCity()+"</suburb>\r\n" + 
				"		<state>"+austracReportResponceDTO.getBenState()+"</state>\r\n" + 
				"		<postcode>"+austracReportResponceDTO.getBenPostCode()+"</postcode>\r\n" + 
				"		<country>"+austracReportResponceDTO.getBenCountry()+"</country>\r\n" + 
				"	</mainAddress>\r\n" + 
				"	<account id=\"ID_1R18\">\r\n" + 
				"		<acctNumber>"+austracReportResponceDTO.getBenAccNo()+"</acctNumber>\r\n" + 
				"		<name>"+austracReportResponceDTO.getBenBankName()+"</name>\r\n" + 
				"		<city>"+austracReportResponceDTO.getBenCity()+"</city>\r\n" + 
				"		<country>"+austracReportResponceDTO.getBenCountry()+"</country>\r\n" + 
				"	</account>\r\n" + 
				"</transferee>\r\n" + 
				"<additionalDetails id=\"ID_1R19\">\r\n" + 
				"	<reasonForTransfer>"+austracReportResponceDTO.getPurpose()+"</reasonForTransfer>\r\n" + 
				"</additionalDetails>\r\n" + 
				"</ifti-dra>\r\n" + 
				"</ifti-draList>";
		return request;
	}

	public String makeHttpCallForAustracReport(AustracReportResponceDTO austracReportResponceDTO) {
		return buildXmlTemplateForAustracReport(austracReportResponceDTO);
	}


}

