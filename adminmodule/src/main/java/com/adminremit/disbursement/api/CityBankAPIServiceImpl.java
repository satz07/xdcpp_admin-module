package com.adminremit.disbursement.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.service.PurposeListMasterService;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.service.PersonalDocumentsService;
import com.adminremit.report.constants.ReportsConstants;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UsersService;
//WEB3 migration changes - Commented out NepalServiceClient imports as JAR is not available
//import com.fynte.api.client.nepal.QueryTXNStatus;
//import com.fynte.api.client.nepal.QueryTXNStatusResponse;
//import com.fynte.api.client.nepal.SendTransaction;
//import com.fynte.api.client.nepal.SendTransactionResponse;
@Service
@Qualifier("cityService")
public class CityBankAPIServiceImpl  extends WebServiceGatewaySupport implements RemitAPIService {
	
	@Value("${nepal.remitt.url}")
    private String remittServiceURL;
	
	@Value("${nepal.remitt.agentcode}")
	 private String agentCode;
	@Value("${nepal.remitt.userid}")
	 private String userId;
	@Value("${nepal.remitt.apipassword}")
	 private String apiPassword;
	
	String sendingCountry ="AUS";
	String receivingCountry="NPL";
	
	 @Autowired
	 private UsersService usersService;
	 
    @Autowired
    private BeneficiaryUserService beneficiaryUserService;    

    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;
    
    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private PersonalDocumentsService personalDocumentsService;
    
    @Autowired
    private PurposeListMasterService purposeListMasterService;

	
	private static final Logger logger = LoggerFactory.getLogger(CityBankAPIServiceImpl.class);
	
	
	
	public CityBankAPIServiceImpl() {
		// TODO Auto-generated constructor stub
		//WEB3 migration changes - Commented out marshaller setup as NepalServiceClient JAR is not available
		//setDefaultUri(remittServiceURL);
		//setMarshaller(marshaller());
        //setUnmarshaller(marshaller());
        
    }
	
	//WEB3 migration changes - Commented out marshaller method as NepalServiceClient JAR is not available
	/*
	public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.fynte.api.client.nepal");
        return marshaller;
    }
	*/
	
	public String getSessionId() {
		return RandomStringUtils.randomAlphanumeric(50).toUpperCase();
	}

	@Override
	public Map<String,String> initiateTransaction(UserCalculatorMapping userCalculatorMapping) throws Exception {
		//WEB3 migration changes - Commented out implementation as NepalServiceClient JAR is not available
		throw new UnsupportedOperationException("NepalServiceClient functionality is temporarily disabled due to missing JAR dependency");
		
		/*WEB3 migration changes - Original implementation commented out
		 Users users = usersService.getUserById(userCalculatorMapping.getUser().getId());
         logger.info("Send Transaction initiated for  "+userCalculatorMapping.getRefId());
         BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
         BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
         PersonalDetails personalDetails = personalDetailsService.getLatestByUser(users.getId());
         PersonalDocuments personalDocuments = personalDocumentsService.fetchOne(personalDetails);
         String paymentModeType=beneficiaryAccount.getReceiveMode().getPaymentCodeMaster().getPaymentCode();
         Map<String,String> response =new HashMap<String,String>();
         response.put("paymentModeType", paymentModeType);
        String signature ="";
		
		SendTransaction sendTransaction = new SendTransaction();
		sendTransaction.setAGENTCODE(agentCode);
		signature+=agentCode;
		sendTransaction.setUSERID(userId);
		signature+=userId;
		sendTransaction.setAGENTSESSIONID(getSessionId());
		signature+=sendTransaction.getAGENTSESSIONID();
		sendTransaction.setAGENTTXNID(userCalculatorMapping.getRefId());
		signature+=sendTransaction.getAGENTTXNID();
		
		if (paymentModeType.equals(AccountType.WAL.name())) {
			sendTransaction.setLOCATIONID(beneficiaryAccount.getIfscCode());
			signature+=sendTransaction.getLOCATIONID();
			
		}
	
		
		
		//Sender Details
		sendTransaction.setSENDERFIRSTNAME(personalDetails.getFullName());
		signature+=sendTransaction.getSENDERFIRSTNAME();
		sendTransaction.setSENDERMIDDLENAME(personalDetails.getMiddleName());
		signature+=sendTransaction.getSENDERMIDDLENAME();
		sendTransaction.setSENDERLASTNAME(personalDetails.getLastName());
		signature+=sendTransaction.getSENDERLASTNAME();
		sendTransaction.setSENDERGENDER(personalDetails.getGender().name());
		signature+=sendTransaction.getSENDERGENDER();
		
		StringBuilder remitterAddress = new StringBuilder();
        remitterAddress.append(personalDetails.getFlatNumber());
        remitterAddress.append(",");
        remitterAddress.append(personalDetails.getStreetNumber());
        remitterAddress.append(",");
        remitterAddress.append(personalDetails.getStreetName());
        
        sendTransaction.setSENDERADDRESS(remitterAddress.toString());
        signature+=sendTransaction.getSENDERADDRESS();
        sendTransaction.setSENDERCITY(personalDetails.getProvince());
        signature+=sendTransaction.getSENDERCITY();
        sendTransaction.setSENDERCOUNTRY(sendingCountry); //3 digit country code not exist
        signature+=sendTransaction.getSENDERCOUNTRY();
        sendTransaction.setSENDERIDTYPE(personalDocuments.getDocumentTypes().name()); 
        signature+=sendTransaction.getSENDERIDTYPE();
        sendTransaction.setSENDERIDNUMBER(personalDocuments.getDocumentNumber());
        signature+=sendTransaction.getSENDERIDNUMBER();
       //Non-Mandatory fields:
       // "senderidissuedate",
       // "senderidexpiredate",
       // "senderdateofbirth",
       // "sendermobile",
       // "sourceoffund",
       // "senderoccupation",
       // "sendernationality"
        
        //Check later
        sendTransaction.setSOURCEOFFUND("");
        signature+=sendTransaction.getSOURCEOFFUND();
        sendTransaction.setSENDEROCCUPATION("");
        signature+=sendTransaction.getSENDEROCCUPATION();
        
        
        //String[] name= beneficiaryUser.getFullName().split(" ");
        
        sendTransaction.setRECEIVERFIRSTNAME(beneficiaryUser.getFullName());
        signature+=sendTransaction.getRECEIVERFIRSTNAME();
        if(beneficiaryUser.getMiddleName() !=null) {
	        sendTransaction.setRECEIVERMIDDLENAME(beneficiaryUser.getMiddleName());
	        signature+=sendTransaction.getRECEIVERMIDDLENAME();
        }
        if(beneficiaryUser.getLastName() != null) {
        	sendTransaction.setRECEIVERLASTNAME(beneficiaryUser.getLastName());
        	signature+=sendTransaction.getRECEIVERLASTNAME();
        }
        
        sendTransaction.setRECEIVERCOUNTRY(receivingCountry);
        signature+=sendTransaction.getRECEIVERCOUNTRY();
        sendTransaction.setRECEIVERCONTACTNUMBER(beneficiaryUser.getMobileNumber());
        signature+=sendTransaction.getRECEIVERCONTACTNUMBER();
        
        
        if (beneficiaryUser.getReceiverType().equals(3L)) {
			sendTransaction.setRELATIONSHIPTOBENEFICIARY("SELF");
		}
        else if (beneficiaryUser.getReceiverType().equals(2L)) {
			if(beneficiaryUser.getBeneficiaryRelationship() != null) {
				sendTransaction.setRELATIONSHIPTOBENEFICIARY(ReportsConstants.relationShipMap.get(beneficiaryUser.getBeneficiaryRelationship().intValue()));
			}else {
				sendTransaction.setRELATIONSHIPTOBENEFICIARY("FAMILY_FRIENDS");
			}
		}
        else{
			
			sendTransaction.setRELATIONSHIPTOBENEFICIARY("BUSINESS");
		}
        signature+=sendTransaction.getRELATIONSHIPTOBENEFICIARY();
        
        
        // Payment modes:
        // C- Cash Pickup
        // B- Account Deposit
        // I – Instant Credit
        // W – Mobile Wallet 
        
        if(paymentModeType.equals(AccountType.BAC.name())) {
        	//Hardcoding of 10L needs to be removed later
        	if(beneficiaryAccount.getInstaAvailable() && userCalculatorMapping.getTransferAmount().doubleValue() <= 1000000) {
        		sendTransaction.setPAYMENTMODE("I");
        		signature+=sendTransaction.getPAYMENTMODE();
        		sendTransaction.setBANKID(beneficiaryAccount.getInstaCode());
        		signature+=sendTransaction.getBANKID();
        	}else {
        		
        		sendTransaction.setPAYMENTMODE("B");
        		signature+=sendTransaction.getPAYMENTMODE();
        		sendTransaction.setBANKID(beneficiaryAccount.getIfscCode());
        		signature+=sendTransaction.getBANKID();
        		sendTransaction.setBANKBRANCHNAME(beneficiaryAccount.getBranchName());
        		signature+=sendTransaction.getBANKBRANCHNAME();
        		sendTransaction.setBANKNAME(beneficiaryAccount.getBranchName());
        		signature+=sendTransaction.getBANKNAME();
        	}
        	sendTransaction.setBANKACCOUNTNUMBER(beneficiaryAccount.getAccountNumber());
        	signature+=sendTransaction.getBANKACCOUNTNUMBER();
        }else if (paymentModeType.equals(AccountType.WAL.name())) {
        	sendTransaction.setPAYMENTMODE("W");
        	signature+=sendTransaction.getPAYMENTMODE();
		}else {
			sendTransaction.setPAYMENTMODE("C");
			signature+=sendTransaction.getPAYMENTMODE();
		}
        
        sendTransaction.setCALCBY("P"); 
        signature+=sendTransaction.getCALCBY();
        sendTransaction.setTRANSFERAMOUNT(userCalculatorMapping.getTotalConvertedValue().doubleValue()+"");
        signature+=sendTransaction.getTRANSFERAMOUNT();
        sendTransaction.setOURSERVICECHARGE("0");
        signature+=sendTransaction.getOURSERVICECHARGE();
        sendTransaction.setTRANSACTIONEXCHANGERATE("1");
        signature+=sendTransaction.getTRANSACTIONEXCHANGERATE();
        sendTransaction.setSETTLEMENTDOLLARRATE("1");
        signature+=sendTransaction.getSETTLEMENTDOLLARRATE();
        if(beneficiaryUser.getPurpose() !=null) {
	        Long purposeId= Long.valueOf(beneficiaryUser.getPurpose());
	        PurposeListMaster purposeListMaster =purposeListMasterService.getById(purposeId);
	        sendTransaction.setPURPOSEOFREMITTANCE(purposeListMaster.getPurpose());
        }else {
        	sendTransaction.setPURPOSEOFREMITTANCE("");
        }
        signature+=sendTransaction.getPURPOSEOFREMITTANCE();
        
        signature+=apiPassword;
        
        sendTransaction.setSIGNATURE(DigestUtils.sha256Hex(signature));
        
        logger.info("initiateTransaction signature for  "+signature+"\t is  \t"+sendTransaction.getSIGNATURE());
		
        
        WebServiceTemplate wst= getWebServiceTemplate();
		
		SendTransactionResponse bankListResponse = (SendTransactionResponse)wst
		          .marshalSendAndReceive(remittServiceURL, sendTransaction, 
		        		  new SoapActionCallback("ClientWebService/SendTransaction"));
		
		response.put("status", bankListResponse.getSendTransactionResult().getCODE());
		response.put("message", bankListResponse.getSendTransactionResult().getMESSAGE());
		
		response.put("PINNO", bankListResponse.getSendTransactionResult().getPINNO());
		
		logger.info("Send Transaction completed for  "+userCalculatorMapping.getRefId()+" with status "+bankListResponse.getSendTransactionResult().getCODE());
		response.put("paymentModeType", bankListResponse.getSendTransactionResult().getCODE());
		return response;
		*/
	}

	@Override
	public Map<String, String> getStatus(String referenceId) throws Exception {
		//WEB3 migration changes - Commented out implementation as NepalServiceClient JAR is not available
		throw new UnsupportedOperationException("NepalServiceClient functionality is temporarily disabled due to missing JAR dependency");
		
		/*WEB3 migration changes - Original implementation commented out
		// TODO Auto-generated method stub
		
		logger.info("getStatus initiated for  "+referenceId);
		//AGENT_CODE +     USER_ID + PINNO +     AGENT_SESSION_ID +     AGENT_TXNID +     API_PASSWORD 

		String signature ="";
		QueryTXNStatus queryTXNStatus = new QueryTXNStatus();
		queryTXNStatus.setAGENTCODE(agentCode);
		signature+=agentCode;
		queryTXNStatus.setUSERID(userId);
		signature+=userId;
		queryTXNStatus.setAGENTSESSIONID(getSessionId());
		signature+=queryTXNStatus.getAGENTSESSIONID();
		queryTXNStatus.setAGENTTXNID(referenceId);
		signature+=queryTXNStatus.getAGENTTXNID();
		
		signature+=apiPassword;
		queryTXNStatus.setSIGNATURE(DigestUtils.sha256Hex(signature));
		logger.info("getStatus signature for  "+signature+"\t is  \t"+queryTXNStatus.getSIGNATURE());
		    
	        WebServiceTemplate wst= getWebServiceTemplate();
			
			QueryTXNStatusResponse bankListResponse = (QueryTXNStatusResponse)wst
			          .marshalSendAndReceive(remittServiceURL, queryTXNStatus, 
			        		  new SoapActionCallback("ClientWebService/QueryTXNStatus"));
			
			
			logger.info("getStatus completed for  "+referenceId+" with status "+bankListResponse.getQueryTXNStatusResult().getSTATUS());
			
			Map<String, String>  obj =new HashMap<String, String>() ;
			obj.put("refid", referenceId);
			obj.put("status_code", bankListResponse.getQueryTXNStatusResult().getCODE());
			obj.put("message", bankListResponse.getQueryTXNStatusResult().getMESSAGE());
			obj.put("status", bankListResponse.getQueryTXNStatusResult().getSTATUS());
	        
			return obj;
		*/
	}

}
