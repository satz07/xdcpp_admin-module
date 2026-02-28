package com.adminremit.backoffice.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.adminremit.austrac.report.AustracReportCommonService;
import com.adminremit.austrac.report.AustracReportHelper;
import com.adminremit.austrac.report.AustracReportResponceDTO;
import com.adminremit.austrac.report.Maintaining_Sequence;
import com.adminremit.austrac.report.austracService.AustracService;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.PurposeListMasterService;
import com.adminremit.personaldetails.enums.GBGApiNames;
import com.adminremit.personaldetails.model.GBGVerificationRegisterVerificationResponse;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.service.PersonalDocumentsService;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.backoffice.dto.DisbursementCheckerSearchDTO;
import com.adminremit.backoffice.dto.DisbursementSearchDTO;
import com.adminremit.backoffice.service.DisbursementService;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.disbursement.api.YesBankAPIService;
import com.adminremit.emails.MailService;
import com.adminremit.gbg.repository.GBGVerificationResponseRepository;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.operations.model.ActionStatus;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.service.TransactionWorkflowService;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.user.service.UserCalculatorMappingService;

@RestController
public class DisbursementController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DisbursementController.class);
	
	@Autowired
	private DisbursementService disbursementService;
	
	@Autowired
    private PaymentCodeMasterService paymentCodeMasterService;
	
	@Autowired
    private CurrenciesService currenciesService;
	
	@Autowired
	private UserCalculatorMappingService userCalculatorMappingService;
	
	@Autowired
    private TransactionWorkflowService transactionWorkflowService;
	
	@Autowired
	TransactionWorkflowRepository transactionWorkflowRepository;
	
	@Autowired
	private BeneficiaryUserService beneficiaryUserService;

	@Autowired
	private BeneficiaryAccountService beneficiaryAccountService;
	
	@Autowired
    private GBGVerificationResponseRepository gbgVerificationResponseRepository;
	
	@Autowired
	private PersonalDetailsService personalDetailsService;

	@Autowired
	private MailService mailService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private PersonalDocumentsService personalDocumentsService;

	@Autowired
	private CountriesService countryService;

	@Autowired
	private PurposeListMasterService purposeListMasterService;

	@Autowired
	private AustracService austracService;

	
	@RequestMapping(value = "/backoffice/disbursment/disbursementmaker",method = RequestMethod.GET)
    public ModelAndView getMakerDisbursementDetails(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
        DisbursementSearchDTO disbursementSearchDTO = new DisbursementSearchDTO();
        List<UserCalculatorMapping> userCalculatorMappings = disbursementService.getAllDisbursementMakerList();

        if(userCalculatorMappings!=null) {
            LOG.info("Size of the Mapping "+userCalculatorMappings.size());
        }

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);
        modelAndView.addObject("disbursementSearch",disbursementSearchDTO);
        modelAndView.addObject("usertransactions",userCalculatorMappings);

        modelAndView.setViewName("backoffice/disbursement/disbursementmaker");
        return modelAndView;
    }
	
	
	  @RequestMapping(value = "/backoffice/disbursment/disbursementmaker/disburse/{refId}",method = RequestMethod.GET) 
	  public ModelAndView disburse(@PathVariable("refId") String refId,ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
		LOG.info("Transaction Reference ID to be disbursed:::"+refId);
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
		
		if (userCalculatorMapping != null) {
			transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_START);
			transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_CHECK, ActionStatus.DISBURSE);
		}
	  
		return new ModelAndView("redirect:/backoffice/disbursment/disbursementmaker");
	  }	 
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementmaker/refund/{refId}",method = RequestMethod.GET) 
	  public ModelAndView refund(@PathVariable("refId") String refId,ModelAndView modelAndView, BindingResult bindingResult) throws ParseException { 
		LOG.info("Transaction Reference ID to be refunded:::"+refId);
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
		/*List<TransactionWorkflow> transactions = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMapping.getRefId());
		
		for(int i=0;i<transactions.size();i++) {
			if(transactions.get(i).isCompleted() == false) {
				transactions.get(i).setCompleted(true);
			}
		}*/
		
		if (userCalculatorMapping != null) {
			transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.DISBURSEMENT_START);
			transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping, WorkflowStatus.REFUND_START, ActionStatus.REFUND);
		}

		/*TransactionWorkflow transactionWorflow = new TransactionWorkflow();
		transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
		transactionWorflow.setWorkflowStatus(WorkflowStatus.REFUND_START);
		transactionWorflow.setCompleted(false);
		transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
		transactionWorkflowRepository.save(transactionWorflow);
		 */
		return new ModelAndView("redirect:/backoffice/disbursment/disbursementmaker");
	  }
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementchecker",method = RequestMethod.GET)
	  public ModelAndView getCheckerDisbursementDetails(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
		DisbursementCheckerSearchDTO disbursementChekerSearchDTO = new DisbursementCheckerSearchDTO();
        List<UserCalculatorMapping> userCalculatorMappings = disbursementService.getAllDisbursementCheckerList();

        if(userCalculatorMappings!=null) {
            LOG.info("Size of the Mapping "+userCalculatorMappings.size());
        }

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);
        modelAndView.addObject("disbursementSearch",disbursementChekerSearchDTO);
        modelAndView.addObject("usertransactions",userCalculatorMappings);
        
        modelAndView.setViewName("backoffice/disbursement/disbursementchecker");
        return modelAndView;
	   }
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementchecker/getStatus/{refId}",method = RequestMethod.GET)
	  public String getTransactionStatus(@PathVariable("refId") String refId, ModelAndView modelAndView, BindingResult bindingResult) throws Exception {  
		  return disbursementService.getTransactionStatus(refId);
	  }
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementchecker/getBalance",method = RequestMethod.GET)
	  public String getBalance(ModelAndView modelAndView, BindingResult bindingResult) throws Exception {  
		  return disbursementService.getBalance();
	  }
	  
	  
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementchecker/approve/{refId}/{transferType}",method = RequestMethod.GET) 
	  public String approveDisbursement(@PathVariable("refId") String refId, @PathVariable("transferType") String transferType, ModelAndView modelAndView, BindingResult bindingResult){
		LOG.info("Reference Id of the approved disbursement:::"+refId);
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
		
		if (userCalculatorMapping != null) {
			try {
				disbursementService.startDisbursement(userCalculatorMapping.getUid(), userCalculatorMapping, transferType);
				return "Transaction Approved::"+refId;
			}catch (Exception e) {
				// TODO: handle exception
				return "Error while transaction approval::"+refId +"\n"+e.getMessage();
			}
		}
		return null;
		//return new ModelAndView("redirect:/backoffice/disbursment/disbursementchecker");
	  }
	  
	  @RequestMapping(value = "/backoffice/disbursment/disbursementchecker/refund/{refId}",method = RequestMethod.GET) 
	  public ModelAndView approveDisbursement(@PathVariable("refId") String refId, ModelAndView modelAndView, BindingResult bindingResult) throws Exception {
		LOG.info("Reference Id of the approved disbursement refund:::"+refId);
		
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
		
		if (userCalculatorMapping != null) {
			
			List<TransactionWorkflow> transactions = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMapping.getRefId());
			
			for(int i=0;i<transactions.size();i++) {
				if(transactions.get(i).isCompleted() == false && transactions.get(i).getWorkflowStatus() == WorkflowStatus.REFUND_START) {
					transactions.get(i).setCompleted(true);
					transactionWorkflowRepository.save(transactions.get(i));
					break;
				}
			}
			/*final Map<String, Object> messageValueMap = new HashMap<>();
			String subject = "Funds are sent to your Recipient";
			String templateName = "funds_disbursed_3";
			messageValueMap.put("remitter_name",getFullName(userCalculatorMapping.getUser().getId()));
			messageValueMap.put("transaction_booking_date",userCalculatorMapping.getCreatedAt());
			messageValueMap.put("transaction_booking_amount",userCalculatorMapping.getTransferAmount());
			messageValueMap.put("transaction_booking_currency",userCalculatorMapping.getFromCurrencyCode());
			mailService.sendEmailHtml(userCalculatorMapping.getUser().getEmail(),subject,templateName,messageValueMap);*/
		}
	  
		return new ModelAndView("redirect:/backoffice/disbursment/disbursementchecker");
	  }
	  
	  
	  @Scheduled(cron = "${yesbank.tran.statuscheck.cron.expression}")
	  public void checkAndUpdateDisbursementStatus() throws Exception {
		  disbursementService.checkAndUpdateDisbursementStatus();
	  }
	  
	  @Scheduled(cron = "${yesbank.tran.statuscheck.cron.expression.completed}")
	  public void checkAndCompleteDisbursementStatus() throws Exception {
		  disbursementService.checkAndCompleteDisbursementStatus();
	  }
	  
	  @Scheduled(cron = "${yesbank.penny.statuscheck.cron.expression.completed}")
	  public void checkAndUpdatePennyDropStatus() throws Exception {
		  disbursementService.checkAndUpdatepennyDropStatus();
	  }
	  
	  @Scheduled(cron = "${funds.notreceived.cron.expression}")
	  public void checkFundsReceived() throws Exception {
		  disbursementService.checkAndSendReminder();
	  }
	  
	  @RequestMapping(value = "/backoffice/beneficiary/beneficiaryid/{refId}",method = RequestMethod.GET)
	    public String getBeneficiary(@PathVariable("refId") String refId) {
		  //AJ: Changes to retrieve based on bene account
	    	UserCalculatorMapping userCalculatorMapping =userCalculatorMappingService.findByRefId(refId);
	    	    	
	    	if(userCalculatorMapping!=null && userCalculatorMapping.getBeneficiaryAccountId()!=null) {
	    		BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
	    		return beneficiaryAccount.getReceiveMode().getDescription();
	    		
	    	}
	    	
	    	return null;
	    }

	private String getFullName(Long userId) {
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
	}
	
		private String getFirstName(Long userId) {
			PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
			return personalDetails.getFullName();
		}

	@RequestMapping(value = "/reports/complaince-checklist/download-xml/{refId}",method = RequestMethod.GET)
	public ResponseEntity getReport(@PathVariable("refId") String refid, ModelAndView modelAndView, BindingResult bindingResult) throws Exception {
		String refId =refid.trim();
		AustracReportResponceDTO austracReportResponceDTO = new AustracReportResponceDTO();
		AustracReportHelper helper = new AustracReportHelper();
		AustracReportCommonService service = new AustracReportCommonService();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//file Name
		Date date = new Date();
		DateFormat renameFile = new SimpleDateFormat("yyyyMMdd");
		austracReportResponceDTO.setFileName("IFTI-DRA"+renameFile.format(date)+austracService.getReportSeq()+".xml");
		//Transaction details
		austracReportResponceDTO.setTranBookingDate(transactionWorkflowService.getTransactionCreatedDate(refId) == null ? null :  dateFormat.format(transactionWorkflowService.getTransactionCreatedDate(refId)));
		austracReportResponceDTO.setTranCompletedDate(transactionWorkflowService.getTransactionCompletedDate(refId) == null ? null : dateFormat.format(transactionWorkflowService.getTransactionCompletedDate(refId)));
		if(austracReportResponceDTO.getTranCompletedDate() == null){
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Error Message");
		}
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
		austracReportResponceDTO.setTranCurrencyCode(userCalculatorMapping.getFromCurrencyCode());
		austracReportResponceDTO.setTransferAmount(userCalculatorMapping.getTransferAmount());
		austracReportResponceDTO.setTranRefNo(refId);
		//Ordering customer
		Users users = usersService.getUserById(userCalculatorMapping.getUser().getId());
		PersonalDetails personalDetails = personalDetailsService.getLatestByUser(users.getId());
		austracReportResponceDTO.setOrdrCustFullName(getFullName(users.getId()));
		austracReportResponceDTO.setOrdrCustDOB(personalDetails.getDob() == null ? null : dateFormat.format(personalDetails.getDob()));
		//Ordering customer contact details
		PersonalDocuments personalDocuments = personalDocumentsService.fetchOne(personalDetails);
		austracReportResponceDTO.setOrdrCustAddress(helper.getAddress(personalDetails));
		austracReportResponceDTO.setOrdrCustCity(personalDetails.getSuburb());
		austracReportResponceDTO.setOrdrCustState(personalDetails.getProvince());
		austracReportResponceDTO.setOrdrCustPostCode(personalDetails.getPostalCode());
		austracReportResponceDTO.setOrdrCustCountry(personalDetails.getCountry());
		//Ordering customer business details
		austracReportResponceDTO.setOrdrCustBusiCustNo(users.getId());
		//Ordering customer identification details
		austracReportResponceDTO.setDocType1(helper.setDocType(personalDocuments.getDocumentTypes().toString()));
		austracReportResponceDTO.setDocNumber1(personalDocuments.getDocumentNumber());
		austracReportResponceDTO.setDocIssuer1(helper.getIssuer(personalDocuments));
		//Beneficiary customer
		BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
		austracReportResponceDTO.setBenfullName(beneficiaryUser.getFullName());
		austracReportResponceDTO.setBenAdress(beneficiaryUser.getAddress());
		austracReportResponceDTO.setBenCity(beneficiaryUser.getCity());
		austracReportResponceDTO.setBenState(beneficiaryUser.getProvince());
		austracReportResponceDTO.setBenPostCode(beneficiaryUser.getPincode());
		austracReportResponceDTO.setBenCountry(countryService.getById(beneficiaryUser.getCountry()).getCountryName());
		//Beneficiary customer account details
		BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
		if(beneficiaryAccount.getAccountType().toString() == "WAL" || beneficiaryAccount.getAccountType().toString() == "CAS"){
			austracReportResponceDTO.setBenAccNo("");
			austracReportResponceDTO.setBenBankName("");
			austracReportResponceDTO.setBenCity("");
			austracReportResponceDTO.setBenCountry("");
		}else {
			austracReportResponceDTO.setBenAccNo(beneficiaryAccount.getAccountNumber());
			austracReportResponceDTO.setBenBankName(beneficiaryAccount.getBranchName());
		}
		List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponseList = gbgVerificationResponseRepository.findByPersonalDetailsAndGbgApiNames(personalDetails, GBGApiNames.DVS);
		if(gbgVerificationRegisterVerificationResponseList!=null && !gbgVerificationRegisterVerificationResponseList.isEmpty()) {
			GBGVerificationRegisterVerificationResponse gbgVerificationRegisterVerificationResponse = gbgVerificationRegisterVerificationResponseList.get(gbgVerificationRegisterVerificationResponseList.size() - 1);
			austracReportResponceDTO.setDocElectronicData(gbgVerificationRegisterVerificationResponse.getSourceList());
		}else {
		
			gbgVerificationRegisterVerificationResponseList = gbgVerificationResponseRepository.findByPersonalDetailsAndGbgApiNames(personalDetails, GBGApiNames.NON_DVS);
			if(gbgVerificationRegisterVerificationResponseList!=null && !gbgVerificationRegisterVerificationResponseList.isEmpty()) {
				GBGVerificationRegisterVerificationResponse gbgVerificationRegisterVerificationResponse = gbgVerificationRegisterVerificationResponseList.get(gbgVerificationRegisterVerificationResponseList.size() - 1);
				austracReportResponceDTO.setDocElectronicData(gbgVerificationRegisterVerificationResponse.getSourceList());
			}
		}
		
		austracReportResponceDTO.setDocElectronicData(refId);
		
		//Person/organisation receiving the transfer instruction
		helper.personToRecivingTranAmt(austracReportResponceDTO,userCalculatorMapping);
		//Reason
		if(beneficiaryUser.getPurpose() == null) {
			austracReportResponceDTO.setPurpose(null);
		}else {
			austracReportResponceDTO.setPurpose(purposeListMasterService.getById(Long.parseLong(beneficiaryUser.getPurpose())).getPurpose());
		}
		String request = service.makeHttpCallForAustracReport(austracReportResponceDTO);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + austracReportResponceDTO.getFileName() + "\"")
				.body(request);
	}

	@RequestMapping(value = "/reports/complaince-checklist/updateSeq/{fileName}",method = RequestMethod.GET)
	public Maintaining_Sequence updateSeq(@PathVariable("fileName") String fileName){

		return austracService.saveReportSeq(fileName);
	}

}
