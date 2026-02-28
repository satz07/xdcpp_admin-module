package com.adminremit.backoffice.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.backoffice.dto.RemittanceDTO;
import com.adminremit.backoffice.dto.UPIValidationResponseDTO;
import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.TransactionType;
import com.adminremit.backoffice.model.TransferCurrency;
import com.adminremit.backoffice.model.TransferType;
import com.adminremit.backoffice.model.YesBankAPINames;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryNameCheckService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.disbursement.api.YesBankAPIService;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.service.PersonalDocumentsService;
import com.adminremit.receiver.model.ReceiverTransactionModel;
import com.adminremit.receiver.service.ReceiverPurposeService;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UserCalculatorMappingService;
import com.adminremit.user.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StartRemitterServiceImpl implements StartRemitterService {

    private static final Logger LOG = LoggerFactory.getLogger(StartRemitterServiceImpl.class);


    @Autowired
    private UserCalculatorMappingService userCalculatorMappingService;

    @Autowired
    private BeneficiaryUserService beneficiaryUserService;
    
    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;

    @Value("${ifsc.api.version}")
    private String version;

    @Value("${remit.partner.code}")
    private String partnerCode;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private PersonalDocumentsService personalDocumentsService;

    @Autowired
    private YesBankAPIService yesBankAPIService;

    @Autowired
    private BeneficiaryNameCheckService beneficiaryNameCheckService;

    @Autowired
    private CountriesService countriesService;
    
    @Autowired
    private ReceiverPurposeService receiverPurposeService;

    @Override
    public RemittanceDTO createRemittanceObject(String uid) {
        RemittanceDTO remittanceDTO = new RemittanceDTO();
        try {
            if(uid != null) {
                remittanceDTO.setVersion(version);
                remittanceDTO.setPartnerCode(partnerCode);
                remittanceDTO.setRemitterType(TransactionType.I);
                BeneficiaryUser beneficiaryUser = null;
                BeneficiaryAccount beneficiaryAccount = null;
                Users user = null;
                PersonalDetails personalDetails = null;
                PersonalDocuments personalDocuments = null;

                UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);
                LOG.info("createRemittanceObject   Uset ID "+userCalculatorMapping.getUser().getId().toString());
                if(userCalculatorMapping.getUser() != null) {
                    user = usersService.getUserById(userCalculatorMapping.getUser().getId());
                }
                if (user != null) {
                    if(userCalculatorMapping.getBeneficiaryId() != null) {
                        LOG.info("Beneficiary ID  "+userCalculatorMapping.getBeneficiaryId());

                        beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
                        LOG.info("Beneficiary is null  "+beneficiaryUser);
                        
                        
                        beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
                    }

                    personalDetails = personalDetailsService.getLatestByUser(user.getId());
                    personalDocuments = personalDocumentsService.fetchOne(personalDetails);
                    remittanceDTO.setRemitterFullName(personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName());
                    StringBuilder remitterAddress = new StringBuilder();
                    remitterAddress.append(personalDetails.getFlatNumber());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getStreetNumber());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getStreetName());
                    remittanceDTO.setRemitterAddress(remitterAddress.toString());
                    remittanceDTO.setRemitterCountry(personalDetails.getCountry());
                    remittanceDTO.setRemitterIdType(personalDocuments.getDocumentTypes());
                    remittanceDTO.setRemitterIdNumber(personalDocuments.getDocumentNumber());
                    remittanceDTO.setRemitterZipCode(personalDetails.getPostalCode());
                    remittanceDTO.setRemitterCity(personalDetails.getSuburb());
                    remittanceDTO.setRemitterProvince(personalDetails.getProvince());
                    remittanceDTO.setRemitterCountry(personalDetails.getCountry());
                    remittanceDTO.setRemitterPhone(personalDetails.getPhoneNumber());
                    remittanceDTO.setRemitterEmail(user.getEmail());

                    /*remittanceDTO.setBeneficiaryZipCode(beneficiaryUser.getPincode());
                    remittanceDTO.setBeneficiaryCity("ABC");
                    remittanceDTO.setBeneficiaryCountry(countriesService.getById(beneficiaryUser.getCountry()).getCountryCode());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());*/
                    remittanceDTO.setBeneficiaryType(TransactionType.I);
                    remittanceDTO.setBeneFullName(beneficiaryUser.getName());
                    remittanceDTO.setBeneficiaryAddress(beneficiaryUser.getAddress());
                    remittanceDTO.setBeneficiaryAccountNo(beneficiaryAccount.getAccountNumber());
                    remittanceDTO.setBeneficiaryIFSC(beneficiaryAccount.getIfscCode());
                    remittanceDTO.setTransferType(TransferType.IMPS);
                    remittanceDTO.setTransferCurrencyCode(TransferCurrency.INR);
                    remittanceDTO.setBeneficiaryId(beneficiaryUser.getId());
                    remittanceDTO.setTransferAmount(userCalculatorMapping.getTransferAmount());
                    remittanceDTO.setUid(userCalculatorMapping.getUid());
                    remittanceDTO.setRefId(userCalculatorMapping.getRefId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remittanceDTO;
    }

    @Override
    public String startRemitApi(RemittanceDTO remittanceDTO) throws Exception {
        String response = yesBankAPIService.remitTransaction(remittanceDTO);
        return response;
    }

    @Override
    public String startUPIApi(RemittanceDTO remittanceDTO) throws Exception {
        String response = yesBankAPIService.startUPITransaction(remittanceDTO);
        return response;
    }

    @Override
    public String getRemitStatusApi(RemittResponseDTO remittResponseDTO) throws Exception {
        String response = yesBankAPIService.transactionResponse(remittResponseDTO);
        return response;
    }

    @Override
    public String StartUPITransaction(String uid) throws Exception {
        RemittanceDTO remittanceDTO = createRemittanceObject(uid);
        remittanceDTO.setPurposeCode("PC01");
        String response = yesBankAPIService.startUPITransaction(remittanceDTO);
        return response;
    }

    @Override
    public BeneficiaryNameCheck checkBeneficiaryName(String uid) throws Exception {
        RemittanceDTO remittanceDTO = createRemittanceObject(uid);
        
        LOG.info("Checking beneficiary name...");
        remittanceDTO.setTransferAmount(new BigDecimal(1.0));
        
        String purposeCode = getPurposeCodeForGivenTxn(uid);
        
        LOG.info("Setting purpose code..." + purposeCode);
        
        remittanceDTO.setPurposeCode(purposeCode);
        remittanceDTO.setRemitterToBeneficiaryInfo(remittanceDTO.getRemitterFullName()+" - "+remittanceDTO.getBeneficiaryAddress());
        remittanceDTO.setRefId(remittanceDTO.getRefId() + "PD");
        String response = yesBankAPIService.remitTransaction(remittanceDTO);
        
        LOG.info("YES Bank Penny Drop Response:" + response);
        
        JSONParser parser = new JSONParser();
        
        try {
        	LOG.info("Parsing the response..");
        	JSONObject json = (JSONObject) parser.parse(response);
        	LOG.info("JSON response parsed..");
        	
            // Viren - We get error from YesBank API call if the beneficiary account doesn't have activated IMPS
            // In such cases, we don't want to show the white lable error - hence returning null
            if(json == null) {
            	LOG.error("An error occurr while getting the penny drop transaciton. YesBank response:" + response);
            	return null;
            }
            
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonBody = (JSONObject) json.get("Body");
            
        	RemittResponseDTO responseDTO = objectMapper.readValue(jsonBody.get("startRemitResponse").toString(), RemittResponseDTO.class);
            Map<String, String> statusMap = getStatus(responseDTO);

            BeneficiaryNameCheck beneficiaryNameCheck = new BeneficiaryNameCheck();
            boolean isKeyPresent = false;
            Iterator<Map.Entry<String, String> > iterator = statusMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if ("name".equalsIgnoreCase(entry.getKey())) {
                    isKeyPresent = true;
                }
            }
            if(isKeyPresent) {
            	LOG.info("Name from response.." + statusMap.get("name"));
                beneficiaryNameCheck.setNameInResponse(statusMap.get("name"));
            }
            beneficiaryNameCheck.setYesBankAPITransactionStatus(YesBankAPITransactionStatus.valueOf(statusMap.get("status")));
            beneficiaryNameCheck.setBeneficiaryId(remittanceDTO.getBeneficiaryId());
            beneficiaryNameCheck.setUid(remittanceDTO.getUid());
            beneficiaryNameCheck.setUniqueRequestNo(responseDTO.getRequestReferenceNo());
            beneficiaryNameCheck.setUniqueResponseNo(responseDTO.getUniqueResponseNo());
            beneficiaryNameCheck.setAttemptNo(responseDTO.getAttemptNo());
            beneficiaryNameCheck.setYesBankAPINames(YesBankAPINames.START_REMIT);
            beneficiaryNameCheck.setTransferType(TransferType.IMPS);
            beneficiaryNameCheck.setPaymentType(PaymentType.PENNY_DROP);
            LOG.info("updating name check table..");
            beneficiaryNameCheckService.save(beneficiaryNameCheck);
            return beneficiaryNameCheck;
	
        }
        catch(Exception e) {
        	LOG.info("Error occurred.." + e.getMessage());
        	return null;
        }
    }

    private String getPurposeCodeForGivenTxn(String uid) {
    	UserCalculatorMapping userCalcMapping = userCalculatorMappingService.findByUid(uid);
    	LOG.info("Got the usercalc mapping for purpose list" + userCalcMapping);
    	
    	if(userCalcMapping == null) {
    		LOG.info("User calc mapping is null");
    	}
    	
    	ReceiverTransactionModel receiverTransactionModel = receiverPurposeService.getReceiverTypeByUid(userCalcMapping.getId());
    	
    	LOG.info("receiverTransactionModel" + receiverTransactionModel);
    	
    	PurposeListMaster purposeListMaster = receiverPurposeService.getPurposeListById(receiverTransactionModel.getPurposeList());
    	
    	if(purposeListMaster == null) {
    		LOG.info("purposeListMaster is null");
    	}
    	
		return purposeListMaster.getPurposeCode();
	}

	@Override
    public  Map<String,String> getStatus(RemittResponseDTO remittResponseDTO) throws Exception {
        Map<String,String> statusMap = new HashMap<>();
        try {
            String response = yesBankAPIService.transactionResponse(remittResponseDTO);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);
            JSONObject jsonBody = (JSONObject) json.get("Body");
            JSONObject jsonStatus = (JSONObject) jsonBody.get("getStatusResponse");
            JSONObject transactionStatus = (JSONObject) jsonStatus.get("transactionStatus");
            String statusCode = String.valueOf(transactionStatus.get("statusCode"));
            statusMap.put("status", statusCode);
            if (YesBankAPITransactionStatus.COMPLETED.equals(statusCode)) {
                statusMap.put("name", String.valueOf(jsonStatus.get("nameWithBeneficiaryBank")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusMap;
    }

    @Override
    public boolean isBeneficiaryNameMatched(String uid) {
        boolean isMatched = false;
        try {
            LOG.info("UID "+uid);
            BeneficiaryNameCheck beneficiaryNameCheck = this.checkBeneficiaryName(uid);
            if(beneficiaryNameCheck != null && beneficiaryNameCheck.getNameInResponse() != null) {
                BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(beneficiaryNameCheck.getBeneficiaryId());
                if(beneficiaryUser != null) {
                    if(beneficiaryUser.getName().equalsIgnoreCase(beneficiaryNameCheck.getNameInResponse())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMatched;
    }

    @Override
    public UPIValidationResponseDTO getUPIValidationDetails(String upiId) throws Exception{
        String response = yesBankAPIService.getUPIDetails(upiId);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject jsonBody = (JSONObject) json.get("Body");
        ObjectMapper objectMapper = new ObjectMapper();

        UPIValidationResponseDTO responseDTO = objectMapper.readValue(jsonBody.get("ValidateVPAResponse").toString(), UPIValidationResponseDTO.class);
        return responseDTO;
    }
}
