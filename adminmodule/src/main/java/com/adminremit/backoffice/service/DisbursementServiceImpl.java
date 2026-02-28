package com.adminremit.backoffice.service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.backoffice.dto.RemittanceDTO;
import com.adminremit.backoffice.model.CityBankAPITransactionStatus;
import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.backoffice.model.TerraPayAPITransactionStatus;
import com.adminremit.backoffice.model.TransactionType;
import com.adminremit.backoffice.model.TransferCurrency;
import com.adminremit.backoffice.model.TransferType;
import com.adminremit.backoffice.model.YesBankAPINames;
import com.adminremit.backoffice.model.YesBankAPITransactionStatus;
import com.adminremit.backoffice.repository.DisbursementRepository;
import com.adminremit.beneficiary.enums.IFSCCodePrefixEnum;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryNameCheckService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.common.models.Constants;
import com.adminremit.disbursement.api.RemitAPIService;
import com.adminremit.disbursement.api.YesBankAPIService;
import com.adminremit.emails.MailService;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.models.ReceiverType;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.service.ReceiverTypeService;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransactionWorkflow_;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.UserCalculatorMapping_;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.service.TransactionWorkflowService;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.service.PersonalDocumentsService;
import com.adminremit.receipt.FinalReceiptService;
import com.adminremit.receipt.ReceiptService;
import com.adminremit.receiver.model.ReceiverTransactionModel;
import com.adminremit.receiver.service.ReceiverPurposeService;
import com.adminremit.report.model.business.BusinessInfo;
import com.adminremit.report.repo.BusinessInfoRepository;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UserCalculatorMappingService;
import com.adminremit.user.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fynte.api.client.nepal.CityBankAPITransactionStatus;

@Service
public class DisbursementServiceImpl implements DisbursementService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DisbursementServiceImpl.class);
	
	@Autowired
	DisbursementRepository disbursementRepository;
	
	@Autowired
    private UserCalculatorMappingService userCalculatorMappingService;

    @Autowired
    private BeneficiaryUserService beneficiaryUserService;
    

    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;
    
    @Autowired
    private ReceiverTypeService receiverService;
    
    @Autowired
    TransactionWorkflowRepository transactionWorkflowRepository;
    
    @Autowired
    private BusinessInfoRepository businessInfoRepository;

    @Autowired
    private MailService mailService;

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
    @Qualifier("cityService")
    private RemitAPIService cityBankAPIService;
    
    @Autowired
    @Qualifier("terraPayService")
    private RemitAPIService terraPayService;
    
    @Autowired
    private BeneficiaryNameCheckService beneficiaryNameCheckService;
    
    @Autowired
    private TransactionWorkflowService transactionWorkflowService;

    @Autowired
    private ReceiptService receiptService;    
    
    @Autowired
    private FinalReceiptService finalReceiptService;
    
    @Autowired
    private ReceiverPurposeService receiverPurposeService;
    
    @Autowired
    private CountriesRepository countriesRepository;

	public List<UserCalculatorMapping> getAllDisbursementMakerList() {
		Specification<UserCalculatorMapping> result = Specification.where(
                (root, query, builder) -> {
                    final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                    return builder.and(
                            builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.DISBURSEMENT_START),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))
                    );

                }
        );
         return disbursementRepository.findAll(result);
    }

	@Override
	public List<UserCalculatorMapping> getAllDisbursementCheckerList() {
		Specification<UserCalculatorMapping> result = Specification.where(
                (root, query, builder) -> {
                    final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                    return builder.or(
                    		builder.and(builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.DISBURSEMENT_CHECK),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))),
                    		builder.and(builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.REFUND_START),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))),
                            builder.and(builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.BACKOFFICE_CANCEL_CONFIRMED),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted)))
                    );
                }
        );
		
		List<UserCalculatorMapping>  userCalculatorMappingList = disbursementRepository.findAll(result);		 
		return populateCurrentActionStatus(userCalculatorMappingList);
	}
	
	public List<UserCalculatorMapping> getAllSentToBankListForNepal() {
		Specification<UserCalculatorMapping> result = Specification.where(
                (root, query, builder) -> {
                    final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                    return builder.and(
                    		builder.and(builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.SENT_TO_BENEFICIARY),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted)))    
                    		
                    );
                }
        );
		
		
		List<UserCalculatorMapping>  userCalculatorMappingList = disbursementRepository.findAll(result);	
		return userCalculatorMappingList;
	}

	private List<UserCalculatorMapping> populateCurrentActionStatus(List<UserCalculatorMapping> userCalculatorMappingList) {
		
		userCalculatorMappingList.stream()
								 .forEach(u->u.setCurrentActionStatus(getActionStatus(u)));
		
		return userCalculatorMappingList;
	}

	private String getActionStatus(UserCalculatorMapping u) {

		Optional<TransactionWorkflow> transactionWorkflow = u.getTransactionWorflow().stream()
																				.filter(t->t.isCompleted() == false && (t.getWorkflowStatus().equals(WorkflowStatus.DISBURSEMENT_CHECK) || t.getWorkflowStatus().equals(WorkflowStatus.REFUND_START)))
																				.findAny();
		
		if(transactionWorkflow.isPresent()) {
			if(transactionWorkflow.get().getActionStatus() != null)
				return transactionWorkflow.get().getActionStatus();
			else
				return "Refund";
		}
		
		return "";
	}
	
	@Override
	public BeneficiaryNameCheck startDisbursement(String uid, UserCalculatorMapping userCalcMapping, String transferType) throws Exception {
		
		switch(userCalcMapping.getToCountryCode()) {
		case "IN":
			return startDisbursementIndia(uid, userCalcMapping, transferType);
		case "NP":
			startDisbursementNepal(uid, userCalcMapping, transferType);
			break;
		case "VN":
		case "LK":
		case "FR":
		case "KR":
		case "UK":
		case "CN":
		case "TH":
			startDisbursementWithTerraPay(uid, userCalcMapping, transferType);
			break;
		}
			
		return null;
	}

	
	public BeneficiaryNameCheck startDisbursementIndia(String uid, UserCalculatorMapping userCalcMapping, String transferType) throws Exception {
		LOG.info("Transfer Type for Reference Number "+userCalcMapping.getRefId()+" is "+transferType);
		
		RemittanceDTO remittanceDTO = createRemittanceObject(uid, transferType);
		//remittanceDTO.setTransferAmount(remittanceDTO.getTransferAmount());
		// Viren: TODO - Check UPI SOAP request - remove any hard coded customer data.
		
		String purposeCode = getPurposeCodeForGivenTxn(uid);
		LOG.info("UID:::"+ uid +" Purpose Code::: "+purposeCode);
		remittanceDTO.setPurposeCode(purposeCode);
        //remittanceDTO.setRemitterToBeneficiaryInfo(remittanceDTO.getRemitterFullName()+" - "+remittanceDTO.getBeneficiaryAddress());
        String response = "";
        
        if(Constants.UPI_TRANSFER_TYPE.equalsIgnoreCase(transferType)) {
        	 response = yesBankAPIService.startUPITransaction(remittanceDTO);
             LOG.info("Response from Yes bank for disbursement approval of UPI transaction::: "+userCalcMapping.getRefId()+" is "+response);
        }else {
        	response = yesBankAPIService.remitTransaction(remittanceDTO);
            LOG.info("Response from Yes bank for disbursement approval of Bank Account Deposit transaction::: "+userCalcMapping.getRefId()+" is "+response);
        }
        
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonBody = (JSONObject) json.get("Body");
        if(jsonBody.containsKey("Fault"))
        	throw new Exception(response);
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
            beneficiaryNameCheck.setNameInResponse(statusMap.get("name"));
        }
        beneficiaryNameCheck.setYesBankAPITransactionStatus(YesBankAPITransactionStatus.valueOf(statusMap.get("status")));
        beneficiaryNameCheck.setBeneficiaryId(remittanceDTO.getBeneficiaryId());
        beneficiaryNameCheck.setUid(remittanceDTO.getUid());
        beneficiaryNameCheck.setUniqueRequestNo(responseDTO.getRequestReferenceNo());
        beneficiaryNameCheck.setUniqueResponseNo(responseDTO.getUniqueResponseNo());
        beneficiaryNameCheck.setAttemptNo(responseDTO.getAttemptNo());
        beneficiaryNameCheck.setYesBankAPINames(YesBankAPINames.START_REMIT);
        beneficiaryNameCheck.setTransferType(TransferType.valueOf(responseDTO.getReqTransferType()));
        beneficiaryNameCheck.setPaymentType(PaymentType.DISBURSEMENT_APPROVE);
        
        beneficiaryNameCheckService.save(beneficiaryNameCheck);
        
        sendEmailOnTxnCompletion(statusMap, userCalcMapping);
        updateTransactionWorkflow(statusMap, userCalcMapping,false);
        
		return beneficiaryNameCheck;
	}
	
	public void startDisbursementNepal(String uid, UserCalculatorMapping userCalcMapping, String transferType) throws Exception {
		LOG.info("startDisbursementNepal "+userCalcMapping.getRefId()+" is "+transferType);
		Map<String, String> statusMap =cityBankAPIService.initiateTransaction(userCalcMapping);
		
		if( !CityBankAPITransactionStatus.INITIATED.getTransactionStatus().equals(statusMap.get("status"))) {
			throw new Exception("Failed with "+statusMap.get("status")+ "\t message: "+statusMap.get("message"));
		}
		updateTransactionWorkflow(statusMap, userCalcMapping,false);   
        sendEmailOnTxnCompletion(statusMap, userCalcMapping);
        if (statusMap != null && statusMap.get("status")!=null  && userCalcMapping.getToCountryCode().equals("NP") && CityBankAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status"))){
        	transactionWorkflowService.updateTransactionWorkFlow(userCalcMapping, WorkflowStatus.COMPLETED);
        }
        
	}
	
	public void startDisbursementWithTerraPay(String uid, UserCalculatorMapping userCalcMapping, String transferType) throws Exception {
		LOG.info("startDisbursementNepal "+userCalcMapping.getRefId()+" is "+transferType);
		Map<String, String> statusMap =terraPayService.initiateTransaction(userCalcMapping);
		
		if( TerraPayAPITransactionStatus.CANCEL.getTransactionStatus().equals(statusMap.get("status"))
				|| TerraPayAPITransactionStatus.RETRY.getTransactionStatus().equals(statusMap.get("status"))) {
			throw new Exception("Failed with "+statusMap.get("status")+ "\t message: "+statusMap.toString());
		}
		updateTransactionWorkflow(statusMap, userCalcMapping,true);   
        sendEmailOnTxnCompletion(statusMap, userCalcMapping);
        if (statusMap != null && statusMap.get("status")!=null  && TerraPayAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status"))){
        	transactionWorkflowService.updateTransactionWorkFlow(userCalcMapping, WorkflowStatus.COMPLETED);
        }
        
	}
	
	
	
	
	private String getPurposeCodeForGivenTxn(String uid) {
    	UserCalculatorMapping userCalcMapping = userCalculatorMappingService.findByUid(uid);
    	ReceiverTransactionModel receiverTransactionModel = receiverPurposeService.getReceiverTypeByUid(userCalcMapping.getId());
    	PurposeListMaster purposeListMaster = receiverPurposeService.getPurposeListById(receiverTransactionModel.getPurposeList());
    	
		return purposeListMaster.getPurposeCode();
	}

	private void sendEmailOnTxnCompletion(Map<String, String> statusMap, UserCalculatorMapping userCalcMapping) {
		if(statusMap != null && statusMap.get("status")!=null 
				&& ((userCalcMapping.getToCountryCode().equals("IN") && statusMap.get("status").equals(YesBankAPITransactionStatus.COMPLETED.getTransactionStatus()))
				|| (userCalcMapping.getToCountryCode().equals("NP") && CityBankAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status")))
				|| TerraPayAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status"))
				)) {
		    try {
		    	LOG.info("Sending completion email to the user ... " + userCalcMapping.getUser().getEmail() + " for txn ..." + userCalcMapping.getRefId());
				finalReceiptService.sendEmailOnTransactionComplete(userCalcMapping.getUser().getEmail(), userCalcMapping);
				LOG.info("Txn completion email sent successfully to " + userCalcMapping.getUser().getEmail() + " for txn ..." + userCalcMapping.getRefId());
			} catch (Exception e) {
				 throw new RuntimeException("Unable to send transaction completion email for reference id===>"+userCalcMapping.getRefId(), e);
			} 
        }		
	}

	private void updateTransactionWorkflow(Map<String, String> statusMap, UserCalculatorMapping userCalculatorMapping, boolean instantPaid) {
		
		
		switch(userCalculatorMapping.getToCountryCode()) {
		case "IN":
			if (statusMap != null && statusMap.get("status")!=null && statusMap.get("status").equals(Constants.YES_BANK_NEFT_RESPONSE_IN_PROCESS_STATUS_CODE)) {
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BANK);		
			}else if(statusMap != null && statusMap.get("status")!=null && statusMap.get("status").equals(Constants.FT_UPI_COMPLETE_TRANSACTION_STATUS)){
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.COMPLETED);
			}
			break;
		case "NP":
			if( CityBankAPITransactionStatus.INITIATED.getTransactionStatus().equals(statusMap.get("status"))) {
				userCalculatorMapping.setPinno(statusMap.get("PINNO"));
				userCalculatorMappingService.save(userCalculatorMapping);
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				//send instruction mail//
				sendInstructionMail(userCalculatorMapping);
				if(userCalculatorMapping.getReceiveMode().equals("CAS")) {
					sendInstructionMailForCash(userCalculatorMapping);	
				}
			}else if( CityBankAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status"))) {
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.COMPLETED);
			}else if( CityBankAPITransactionStatus.CANCEL.getTransactionStatus().equals(statusMap.get("status"))) {
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.CANCELLED);
			}

			break;
		case "VN":
		case "LK":
		case "FR":
		case "KR":
		case "UK":
		case "CN":
		case "TH":
			if( TerraPayAPITransactionStatus.INITIATED.getTransactionStatus().equals(statusMap.get("status"))) {
				userCalculatorMapping.setPinno(statusMap.get("terraPayReference"));
				userCalculatorMappingService.save(userCalculatorMapping);
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				//send instruction mail//
				sendInstructionMail(userCalculatorMapping);
				if(userCalculatorMapping.getReceiveMode().equals("CAS")) {
					sendInstructionMailForCash(userCalculatorMapping);	
				}
			}else if( TerraPayAPITransactionStatus.PAID.getTransactionStatus().equals(statusMap.get("status"))) {
				if(instantPaid) {
					userCalculatorMapping.setPinno(statusMap.get("terraPayReference"));
					userCalculatorMappingService.save(userCalculatorMapping);
					userCalculatorMapping.setPinno(statusMap.get("terraPayReference"));
					userCalculatorMappingService.save(userCalculatorMapping);
					transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
					transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
					//send instruction mail//
					sendInstructionMail(userCalculatorMapping);
					if(userCalculatorMapping.getReceiveMode().equals("CAS")) {
						sendInstructionMailForCash(userCalculatorMapping);	
					}
				}
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.COMPLETED);
			}else if( TerraPayAPITransactionStatus.CANCEL.getTransactionStatus().equals(statusMap.get("status"))) {
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.CANCELLED);
			}
			break;
		}
		
	}

	private RemittanceDTO createRemittanceObject(String uid, String transferType) {
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);
		if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null && userCalculatorMapping.getUser().getUserType()!=null 
				&& userCalculatorMapping.getUser().getUserType().equalsIgnoreCase("PERSONAL")) {
			return createRemittanceObjectForPersonalUser(uid, transferType);
		}else {
			return createRemittanceObjectForBusinessUser(uid, transferType);
		}
		
	}

	private RemittanceDTO createRemittanceObjectForPersonalUser(String uid, String transferType) {
		RemittanceDTO remittanceDTO = new RemittanceDTO();
		
		try {
            if(uid != null) {
                remittanceDTO.setVersion(version);
                remittanceDTO.setPartnerCode(partnerCode);
                remittanceDTO.setRemitterType(TransactionType.I);
                BeneficiaryUser beneficiaryUser = null;
                BeneficiaryAccount beneficiaryAccount = null;
                Users users = null;
                PersonalDetails personalDetails = null;
                PersonalDocuments personalDocuments = null;
                

                UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);
                LOG.info("createRemittanceObject   Uset ID "+userCalculatorMapping.getUser().getId().toString());
                if(userCalculatorMapping.getUser() != null) {
                    users = usersService.getUserById(userCalculatorMapping.getUser().getId());
                }
                if (users != null) {
                    if(userCalculatorMapping.getBeneficiaryId() != null) {
                        LOG.info("Beneficiary ID  "+userCalculatorMapping.getBeneficiaryId());

                        beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
                        LOG.info("Beneficiary is "+beneficiaryUser.getId() + "," + beneficiaryUser.getFullName());
                        
                        if(beneficiaryUser!=null) {
                        	beneficiaryAccount = beneficiaryAccountService.getByBeneficiaryUser(beneficiaryUser);
                        	LOG.info("Beneficiary Account is "+beneficiaryAccount.getAccountNumber());
                        }

                        if(users.getType() != null && users.getType().toLowerCase().equals("business")) {
                        	
                        	// Compare the type of the beneficiary 
                        	List<ReceiverType> receivers =	receiverService.getAllReceiverType();
                        	
                        	for(int index = 0; index < receivers.size();index++) {
                        		ReceiverType receiver = receivers.get(index);
                        		if(receiver!=null && beneficiaryUser.getReceiverType()!=null && beneficiaryUser.getReceiverType().equals(receiver.getId())){
                        			if(receiver.getReceiverDesc().equals("MY_BUSINESS") || receiver.getReceiverDesc().equals("ANOTHER_BUSINESS") || receiver.getReceiverDesc().equals("OTHERS")) {
                        				remittanceDTO.setRemitterType(TransactionType.C);
                        				break;
                        			}
                        		}
                        	}
                        }
                    }
                    personalDetails = personalDetailsService.getLatestByUser(users.getId());
                    personalDocuments = personalDocumentsService.fetchOne(personalDetails);
                    remittanceDTO.setRemitterFullName(personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName());
                    StringBuilder remitterAddress = new StringBuilder();
                    remitterAddress.append(personalDetails.getFlatNumber());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getStreetNumber());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getStreetName());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getStreetType());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getSuburb());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getProvince());
                    remitterAddress.append(",");
                    remitterAddress.append(personalDetails.getPostalCode());
                    
                    LOG.info("======================================");
                    LOG.info("Address: " + remitterAddress.toString());
                    LOG.info("Country: " + personalDetails.getCountry());
                    LOG.info("Document Type: " + personalDocuments.getDocumentTypes());
                    LOG.info("Document Number: " + personalDocuments.getDocumentNumber());
                    LOG.info("Postal Code: " + personalDetails.getPostalCode());
                    LOG.info("Suburb: " + personalDetails.getSuburb());
                    LOG.info("Province: " + personalDetails.getProvince());
                    LOG.info("Phone number: " + personalDetails.getPhoneNumber());
                    LOG.info("Email: " + users.getEmail());
                    LOG.info("Bene full name:" + beneficiaryUser.getName());
                    LOG.info("Bene Account number:" + beneficiaryAccount.getAccountNumber());
                    LOG.info("Bene IFSC code:" + beneficiaryAccount.getIfscCode());
                    
                    LOG.info("======================================");
                    
                    remittanceDTO.setRemitterAddress(remitterAddress.toString());
                    remittanceDTO.setRemitterCountry(personalDetails.getCountry());
                    remittanceDTO.setRemitterIdType(personalDocuments.getDocumentTypes());
                    remittanceDTO.setRemitterIdNumber(personalDocuments.getDocumentNumber());
                    remittanceDTO.setRemitterZipCode(personalDetails.getPostalCode());
                    remittanceDTO.setRemitterCity(personalDetails.getSuburb());
                    remittanceDTO.setRemitterProvince(personalDetails.getProvince());
                    //remittanceDTO.setRemitterCountry(personalDetails.getCountry());
                    remittanceDTO.setRemitterPhone(personalDetails.getPhoneNumber());
                    remittanceDTO.setRemitterEmail(users.getEmail());

                    /*remittanceDTO.setBeneficiaryZipCode(beneficiaryUser.getPincode());
                    remittanceDTO.setBeneficiaryCity("ABC");
                    remittanceDTO.setBeneficiaryCountry(countriesService.getById(beneficiaryUser.getCountry()).getCountryCode());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());*/
                    remittanceDTO.setBeneficiaryType(TransactionType.I);
                    remittanceDTO.setBeneFullName(beneficiaryUser.getName());
                    //remittanceDTO.setBeneficiaryAddress(beneficiaryUser.getAddress());
                    remittanceDTO.setBeneficiaryAddress(setBeneficiaryAddress(beneficiaryUser));
                    remittanceDTO.setBeneficiaryAccountNo(beneficiaryAccount.getAccountNumber());
                    remittanceDTO.setBeneficiaryIFSC(beneficiaryAccount.getIfscCode());
                    setTransferType(remittanceDTO, transferType);
                    remittanceDTO.setTransferCurrencyCode(TransferCurrency.INR);
                    remittanceDTO.setBeneficiaryId(beneficiaryUser.getId());
                    
                    remittanceDTO.setTransferAmount(userCalculatorMapping.getTotalConvertedValue());
                	remittanceDTO.setRefId(userCalculatorMapping.getRefId());
                	remittanceDTO.setUid(userCalculatorMapping.getUid());
                }
            }
        } catch (Exception e) {
        	LOG.info("Error occurred while preparing yes bank remittance object" + e.getMessage());
        	LOG.info(e.getStackTrace().toString());
            e.printStackTrace();
        }
        return remittanceDTO;
	}
	
	private RemittanceDTO createRemittanceObjectForBusinessUser(String uid, String transferType) {
		RemittanceDTO remittanceDTO = new RemittanceDTO();
		
		try {
            if(uid != null) {
                remittanceDTO.setVersion(version);
                remittanceDTO.setPartnerCode(partnerCode);
                remittanceDTO.setRemitterType(TransactionType.I);
                BeneficiaryUser beneficiaryUser = null;
                BeneficiaryAccount beneficiaryAccount = null;
                Users users = null;
                PersonalDetails personalDetails = null;
                PersonalDocuments personalDocuments = null;
                BusinessInfo  businessInfo = null;

                UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);
                LOG.info("createRemittanceObject   User ID "+userCalculatorMapping.getUser().getId().toString());
                if(userCalculatorMapping.getUser() != null) {
                    users = usersService.getUserById(userCalculatorMapping.getUser().getId());
                }
                if (users != null) {
                    if(userCalculatorMapping.getBeneficiaryId() != null) {
                        LOG.info("Beneficiary ID  "+userCalculatorMapping.getBeneficiaryId());

                        beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
                        LOG.info("Beneficiary is "+beneficiaryUser.getId() + "," + beneficiaryUser.getFullName());
                        
                        if(beneficiaryUser!=null) {
                        	beneficiaryAccount = beneficiaryAccountService.getByBeneficiaryUser(beneficiaryUser);
                        	LOG.info("Beneficiary Account is "+beneficiaryAccount.getAccountNumber());
                        }
                        
                        businessInfo = businessInfoRepository.findOneByUserId(users.getId());
                       
                        if(users.getType() != null && users.getType().toLowerCase().equals("business")) {
                        	
                        	// Compare the type of the beneficiary 
                        	List<ReceiverType> receivers =	receiverService.getAllReceiverType();
                        	
                        	for(int index = 0; index < receivers.size();index++) {
                        		ReceiverType receiver = receivers.get(index);
                        		if(receiver!=null && beneficiaryUser.getReceiverType()!=null && beneficiaryUser.getReceiverType().equals(receiver.getId())){
                        			if(receiver.getReceiverDesc().equals("MY_BUSINESS") || receiver.getReceiverDesc().equals("ANOTHER_BUSINESS") || receiver.getReceiverDesc().equals("OTHERS")) {
                        				remittanceDTO.setRemitterType(TransactionType.C);
                        				break;
                        			}
                        		}
                        	}
                        }
                    }
                    personalDetails = personalDetailsService.getLatestByUser(users.getId());
                    personalDocuments = personalDocumentsService.fetchOne(personalDetails);
                    StringBuilder remitterAddress = new StringBuilder();
                    
                    if(businessInfo!=null) {
                    	remittanceDTO.setRemitterFullName(businessInfo.getCompanyName());
                    	
                    	remitterAddress.append(businessInfo.getRegisteredUnitNumber());
                        remitterAddress.append(",");
                        remitterAddress.append(businessInfo.getRegisteredStreetNumber());
                        remitterAddress.append(",");
                        remitterAddress.append(businessInfo.getRegisteredStreetName());
                        remitterAddress.append(",");
                        remitterAddress.append(businessInfo.getRegisteredSuburb());
                        remitterAddress.append(",");
                        remitterAddress.append(businessInfo.getRegisteredState());
                        remitterAddress.append(",");
                        remitterAddress.append(businessInfo.getRegisteredPostCode());     
                        
                        LOG.info("======================================");
                        LOG.info("Address: " + remitterAddress.toString());
                        LOG.info("Country: " + businessInfo.getRegisteredCountry());
                        LOG.info("Postal Code: " + personalDetails.getPostalCode());
                        LOG.info("Suburb: " + personalDetails.getSuburb());
                        
                        remittanceDTO.setRemitterAddress(remitterAddress.toString());
                        
                        Countries countries = countriesRepository.findByIsoCode(businessInfo.getRegisteredCountry());
                        
                        if(countries!=null) {
                        	remittanceDTO.setRemitterCountry(countries.getCountryCode());
                        }
                        
                        remittanceDTO.setRemitterDocumentType(null);
                        remittanceDTO.setRemitterIdNumber(businessInfo.getAbn());
                    }
                    
                    
                    
                    LOG.info("Document Type: " + personalDocuments.getDocumentTypes());
                    LOG.info("Document Number: " + personalDocuments.getDocumentNumber());
                    LOG.info("Email: " + users.getEmail());
                    LOG.info("Bene full name:" + beneficiaryUser.getName());
                    LOG.info("Bene Account number:" + beneficiaryAccount.getAccountNumber());
                    LOG.info("Bene IFSC code:" + beneficiaryAccount.getIfscCode());
                    
                    LOG.info("======================================");                  
                    
                    
                    remittanceDTO.setRemitterZipCode(personalDetails.getPostalCode());
                    remittanceDTO.setRemitterCity(personalDetails.getSuburb());
                    remittanceDTO.setRemitterProvince(personalDetails.getProvince());
                    //remittanceDTO.setRemitterCountry(personalDetails.getCountry());
                    remittanceDTO.setRemitterPhone(personalDetails.getPhoneNumber());
                    remittanceDTO.setRemitterEmail(users.getEmail());

                    /*remittanceDTO.setBeneficiaryZipCode(beneficiaryUser.getPincode());
                    remittanceDTO.setBeneficiaryCity("ABC");
                    remittanceDTO.setBeneficiaryCountry(countriesService.getById(beneficiaryUser.getCountry()).getCountryCode());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());
                    remittanceDTO.setBeneficiaryMobile(beneficiaryUser.getMobileNumber());*/
                    remittanceDTO.setBeneficiaryType(TransactionType.C);
                    remittanceDTO.setBeneFullName(beneficiaryUser.getName());
                    //remittanceDTO.setBeneficiaryAddress(beneficiaryUser.getAddress());
                    remittanceDTO.setBeneficiaryAddress(setBeneficiaryAddress(beneficiaryUser));
                    remittanceDTO.setBeneficiaryAccountNo(beneficiaryAccount.getAccountNumber());
                    remittanceDTO.setBeneficiaryIFSC(beneficiaryAccount.getIfscCode());
                    setTransferType(remittanceDTO, transferType);
                    remittanceDTO.setTransferCurrencyCode(TransferCurrency.INR);
                    remittanceDTO.setBeneficiaryId(beneficiaryUser.getId());
                    
                    remittanceDTO.setTransferAmount(userCalculatorMapping.getTotalConvertedValue());
                	remittanceDTO.setRefId(userCalculatorMapping.getRefId());
                	remittanceDTO.setUid(userCalculatorMapping.getUid());
                }
            }
        } catch (Exception e) {
        	LOG.info("Error occurred while preparing yes bank remittance object" + e.getMessage());
        	LOG.info(e.getStackTrace().toString());
            e.printStackTrace();
        }
        return remittanceDTO;
	}
	
	private String setBeneficiaryAddress(BeneficiaryUser beneficiaryUser) {
		StringBuilder beneficiaryAddress = new StringBuilder();
		
		if(beneficiaryUser!=null) {
			beneficiaryAddress.append(beneficiaryUser.getAddress());
			beneficiaryAddress.append(",");
			beneficiaryAddress.append(beneficiaryUser.getAddress2());
		}
		return beneficiaryAddress.toString();
	}

	private void setTransferType(RemittanceDTO remittanceDTO, String transferType) {
		if(transferType!=null && Constants.UPI_TRANSFER_TYPE.equals(transferType)) {
			remittanceDTO.setTransferType(TransferType.UPI);			
		}else if(remittanceDTO!=null && remittanceDTO.getBeneficiaryIFSC()!=null) {
			String ifscCodePrefix = remittanceDTO.getBeneficiaryIFSC().substring(0, 4);
			
			if(IFSCCodePrefixEnum.YES_BANK.getIfscPrefix().equalsIgnoreCase(ifscCodePrefix)){
				remittanceDTO.setTransferType(TransferType.FT);
			}else {
				remittanceDTO.setTransferType(TransferType.NEFT);
			}
		}
	}

	@Override
    public  Map<String,String> getStatus(RemittResponseDTO remittResponseDTO) throws Exception {
        Map<String,String> statusMap = new HashMap<>();
        
        try {
            String response = yesBankAPIService.transactionResponse(remittResponseDTO);
            LOG.info("Yes Bank Status Response for Reference Number:::::"+remittResponseDTO.getRequestReferenceNo() + "====>" +response);
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);
            JSONObject jsonBody = (JSONObject) json.get("Body");
            JSONObject jsonStatus = (JSONObject) jsonBody.get("getStatusResponse");
            JSONObject transactionStatus = (JSONObject) jsonStatus.get("transactionStatus");
            
            String transactionDate = String.valueOf(jsonStatus.get("transactionDate"));
            String statusCode = String.valueOf(transactionStatus.get("statusCode"));
            String subStatusCode = String.valueOf(transactionStatus.get("subStatusCode"));
            String subStatusText = String.valueOf(transactionStatus.get("subStatusText"));
            String bankReferenceNo = String.valueOf(transactionStatus.get("bankReferenceNo"));
            
            LOG.info("Get status - " +  statusCode +", " + bankReferenceNo + ", "+ transactionDate);
            statusMap.put("status", statusCode);
            statusMap.put("bankReferenceNo", bankReferenceNo);
            statusMap.put("transactionDate", transactionDate);
            statusMap.put("subStatusCode", subStatusCode);
            statusMap.put("subStatusText", subStatusText);
            
            if (YesBankAPITransactionStatus.COMPLETED.getTransactionStatus().equals(statusCode)) {
                statusMap.put("name", String.valueOf(jsonStatus.get("nameWithBeneficiaryBank")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusMap;
    }

	@Override
	public void checkAndUpdateDisbursementStatus() throws Exception {
		try {
		checkAndUpdateDisbursementStatusIndia();
		}catch (Exception e) {
			LOG.error("failed during auto update for India",e);
		}		
		checkAndUpdateDisbursementStatusOthers();
	}
	
	public void checkAndUpdateDisbursementStatusIndia() throws Exception {
		List<BeneficiaryNameCheck> beneficiaryNameCheckList = beneficiaryNameCheckService.findByDisbursementStatusAndPaymentType(YesBankAPITransactionStatus.IN_PROCESS, PaymentType.DISBURSEMENT_APPROVE);
		LOG.info("Size of Beneficiary Name Check with Status IN_PROCESS::"+(beneficiaryNameCheckList!=null ? beneficiaryNameCheckList.size() : 0));
		
		if(beneficiaryNameCheckList!=null && !beneficiaryNameCheckList.isEmpty()) {
			beneficiaryNameCheckList.parallelStream()
									.forEach(b->updateTransactionStatus(b));
		}		
	}
	
	public void checkAndUpdateDisbursementStatusOthers() throws Exception {
		List<UserCalculatorMapping>	requestList =getAllSentToBankListForNepal();	
		if(requestList!=null && !requestList.isEmpty()) {
			//AJ: Specification was not working to filter based on toCountryCode, hence used java filter. In future this needs to be changed.
			List<UserCalculatorMapping>	nonIndianRequestList= requestList.stream().filter(user -> !user.getToCountryCode().equals("IN")).collect(Collectors.toList());
			if(nonIndianRequestList!=null && !nonIndianRequestList.isEmpty()) {
				LOG.info("Size of transaction for Non Indian with status IN_PROCESS::"+nonIndianRequestList.size());
				nonIndianRequestList.parallelStream().forEach(b->updateTransactionStatusOthers(b));
			}
		}	
	}
	
	
	
	
	
	
	
	private void updateTransactionStatusOthers(UserCalculatorMapping b) {
		// TODO Auto-generated method stub
		try {
			LOG.error("Auto status update initiated for "+b.getRefId());
			Map<String,String> result = null;
			switch(b.getToCountryCode()) {
			case "NP":
				result =cityBankAPIService.getStatus(b.getRefId());
				break;
			case "VN":
			case "LK":
			case "FR":
			case "KR":
			case "UK":
			case "CN":
			case "TH":
				result =terraPayService.getStatus(b.getRefId());
				break;
			}
			if(result !=null) {
				updateTransactionWorkflow(result, b,false);
				sendEmailOnTxnCompletion(result, b);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Failed during auto status update for "+b.getRefId(), e);
		}
	}

	@Override
	public void checkAndUpdatepennyDropStatus() throws Exception {
		List<BeneficiaryNameCheck> beneficiaryNameCheckList = beneficiaryNameCheckService.findByDisbursementStatusAndPaymentType(YesBankAPITransactionStatus.IN_PROCESS, PaymentType.PENNY_DROP);
		LOG.info("Size of Beneficiary Name Check with Status IN_PROCESS for Penny drop::"+(beneficiaryNameCheckList!=null ? beneficiaryNameCheckList.size() : 0));
		
		if(beneficiaryNameCheckList!=null && !beneficiaryNameCheckList.isEmpty()) {
			beneficiaryNameCheckList.parallelStream()
									.forEach(b->updateTransactionStatus(b));
		}		
	}
	
	
	
	@Override
	public void checkAndCompleteDisbursementStatus() throws Exception {
		List<BeneficiaryNameCheck> beneficiaryNameCheckList = beneficiaryNameCheckService.findByDisbursementStatusAndPaymentType(YesBankAPITransactionStatus.SENT_TO_BENEFICIARY, PaymentType.DISBURSEMENT_APPROVE);
		LOG.info("Size of Beneficiary Name Check with Status SENT_TO_BENEFICIARY::"+(beneficiaryNameCheckList!=null ? beneficiaryNameCheckList.size() : 0));
		
		if(beneficiaryNameCheckList!=null && !beneficiaryNameCheckList.isEmpty()) {
			beneficiaryNameCheckList.parallelStream()
									.forEach(b->updateTransactionStatusToCompleted(b));
		}		
	}
	
	@Override
	public String getTransactionStatus(String referenceId) throws Exception {
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(referenceId);
		switch(userCalculatorMapping.getToCountryCode()) {
		case "IN":
			return yesBankAPIService.getStatus(referenceId);
		case "NP":
			return cityBankAPIService.getStatus(referenceId).toString();			
		case "VN":
		case "LK":
		case "FR":
		case "KR":
		case "UK":
		case "CN":
		case "TH":
			return terraPayService.getStatus(referenceId).toString();			
		}
			
		return "Invalid reference id";
	}
	
	@Override
	public String getBalance() throws Exception {
		return yesBankAPIService.getBalance();
	}
	
	@Override
	public void checkAndSendReminder() throws Exception {
		LOG.info("Getting txn for sending reminders::");
		DateTime startDate = DateTime.now().plusHours(-24);
		DateTime endDate = DateTime.now().plusHours(-25);
		List<TransactionWorkflow> twf = transactionWorkflowRepository.findAllStagingTransactions(startDate.toDate(), endDate.toDate());
		
		for(int i=0;i<twf.size();i++) {
			String email = twf.get(i).getUserCalculatorMapping().getUser().getEmail();
			String paymentMethod = twf.get(i).getUserCalculatorMapping().getPaymentMode();	
			String sendAmount = twf.get(i).getUserCalculatorMapping().getFromCurrencyCode() + " " + twf.get(i).getUserCalculatorMapping().getTransferAmount();
			String referenceId = twf.get(i).getUserCalculatorMapping().getRefId();
			String bookingDate = twf.get(i).getCreateAt().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));;
			
			final Map<String, Object> messageValueMap = new HashMap<>();
            String subject = "Awaiting Funds";
            LOG.info("Sending email for Awaiting Funds..." + referenceId);
            String templateName = "awaiting_funds_bank_transfer_15";
            
            if(paymentMethod.toLowerCase().equals("pid") || paymentMethod.toLowerCase().equals("payid")) {
            	templateName = "awaiting_funds_payid_17";
            }
            
            messageValueMap.put("remitter_name",getFirstName(twf.get(i).getUserCalculatorMapping().getUser().getId()));
            messageValueMap.put("refrence_number",referenceId);
            messageValueMap.put("send_amount", sendAmount);
            messageValueMap.put("booking_date", bookingDate);
            
            messageValueMap.put("account_name", "XDC Payments Pty Ltd");
            messageValueMap.put("account_number", "319627648");
            messageValueMap.put("bsb_code", "802-985");
            
            try {
            	mailService.sendEmailHtml(email,subject,templateName,messageValueMap);	
            }
            catch(Exception ex) {
            	LOG.info("Awaiting Funds email sending failed for txn ref id:" + sendAmount);
            	LOG.info("Error message:" + ex.getMessage());
            }
            
		}
 	}
	
	private String getFirstName(Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
        return personalDetails.getFullName();
    }
	
	private String getFUllName(Long userId) {
		 PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
	        return personalDetails.getFullName()+" "+personalDetails.getLastName();
	}
	
	private void updateTransactionStatusToCompleted(BeneficiaryNameCheck beneficiaryNameCheck) {
		RemittResponseDTO remittResponseDTO = new RemittResponseDTO();
		Map<String, String> statusMap = null;
		
		remittResponseDTO.setAttemptNo(beneficiaryNameCheck.getAttemptNo());
		remittResponseDTO.setPartnerCode(partnerCode);
		remittResponseDTO.setReqTransferType(beneficiaryNameCheck.getTransferType().name());
		remittResponseDTO.setRequestReferenceNo(beneficiaryNameCheck.getUniqueRequestNo());
		remittResponseDTO.setStatusCode(beneficiaryNameCheck.getYesBankAPITransactionStatus().name());
		remittResponseDTO.setUniqueResponseNo(beneficiaryNameCheck.getUniqueResponseNo());
				
		try {
			LOG.info("Getting status information by calling YB API 2...");
			statusMap = getStatus(remittResponseDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updateBeneficiaryNameCheckDetailsToCompleted(beneficiaryNameCheck, statusMap);
	}
	
	private void updateTransactionStatus(BeneficiaryNameCheck beneficiaryNameCheck) {
		RemittResponseDTO remittResponseDTO = new RemittResponseDTO();
		Map<String, String> statusMap = null;
		
		remittResponseDTO.setAttemptNo(beneficiaryNameCheck.getAttemptNo());
		remittResponseDTO.setPartnerCode(partnerCode);
		remittResponseDTO.setReqTransferType(beneficiaryNameCheck.getTransferType().name());
		remittResponseDTO.setRequestReferenceNo(beneficiaryNameCheck.getUniqueRequestNo());
		remittResponseDTO.setStatusCode(beneficiaryNameCheck.getYesBankAPITransactionStatus().name());
		remittResponseDTO.setUniqueResponseNo(beneficiaryNameCheck.getUniqueResponseNo());
				
		try {
			LOG.info("Getting status information by calling YB API...");
			statusMap = getStatus(remittResponseDTO);
			
			/*if(statusMap.containsKey("name")) {
			    BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(beneficiaryNameCheck.getBeneficiaryId());
			    Users  user = usersService.getUserById(beneficiaryUser.getUser());
                final Map<String, Object> messageValueMap = new HashMap<>();
                String subject = "Funds Credited to your recipient account";
                LOG.info("Sending email for funds credited..." + getFullName(user.getId()));
                String templateName = "funds_credited_4";
                messageValueMap.put("remitter_name",getFullName(user.getId()));
                mailService.sendEmailHtml(user.getEmail(),subject,templateName,messageValueMap);
            }*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updateBeneficiaryNameCheckDetails(beneficiaryNameCheck, statusMap);
	}

	private void updatePennyDropStatus(BeneficiaryNameCheck beneficiaryNameCheck) {
		RemittResponseDTO remittResponseDTO = new RemittResponseDTO();
		Map<String, String> statusMap = null;
		
		remittResponseDTO.setAttemptNo(beneficiaryNameCheck.getAttemptNo());
		remittResponseDTO.setPartnerCode(partnerCode);
		remittResponseDTO.setReqTransferType(beneficiaryNameCheck.getTransferType().name());
		remittResponseDTO.setRequestReferenceNo(beneficiaryNameCheck.getUniqueRequestNo());
		remittResponseDTO.setStatusCode(beneficiaryNameCheck.getYesBankAPITransactionStatus().name());
		remittResponseDTO.setUniqueResponseNo(beneficiaryNameCheck.getUniqueResponseNo());
				
		try {
			LOG.info("Getting status information by calling YB API...");
			statusMap = getStatus(remittResponseDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updatePennyDropDetails(beneficiaryNameCheck, statusMap);
	}

	
	private void updateBeneficiaryNameCheckDetailsToCompleted(BeneficiaryNameCheck beneficiaryNameCheck, Map<String, String> statusMap) {
		if(statusMap!=null && statusMap.containsKey(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE)) {
			beneficiaryNameCheck.setNameInResponse(statusMap.get(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE));
		}
		
		if(statusMap!=null && statusMap.containsKey(Constants.YES_BANK_STATUS_CODE_KEY)) {
			beneficiaryNameCheck.setYesBankAPITransactionStatus(YesBankAPITransactionStatus.valueOf(statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY)));
		}
		
		beneficiaryNameCheck.setAttemptNo(getIncrementalAttemptNumber(beneficiaryNameCheck));
		beneficiaryNameCheckService.save(beneficiaryNameCheck);
		final String status = MapUtils.getString(statusMap, "status", "n/a");
		final String bankReferenceNo = MapUtils.getString(statusMap, "bankReferenceNo", "n/a");
		final String transactionDate = MapUtils.getString(statusMap, "transactionDate", new Date().toString());
		final String subStatusCode = MapUtils.getString(statusMap, "subStatusCode", "-");
		final String subStatusText = MapUtils.getString(statusMap, "subStatusText", "-");
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(beneficiaryNameCheck.getUid());
		LOG.info("Transaction Date before parsing: " + transactionDate);
		Date dt = LocalDateTime.parse(transactionDate).toDate();
		LOG.info("Transaction Date after parsing: " + dt + ", " + transactionDate);
		
		if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus())) {
			TransactionWorkflow tw = transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
			if(tw == null) {
				transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY, bankReferenceNo, dt);
			}
			
			transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.COMPLETED, bankReferenceNo, dt);
			try{
				LOG.info("Sending email for completion....22");
				if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus())) {
					LOG.info("Sending email for completion....33");
					BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(beneficiaryNameCheck.getBeneficiaryId());
				    Users  user = usersService.getUserById(beneficiaryUser.getUser());
				    finalReceiptService.sendEmailOnTransactionComplete(user.getEmail(), userCalculatorMapping);
	            }
            }catch (Exception e){
            	LOG.info("10: Error..." + e.getMessage());
		        throw new RuntimeException("Unable to send receipt email.");
            }
		}
		else if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.SENT_TO_BENEFICIARY.getTransactionStatus())) {
			TransactionWorkflow tw = transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
			if(tw == null) {
				transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY, bankReferenceNo, dt);
				sendInstructionMail(userCalculatorMapping);
			}
		}
		else {
			LOG.info("Transaction is not completed ," + userCalculatorMapping.getRefId());
		}
	}
	
	private void updatePennyDropDetails(BeneficiaryNameCheck beneficiaryNameCheck, Map<String, String> statusMap) {
		LOG.info("Updating penny drop" + beneficiaryNameCheck);
		if(statusMap!=null && statusMap.containsKey(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE)) {
			LOG.info("1: updateBeneficiaryNameCheckDetails for penny drop, " + statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY));
			beneficiaryNameCheck.setNameInResponse(statusMap.get(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE));
		}
		
		if(statusMap!=null && statusMap.containsKey(Constants.YES_BANK_STATUS_CODE_KEY) && !statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY).equalsIgnoreCase("completed")) {
			LOG.info("2: updateBeneficiaryNameCheckDetails for Penny drop, " + statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY));
			beneficiaryNameCheck.setYesBankAPITransactionStatus(YesBankAPITransactionStatus.valueOf(statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY)));
		}
		
		beneficiaryNameCheck.setAttemptNo(getIncrementalAttemptNumber(beneficiaryNameCheck));
		beneficiaryNameCheckService.save(beneficiaryNameCheck);
	}
	
	private void updateBeneficiaryNameCheckDetails(BeneficiaryNameCheck beneficiaryNameCheck, Map<String, String> statusMap) {
		
		if(statusMap!=null && statusMap.containsKey(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE)) {
			//LOG.info("1: updateBeneficiaryNameCheckDetails, " + statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY));
			beneficiaryNameCheck.setNameInResponse(statusMap.get(Constants.BENEFICIARY_NAME_KEY_YES_BANK_RESPONSE));
		}
		
		if(statusMap!=null && statusMap.containsKey(Constants.YES_BANK_STATUS_CODE_KEY) && !statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY).equalsIgnoreCase("completed")) {
			//LOG.info("2: updateBeneficiaryNameCheckDetails, " + statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY));
			beneficiaryNameCheck.setYesBankAPITransactionStatus(YesBankAPITransactionStatus.valueOf(statusMap.get(Constants.YES_BANK_STATUS_CODE_KEY)));
		}
		
		//LOG.info("4: updateBeneficiaryNameCheckDetails, Saving name check");
		beneficiaryNameCheck.setAttemptNo(getIncrementalAttemptNumber(beneficiaryNameCheck));
		beneficiaryNameCheckService.save(beneficiaryNameCheck);
		final String status = MapUtils.getString(statusMap, "status", "n/a");
		final String bankReferenceNo = MapUtils.getString(statusMap, "bankReferenceNo", "n/a");
		final String transactionDate = MapUtils.getString(statusMap, "transactionDate", new Date().toString());
		final String subStatusCode = MapUtils.getString(statusMap, "subStatusCode", "-");
		final String subStatusText = MapUtils.getString(statusMap, "subStatusText", "-");
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(beneficiaryNameCheck.getUid());
		LOG.info("Transaction Date before parsing: " + transactionDate);
		Date dt = LocalDateTime.parse(transactionDate).toDate();
		LOG.info("Transaction Date after parsing: " + dt + ", " + transactionDate);
		
		if((isTransferTypeOtherThanNEFTAndCompleted(beneficiaryNameCheck, status) || isTransferTypeNEFTAndCompleted(beneficiaryNameCheck, status))){
			if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus())){
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK);
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BANK);
				
				TransactionWorkflow tw = transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				if(tw == null) {
					transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY, bankReferenceNo, dt);
					sendInstructionMail(userCalculatorMapping);
				}
				
				tw = transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.COMPLETED);
				if(tw == null) {
					transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.COMPLETED, bankReferenceNo, dt);
				}
				/*transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.COMPLETED, bankReferenceNo, dt);
				try{
					LOG.info("Sending email for completion....2");
					if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus())) {
						LOG.info("Sending email for completion....3");
						BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(beneficiaryNameCheck.getBeneficiaryId());
					    Users  user = usersService.getUserById(beneficiaryUser.getUser());
					    finalReceiptService.sendEmailOnTransactionComplete(user.getEmail(), userCalculatorMapping);
		            }
	            }catch (Exception e){
	            	LOG.info("10: Error..." + e.getMessage());
			        throw new RuntimeException("Unable to send receipt email.");
	            }*/
			}
			else if(StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.SENT_TO_BENEFICIARY.getTransactionStatus())) {
				LOG.info("Sending instructions sent email, SENT_TOBENE record could not found hence created new record..." + userCalculatorMapping.getRefId());
				transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BANK);
				
				TransactionWorkflow tw = transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY);
				if(tw == null) {
					transactionWorkflowService.createTransactionWorkFlowOnCompletionOfTxn(userCalculatorMapping, WorkflowStatus.SENT_TO_BENEFICIARY, bankReferenceNo, dt);
					sendInstructionMail(userCalculatorMapping);
				}
			}
        }
		else {
			LOG.info("Transaction is not completed ," + dt + ", " + subStatusCode +" ," + subStatusText);
			transactionWorkflowService.updateFailedTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.SENT_TO_BANK, status, subStatusCode, subStatusText, dt);
			LOG.info("Status is updated in table for ..." + userCalculatorMapping.getRefId());
		}
	}

	private boolean isTransferTypeOtherThanNEFTAndCompleted(BeneficiaryNameCheck beneficiaryNameCheck, String status) {
		//LOG.info("3: updateBeneficiaryNameCheckDetails, " + status + "," + beneficiaryNameCheck.getTransferType().getTransferType());
		return (Constants.UPI_TRANSFER_TYPE.equalsIgnoreCase(beneficiaryNameCheck.getTransferType().getTransferType()) || Constants.FT_TRANSFER_TYPE.equalsIgnoreCase(beneficiaryNameCheck.getTransferType().getTransferType()))
				&& (StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.SENT_TO_BENEFICIARY.getTransactionStatus()) || StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus()));
	}
	
	private boolean isTransferTypeNEFTAndCompleted(BeneficiaryNameCheck beneficiaryNameCheck, String status) {
		return (Constants.NEFT_TRANSFER_TYPE.equalsIgnoreCase(beneficiaryNameCheck.getTransferType().getTransferType()))
				&& (StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.SENT_TO_BENEFICIARY.getTransactionStatus()) || StringUtils.equalsIgnoreCase(status, YesBankAPITransactionStatus.COMPLETED.getTransactionStatus()));
	}
	
	private String getIncrementalAttemptNumber(BeneficiaryNameCheck beneficiaryNameCheck) {
		int currentAttemptNumber = Integer.parseInt(beneficiaryNameCheck.getAttemptNo());
		int newAttemptNumber = currentAttemptNumber + 1;
				
		return String.valueOf(newAttemptNumber);
	}

	private int emailReceipt(final BeneficiaryNameCheck beneficiaryNameCheck) throws Exception{
	    final String uid = beneficiaryNameCheck.getUid();
        if(StringUtils.isBlank(uid)){
            LOG.error("Unable to email receipt :: UID is blank");
            return -1;
        }
        UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);
        if(Objects.isNull(userCalculatorMapping)){
            LOG.error("Unable to email receipt :: UserCalculatorMapping is not available for UID: {}", uid);
            return -1;
        }

        if(Objects.isNull(userCalculatorMapping.getUser())){
            LOG.error("Unable to email receipt :: RemitUser is not available via UserCalculatorMapping from UID: {}", uid);
            return -1;
        }

        Users remitUser = usersService.getUserById(userCalculatorMapping.getUser().getId());
        receiptService.sendEmailForReceipt(remitUser.getEmail(), userCalculatorMapping);
        return 1;
    }

	/*private String getFullName(Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
        String fullname = "";

        System.out.println("First Name: "+personalDetails.getFullName());
        System.out.println("Middle Name: "+personalDetails.getMiddleName());
        System.out.println("Last Name: "+personalDetails.getLastName());

        if (!StringUtils.isEmpty(personalDetails.getLastName())) {
            fullname = personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName();
        } else {
            fullname = personalDetails.getFullName()+" "+personalDetails.getLastName();
        }

        return fullname;
    }*/
    
	public void sendInstructionMail(UserCalculatorMapping userCalculatorMapping) {
		try {
			LOG.info("Sending instructions sent email, SENT_TOBENE record could not found hence created new record..." + userCalculatorMapping.getRefId());
			final Map<String, Object> messageValueMap = new HashMap<>();
			String subject = "Instructions Sent";
			String templateName = "funds_disbursed_3";
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
			List<TransactionWorkflow> tfw = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMapping.getRefId());
			
			for(int i=0;i<tfw.size();i++) {
				if(tfw.get(i).getWorkflowStatus().equals(WorkflowStatus.STAGING_START)) {
					messageValueMap.put("transaction_booking_date",formatter.format(tfw.get(i).getCreateAt()));
					break;
				}
			}
			
			messageValueMap.put("remitter_name",getFirstName(userCalculatorMapping.getUser().getId()));
			messageValueMap.put("transaction_receiving_amount",userCalculatorMapping.getTotalConvertedValue());
			messageValueMap.put("transaction_ref_number",userCalculatorMapping.getRefId());
			messageValueMap.put("transaction_receiving_currency",userCalculatorMapping.getToCurrencyCode());
			//messageValueMap.put("transaction_booking_date",userCalculatorMapping.getCreatedAt());
			messageValueMap.put("transaction_booking_amount",userCalculatorMapping.getTransferAmount());
			messageValueMap.put("transaction_booking_currency",userCalculatorMapping.getFromCurrencyCode());
			messageValueMap.put("receive_mode",userCalculatorMapping.getReceiveMode());
			messageValueMap.put("nepal_disbursement_partner_name",Constants.NEPAL_DISBURSEMENT_PARTNER_NAME);
			messageValueMap.put("pin_id",userCalculatorMapping.getPinno());
			mailService.sendEmailHtml(userCalculatorMapping.getUser().getEmail(),subject,templateName,messageValueMap);
			LOG.info("Sending instructions email sent..." + userCalculatorMapping.getUser().getEmail());
		}
		catch(Exception e) {
			LOG.info("An error occurred while sending an email for Instructions sent, "+ userCalculatorMapping.getRefId());
			LOG.info(e.getMessage());
			LOG.info("Stack trace" + e.getStackTrace());
		}
	}
	
	public void sendInstructionMailForCash(UserCalculatorMapping userCalculatorMapping) {
		try {
			LOG.info("Sending instructions sent email, SENT_TOBENE record could not found hence created new record..." + userCalculatorMapping.getRefId());
			final Map<String, Object> messageValueMap = new HashMap<>();
			String subject = "Instructions Sent";
			String templateName = "funds_disbursed_cash_3";
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
			List<TransactionWorkflow> tfw = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMapping.getRefId());
			
			for(int i=0;i<tfw.size();i++) {
				if(tfw.get(i).getWorkflowStatus().equals(WorkflowStatus.STAGING_START)) {
					messageValueMap.put("transaction_booking_date",formatter.format(tfw.get(i).getCreateAt()));
					break;
				}
			}
			
			messageValueMap.put("remitter_name",getFirstName(userCalculatorMapping.getUser().getId()));
			messageValueMap.put("remitter_fullName",getFUllName(userCalculatorMapping.getUser().getId()));	
			messageValueMap.put("transaction_receiving_amount",userCalculatorMapping.getTotalConvertedValue());
			messageValueMap.put("transaction_ref_number",userCalculatorMapping.getRefId());
			messageValueMap.put("transaction_receiving_currency",userCalculatorMapping.getToCurrencyCode());
			//messageValueMap.put("transaction_booking_date",userCalculatorMapping.getCreatedAt());
			messageValueMap.put("transaction_booking_amount",userCalculatorMapping.getTransferAmount());
			messageValueMap.put("transaction_booking_currency",userCalculatorMapping.getFromCurrencyCode());
			messageValueMap.put("receive_mode",userCalculatorMapping.getReceiveMode());
			messageValueMap.put("nepal_disbursement_partner_name",Constants.NEPAL_DISBURSEMENT_PARTNER_NAME);
			messageValueMap.put("pin_id",userCalculatorMapping.getPinno());
			mailService.sendEmailHtml(userCalculatorMapping.getUser().getEmail(),subject,templateName,messageValueMap);
			LOG.info("Sending instructions email sent..." + userCalculatorMapping.getUser().getEmail());
		}
		catch(Exception e) {
			LOG.info("An error occurred while sending an email for Instructions sent, "+ userCalculatorMapping.getRefId());
			LOG.info(e.getMessage());
			LOG.info("Stack trace" + e.getStackTrace());
		}
	}
}
