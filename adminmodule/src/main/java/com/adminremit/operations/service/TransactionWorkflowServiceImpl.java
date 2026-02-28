package com.adminremit.operations.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.common.util.DateUtil;
import com.adminremit.emails.MailService;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.TAT;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.HolidaysService;
import com.adminremit.masters.service.TATService;
import com.adminremit.operations.model.ActionStatus;
import com.adminremit.operations.model.JobType;
import com.adminremit.operations.model.ReconStatus;
import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransactionWorkflowAgeingDetails;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.TransactionWorkflowAgeingRepository;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.user.service.CurrencyService;
import com.adminremit.user.service.UserCalculatorMappingService;

@Service
public class TransactionWorkflowServiceImpl implements TransactionWorkflowService{

    private static final Logger LOG = LoggerFactory.getLogger(TransactionWorkflowServiceImpl.class);
    
    @Autowired
    TransactionWorkflowRepository transactionWorkflowRepository;
      
    @Autowired
    private UserCalculatorMappingService userCalculatorMappingService;
        
    @Autowired
    private CurrenciesService currenciesService;
    
    @Autowired
    private CurrencyService currencyService;
    
    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TATService tatService;
    
    @Autowired
    private HolidaysService holidaysService;
    
    @Autowired
    private CountriesRepository countriesRepository;
    
    @Autowired
    private TransactionWorkflowAgeingRepository transactionWorkflowAgeingRepository;

    @Override
    public TransactionWorkflow updateTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus) {
        LOG.info("Updating transaction::" + userCalculatorMapping.getRefId() + "::" + workflowStatus);
    	List<TransactionWorkflow> transactionWorkflow = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatusAndIsCompleted(userCalculatorMapping.getRefId(),
                workflowStatus,false);
    	if(transactionWorkflow == null || transactionWorkflow.size() == 0) {
    		LOG.info("transactionWorkflow is null");
    		List<TransactionWorkflow> transactionWorkflowList = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatusAndIsCompleted(userCalculatorMapping.getRefId(),
                    workflowStatus,true);
    		LOG.info("transactionWorkflow status checked again " + transactionWorkflow);
    		
    		if(transactionWorkflowList != null && transactionWorkflowList.size() > 0) {
    			LOG.info("List is having more than one records hence returning first element");
    	    	return transactionWorkflowList.get(0);
    		}
    	}
    	else {
	        transactionWorkflow.get(0).setCompleted(true);
	        TransactionWorkflow newTransactionWorkflow =  transactionWorkflowRepository.save(transactionWorkflow.get(0));
	        
	        userCalculatorMapping.setStatus(workflowStatus.name());
	        currencyService.updateCurrencyCalcMapping(userCalculatorMapping);
        
	        LOG.info("Saved transaction::" + userCalculatorMapping.getRefId() + "::" + workflowStatus);
	        return newTransactionWorkflow;
    	}
    	
    	LOG.info("returning null from updateTransactionWorkFlow");
    	
    	if(transactionWorkflow != null && transactionWorkflow.size() > 0)
    		return transactionWorkflow.get(0);
    	else
    		return null;
    }
    
    @Override
    public TransactionWorkflow updateFailedTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, String status, String subStatus, String subStatusText, Date dt) {
        LOG.info("Updating transaction::" + userCalculatorMapping.getRefId() + "::" + workflowStatus);
    	List<TransactionWorkflow> transactionWorkflow = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatusAndIsCompleted(userCalculatorMapping.getRefId(),
                workflowStatus,false);
    	if(transactionWorkflow == null || transactionWorkflow.size() == 0) {
    		LOG.info("transactionWorkflow is null," + userCalculatorMapping.getRefId());
    	}
    	else {
	        transactionWorkflow.get(0).setCompleted(false);
	        transactionWorkflow.get(0).setSubStatusCode(subStatus);
	        transactionWorkflow.get(0).setSubStatusText(subStatusText);
	        if(status!=null && status.equalsIgnoreCase("ONHOLD")) {
	        	transactionWorkflow.get(0).setReconStatus(ReconStatus.ONHOLD);
	        }
	        else if(status!=null && status.equalsIgnoreCase("FAILED")) {
	        	transactionWorkflow.get(0).setReconStatus(ReconStatus.FAILED);
	        }
	        
	        transactionWorkflow.get(0).setUtrTime(dt);
	        TransactionWorkflow newTransactionWorkflow =  transactionWorkflowRepository.save(transactionWorkflow.get(0));
	        userCalculatorMapping.setStatus(workflowStatus.name());
	        currencyService.updateCurrencyCalcMapping(userCalculatorMapping);
            LOG.info("Saved transaction::" + userCalculatorMapping.getRefId() + "::" + workflowStatus);
	        return newTransactionWorkflow;
    	}
    	
    	LOG.info("returning null from updateTransactionWorkFlow");
    	return null;
    }

    @Override
    public TransactionWorkflow updateTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, ReconStatus status) {
        List<TransactionWorkflow> transactionWorkflow = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatusAndIsCompleted(userCalculatorMapping.getRefId(),
                workflowStatus,false);
        transactionWorkflow.get(0).setCompleted(true);
        transactionWorkflow.get(0).setReconStatus(status);
        TransactionWorkflow newTransactionWorkflow =  transactionWorkflowRepository.save(transactionWorkflow.get(0));
        userCalculatorMapping.setStatus(workflowStatus.name());
        currencyService.updateCurrencyCalcMapping(userCalculatorMapping);    
        LOG.info("Saved transaction::" + userCalculatorMapping.getRefId() + "::" + workflowStatus);
        return newTransactionWorkflow;
    }

    @Override
    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus) {

        TransactionWorkflow transactionWorflow = new TransactionWorkflow();
        transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
        transactionWorflow.setWorkflowStatus(workflowStatus);
        transactionWorflow.setCompleted(false);
        transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
        TransactionWorkflow newTransactionWorkflow = transactionWorkflowRepository.save(transactionWorflow);
        return newTransactionWorkflow;
    }
    
    @Override
    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, ActionStatus actionStatus) {

        TransactionWorkflow transactionWorflow = new TransactionWorkflow();
        transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
        transactionWorflow.setWorkflowStatus(workflowStatus);
        transactionWorflow.setCompleted(false);
        transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
        transactionWorflow.setActionStatus(actionStatus.getStatus());
        TransactionWorkflow newTransactionWorkflow = transactionWorkflowRepository.save(transactionWorflow);
        return newTransactionWorkflow;
    }

    @Override
    public TransactionWorkflow createTransactionWorkFlow(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus,ReconStatus status) {

        TransactionWorkflow transactionWorflow = new TransactionWorkflow();
        transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
        transactionWorflow.setWorkflowStatus(workflowStatus);
        transactionWorflow.setReconStatus(status);
        transactionWorflow.setCompleted(false);
        transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
        TransactionWorkflow newTransactionWorkflow = transactionWorkflowRepository.save(transactionWorflow);
        return newTransactionWorkflow;
    }


    @Override
    public TransactionWorkflow createTransactionWorkFlowUnamatched(WorkflowStatus workflowStatus, TransferAccountDetails transferAccountDetails) {

        TransactionWorkflow transactionWorflow = new TransactionWorkflow();
        transactionWorflow.setUserCalculatorMapping(null);
        transactionWorflow.setWorkflowStatus(workflowStatus);
        transactionWorflow.setCompleted(false);
        transactionWorflow.setReferenceNo(transferAccountDetails.getTransactionRefNo());
        TransactionWorkflow newTransactionWorkflow = transactionWorkflowRepository.save(transactionWorflow);
        return newTransactionWorkflow;
    }

    @Override
    public List<TransactionWorkflow> getAllTransactionWorkflow(WorkflowStatus workflowStatus){
        List<TransactionWorkflow> transactionWorkFlows = transactionWorkflowRepository.findAllByWorkflowStatusAndIsCompleted(workflowStatus,false);

        LOG.info("TransactionWorkFlow Size "+transactionWorkFlows.size());

        if(transactionWorkFlows!=null && transactionWorkFlows.size()>0)
        {
            for(TransactionWorkflow transactionWorkflow : transactionWorkFlows)

            LOG.info("transactionWorkFlow  "+transactionWorkflow.getUserCalculatorMapping().getRefId());
        }
        else
        {
            LOG.info("transactionWorkFlow  is Null");

        }

        return transactionWorkFlows;
    }

	@Override
	public void checkAndSetGuarantedRateApplicableFlag() {
		List<TransactionWorkflow> transactionWorkFlows = transactionWorkflowRepository.findAllByWorkflowStatusAndIsCompletedAndIsGuarantedRateApplicable(WorkflowStatus.STAGING_START,false,true);
				
		List<TransactionWorkflow> guarantedRateFallbackTxns = transactionWorkFlows.stream()
		                    													  .filter(t-> isEligibleForGuaranteedRateFallback(t))																								
		                    													  .collect(Collectors.toList());
		
		if(guarantedRateFallbackTxns!=null && !guarantedRateFallbackTxns.isEmpty()) {
			LOG.info("Size of transactions which is elgible for guaranted rate fallback:::"+guarantedRateFallbackTxns.size());
			guarantedRateFallbackTxns.stream()
								     .forEach(t->t.setGuarantedRateApplicable(false));
			
			transactionWorkflowRepository.saveAll(guarantedRateFallbackTxns);
			
			/*for(int i=0;i<guarantedRateFallbackTxns.size();i++) {
				UserCalculatorMapping ucm = guarantedRateFallbackTxns.get(i).getUserCalculatorMapping();
				String email = ucm.getEmail();
				final Map<String, Object> messageValueMap = new HashMap<>();
                String subject = "Change in Exchange Rate";
                LOG.info("Sending email for funds credited..." + ucm.getRefId());
                String templateName = "exchange_rate_fallback_18";
                
                messageValueMap.put("remitter_name",getFirstName(ucm.getUser().getId()));
                messageValueMap.put("refrence_number",ucm.getRefId());
                messageValueMap.put("receive_amount", ucm.getToCurrencyCode() + " " + ucm.getTotalConvertedValue());
                
                try {
                	mailService.sendEmailHtml(email,subject,templateName,messageValueMap);	
                }
                catch(Exception ex) {
                	LOG.info("Exchange rate email sending failed for txn ref id:" + ucm.getRefId());
                	LOG.info("Error message:" + ex.getMessage());
                }
                
			}*/
		}		
	}
	
	private String getFirstName(Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
        return personalDetails.getFullName();
    }

	private boolean isEligibleForGuaranteedRateFallback(TransactionWorkflow t) {
		UserCalculatorMapping userCalcMapping = userCalculatorMappingService.findByRefId(t.getReferenceNo());
		boolean isEligibleForGuaranteedRateFallback = false;
		
		if(userCalcMapping!=null) {
			LOG.info("Reference Id:::"+userCalcMapping.getRefId());
			LOG.info("Transfer Amount:::"+userCalcMapping.getTransferAmount());
			LOG.info("Disbursement Currency Code:::"+userCalcMapping.getFromCurrencyCode());
			LOG.info("Receiving Currency Code:::"+userCalcMapping.getToCurrencyCode());
			LOG.info("Payment Mode Type:::"+userCalcMapping.getPaymentCodeid());
			LOG.info("Receiving Mode Type:::"+userCalcMapping.getReceiveModeId());
			LOG.info("Sending Country Code:::"+userCalcMapping.getFromCountryCode());
			LOG.info("Receiving Country Code:::"+userCalcMapping.getToCountryCode());
			
			Currencies fromCurrency = currenciesService.findByCurrencyCode(userCalcMapping.getFromCurrencyCode());
			Currencies toCurrency = currenciesService.findByCurrencyCode(userCalcMapping.getToCurrencyCode());
			
			LOG.info("From Currency Id:::"+fromCurrency.getId());
			LOG.info("To Currency Id:::"+toCurrency.getId());
			
			
			LocalDateTime bookingDateTime = DateUtil.getDateTimeInAESTZone(new java.sql.Timestamp(t.getCreateAt().getTime()).toLocalDateTime());
	        LocalDateTime currentDateTime = DateUtil.getDateTimeInAESTZone(LocalDateTime.now());
	        TAT tat = null;
	        
	        RemittUser user = userCalcMapping.getUser();
	        if(user!=null && user.getUserType()!=null) {
	        	LOG.info("User Type:::"+user.getUserType().toUpperCase());
	        	tat = tatService.getTatDetailsBySendingAndReceivingCountryAndUserType(userCalcMapping.getTransferAmount(), fromCurrency.getId(), toCurrency.getId(), userCalcMapping.getPaymentCodeid(), userCalcMapping.getReceiveModeId(), user.getUserType().toUpperCase());
	        }			
			
			if(tat == null) {
				LOG.info("Tat is not found for...");
				LOG.info("Transfer Amount:::"+userCalcMapping.getTransferAmount());
				LOG.info("Disbursement Currency Code:::"+userCalcMapping.getFromCurrencyCode());
				LOG.info("Receiving Currency Code:::"+userCalcMapping.getToCurrencyCode());
				LOG.info("Payment Mode Type:::"+userCalcMapping.getPaymentCodeid());
				LOG.info("Receiving Mode Type:::"+userCalcMapping.getReceiveModeId());
				LOG.info("Sending Country Code:::"+userCalcMapping.getFromCountryCode());
				LOG.info("Receiving Country Code:::"+userCalcMapping.getToCountryCode());
				LOG.info("From Currency Id:::"+fromCurrency.getId());
				LOG.info("To Currency Id:::"+toCurrency.getId());
				LOG.info("User Type:::"+user.getUserType().toUpperCase());
				
				return isEligibleForGuaranteedRateFallback;				
			}
			
			LOG.info("TAT => amount from slab===>"+tat.getAmountSlabFrom());
    		LOG.info("TAT => amount to slab===>"+tat.getAmountSlabTo());
    		LOG.info("TAT => Sending Currency Code===>"+tat.getDisburseCCM().getCurrencyCode());
    		LOG.info("TAT => Receiving Currency Code===>"+tat.getOriginCCM().getCurrencyCode());
    		LOG.info("TAT => Payment Mode===>"+tat.getPaymentMode().getId());
    		LOG.info("TAT => Receive Mode===>"+tat.getReceiveMode().getId());
    		LOG.info("TAT => Payment Hours===>"+tat.getPaymentHrs());
    		LOG.info("TAT => Receive Hours===>"+tat.getReceiveHrs());
    		LOG.info("TAT => Expiry Time===>"+tat.getExpiryTime());
    		
    		Countries sendingCountry = countriesRepository.findByCountryCode(userCalcMapping.getFromCountryCode());
    		LOG.info("Sending Country id for Country Code ===>"+userCalcMapping.getFromCountryCode()+ " is==>"+sendingCountry.getId());
    		
			List<String> holidayList = holidaysService.getLimitedHolidaysBySendingCountry(sendingCountry.getId()); 
			
			List<LocalDate> holidays = new ArrayList<>();
			if(holidayList!=null && !holidayList.isEmpty()) {
	        	LOG.info("Holiday List for the country id ==>"+ userCalcMapping.getFromCountryCode()+" is===>"+holidayList);
	        	holidays = holidayList.stream()
						   			   .map(date->DateUtil.convertDateStringToLocalDate(date))
						               .collect(Collectors.toList());	        	
	        	
	        }
			
			long timeInMinutes = DateUtil.getNumberOfMinutesBetweenDatesExcludingWeekendAndHoliday(bookingDateTime, currentDateTime, holidays);
    		LOG.info("isEligibleForGuaranteedRateFallback==>Transaction===>"+t.getReferenceNo()+" is in staging start status since last "+timeInMinutes+" minutes");
    		logTransactionAgeing(timeInMinutes, t, JobType.GUARANTED_RATE_FALLBACK, WorkflowStatus.STAGING_START);
    		
    		long guaranteedRateTimeLimit = Long.valueOf(tat.getExpiryTime()) * 60;
    		
    		if(timeInMinutes > guaranteedRateTimeLimit) {
    			LOG.info("isEligibleForGuaranteedRateFallback====>ALERT::::Transaction:::"+t.getReferenceNo()+" has crossed the time limit of "+guaranteedRateTimeLimit+ " minutes. Hence setting Guaranted Rate flag to false.");
    		}
    		
    		isEligibleForGuaranteedRateFallback = timeInMinutes > guaranteedRateTimeLimit;
		}		
		return isEligibleForGuaranteedRateFallback;
	}

	private void logTransactionAgeing(long timeInMinutes, TransactionWorkflow t, JobType jobType, WorkflowStatus workflowStatus) {
		TransactionWorkflowAgeingDetails transactionWorkflowAgeingDetails = transactionWorkflowAgeingRepository.findByReferenceNoAndJobTypeAndWorkflowStatus(t.getReferenceNo(), jobType, workflowStatus);
		
		if(transactionWorkflowAgeingDetails!=null) {
			transactionWorkflowAgeingDetails.setMinutesSpent(timeInMinutes);
			
			Date currentDate = new Date();
			transactionWorkflowAgeingDetails.setLastUpdatedAt(currentDate);
			
			transactionWorkflowAgeingRepository.save(transactionWorkflowAgeingDetails);
		}else {
			TransactionWorkflowAgeingDetails newTransactionWorkflowAgeingDetails = new TransactionWorkflowAgeingDetails();
			newTransactionWorkflowAgeingDetails.setBookingDate(t.getCreateAt());
			newTransactionWorkflowAgeingDetails.setJobType(jobType);
			newTransactionWorkflowAgeingDetails.setLastUpdatedAt(new Date());
			newTransactionWorkflowAgeingDetails.setMinutesSpent(timeInMinutes);
			newTransactionWorkflowAgeingDetails.setReferenceNo(t.getReferenceNo());
			newTransactionWorkflowAgeingDetails.setWorkflowStatus(workflowStatus);
			
			transactionWorkflowAgeingRepository.save(newTransactionWorkflowAgeingDetails);			
		}		
	}

	@Override
	public void eligibleForAutoCancelOfTxns() {
		List<TransactionWorkflow> transactionWorkFlows = transactionWorkflowRepository.findAllByWorkflowStatusAndIsCompletedAndIsGuarantedRateApplicable(WorkflowStatus.STAGING_START,false,false);
		
		List<TransactionWorkflow> autoCancelEligibleTxns = transactionWorkFlows.stream()
																			   .filter(t->isEligibleForAutoCancelOfTxns(t))
																			   .collect(Collectors.toList());
		
		if(autoCancelEligibleTxns!=null && !autoCancelEligibleTxns.isEmpty()) {
			LOG.info("Size of transactions which is eligible for auto cancellation:::"+autoCancelEligibleTxns.size());
			
			autoCancelEligibleTxns.stream()
								  .forEach(t->updateTransaction(t));
			
			for(int i=0;i<autoCancelEligibleTxns.size();i++) {
				UserCalculatorMapping ucm = autoCancelEligibleTxns.get(i).getUserCalculatorMapping();
				String email = ucm.getEmail();
				
				List<TransactionWorkflow> twf = transactionWorkflowRepository.findAllByReferenceNo(ucm.getRefId());
                String bookingDate = "";
                
                for(int j=0; j<twf.size(); j++) {
                	if(twf.get(j).getWorkflowStatus() == WorkflowStatus.STAGING_START) {
                		bookingDate = twf.get(j).getCreateAt().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
                		break;
                	}
                }
                
				final Map<String, Object> messageValueMap = new HashMap<>();
                String subject = "Transaction Cancelled";
                LOG.info("Sending email for Transaction Cancelled..." + ucm.getRefId());
                String templateName = "transaction_cancelled_22";
                
                messageValueMap.put("remitter_name",getFirstName(ucm.getUser().getId()));
                messageValueMap.put("refrence_number",ucm.getRefId());
                messageValueMap.put("booking_date", bookingDate);
                
                try {
                	mailService.sendEmailHtml(email,subject,templateName,messageValueMap);	
                }
                catch(Exception ex) {
                	LOG.info("Transaction Cancelled email sending failed for txn ref id:" + ucm.getRefId());
                	LOG.info("Error message:" + ex.getMessage());
                }
                
			}
		}		
	}
	
	private void updateTransaction(TransactionWorkflow transactionWorkflow) {
		transactionWorkflow.setCompleted(true);	
		transactionWorkflowRepository.save(transactionWorkflow);
		
		UserCalculatorMapping userCalcMapping = transactionWorkflow.getUserCalculatorMapping();
		createTransactionWorkFlow(userCalcMapping, WorkflowStatus.CANCELLED);	
		updateTransactionWorkFlow(userCalcMapping, WorkflowStatus.CANCELLED);
	}

	private boolean isEligibleForAutoCancelOfTxns(TransactionWorkflow t) {
		UserCalculatorMapping userCalcMapping = userCalculatorMappingService.findByRefId(t.getReferenceNo());
		boolean isEligibleForAutoCancelOfTxns = false;
		
		if(userCalcMapping!=null) {
			LOG.info("Reference Id:::"+userCalcMapping.getRefId());
			LOG.info("Transfer Amount:::"+userCalcMapping.getTransferAmount());
			LOG.info("Disbursement Currency Code:::"+userCalcMapping.getFromCurrencyCode());
			LOG.info("Receiving Currency Code:::"+userCalcMapping.getToCurrencyCode());
			LOG.info("Payment Mode Type:::"+userCalcMapping.getPaymentCodeid());
			LOG.info("Receiving Mode Type:::"+userCalcMapping.getReceiveModeId());
			LOG.info("Sending Country Code:::"+userCalcMapping.getFromCountryCode());
			LOG.info("Receiving Country Code:::"+userCalcMapping.getToCountryCode());
			
			Currencies fromCurrency = currenciesService.findByCurrencyCode(userCalcMapping.getFromCurrencyCode());
			Currencies toCurrency = currenciesService.findByCurrencyCode(userCalcMapping.getToCurrencyCode());
			
			LOG.info("From Currency Id:::"+fromCurrency.getId());
			LOG.info("To Currency Id:::"+toCurrency.getId());
			
			LocalDateTime bookingDateTime = DateUtil.getDateTimeInAESTZone(new java.sql.Timestamp(t.getCreateAt().getTime()).toLocalDateTime());
	        LocalDateTime currentDateTime = DateUtil.getDateTimeInAESTZone(LocalDateTime.now());
			
	        TAT tat =null;
	        RemittUser user = userCalcMapping.getUser();
	        if(user!=null && user.getUserType()!=null) {
	        	tat = tatService.getTatDetailsBySendingAndReceivingCountryAndUserType(userCalcMapping.getTransferAmount(), fromCurrency.getId(), toCurrency.getId(), userCalcMapping.getPaymentCodeid(), userCalcMapping.getReceiveModeId(), user.getUserType());
	        }
	        
			if(tat == null) {
				LOG.info("TAT is not found for... ");
				LOG.info("Transfer Amount:::"+userCalcMapping.getTransferAmount());
				LOG.info("Disbursement Currency Code:::"+userCalcMapping.getFromCurrencyCode());
				LOG.info("Receiving Currency Code:::"+userCalcMapping.getToCurrencyCode());
				LOG.info("Payment Mode Type:::"+userCalcMapping.getPaymentCodeid());
				LOG.info("Receiving Mode Type:::"+userCalcMapping.getReceiveModeId());
				LOG.info("Sending Country Code:::"+userCalcMapping.getFromCountryCode());
				LOG.info("Receiving Country Code:::"+userCalcMapping.getToCountryCode());
				LOG.info("From Currency Id:::"+fromCurrency.getId());
				LOG.info("To Currency Id:::"+toCurrency.getId());
				
				return isEligibleForAutoCancelOfTxns;
			}
			
			LOG.info("TAT => amount from slab===>"+tat.getAmountSlabFrom());
    		LOG.info("TAT => amount to slab===>"+tat.getAmountSlabTo());
    		LOG.info("TAT => Sending Currency Code===>"+tat.getDisburseCCM().getCurrencyCode());
    		LOG.info("TAT => Receiving Currency Code===>"+tat.getOriginCCM().getCurrencyCode());
    		LOG.info("TAT => Payment Mode===>"+tat.getPaymentMode().getId());
    		LOG.info("TAT => Receive Mode===>"+tat.getReceiveMode().getId());
    		LOG.info("TAT => Payment Hours===>"+tat.getPaymentHrs());
    		LOG.info("TAT => Receive Hours===>"+tat.getReceiveHrs());
    		LOG.info("TAT => Cancel Time===>"+tat.getCancelTime()+" Days.");
    		
    		Countries sendingCountry = countriesRepository.findByCountryCode(userCalcMapping.getFromCountryCode());
    		LOG.info("Sending Country id for Country Code ===>"+userCalcMapping.getFromCountryCode()+ " is==>"+sendingCountry.getId());
    		
			List<String> holidayList = holidaysService.getLimitedHolidaysBySendingCountry(sendingCountry.getId());
			
			List<LocalDate> holidays = new ArrayList<>();
			if(holidayList!=null && !holidayList.isEmpty()) {
	        	LOG.info("Holiday List for the country id ==>"+ userCalcMapping.getFromCountryCode()+" is===>"+holidayList);
	        	holidays = holidayList.stream()
						   			   .map(date->DateUtil.convertDateStringToLocalDate(date))
						               .collect(Collectors.toList());	        	
	        	
	        }
			
			long timeInMinutes = DateUtil.getNumberOfMinutesBetweenDatesExcludingWeekendAndHoliday(bookingDateTime, currentDateTime, holidays);
    		LOG.info("isEligibleForAutoCancelOfTxns==>Transaction===>"+t.getReferenceNo()+" is in staging start status since last "+timeInMinutes+" minutes");
    		logTransactionAgeing(timeInMinutes, t, JobType.AUTO_TXN_CANCEL, WorkflowStatus.STAGING_START);
    		
    		long autoCancelTimeLimit = Long.valueOf(tat.getCancelTime()) * 24 * 60;
    		
    		if(timeInMinutes > autoCancelTimeLimit) {
    			LOG.info("isEligibleForAutoCancelOfTxns====>ALERT::::Transaction:::"+t.getReferenceNo()+" has crossed the time limit of "+autoCancelTimeLimit+ " minutes. Hence cancelling the transaction.");
    		}
    		
    		isEligibleForAutoCancelOfTxns = timeInMinutes > autoCancelTimeLimit;
		}
		
		return isEligibleForAutoCancelOfTxns;
	}

	@Override
	public TransactionWorkflow createTransactionWorkFlowOnCompletionOfTxn(UserCalculatorMapping userCalculatorMapping, WorkflowStatus workflowStatus, String bankRefNumber, Date utrTime) {		
	        TransactionWorkflow transactionWorflow = new TransactionWorkflow();
	        transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
	        transactionWorflow.setWorkflowStatus(workflowStatus);
	        transactionWorflow.setCompleted(false);
	        transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
	        transactionWorflow.setUtrNumber(bankRefNumber);
	        transactionWorflow.setUtrTime(utrTime);
	        TransactionWorkflow newTransactionWorkflow = transactionWorkflowRepository.save(transactionWorflow);
	        return newTransactionWorkflow;	    
	}

	@Override
	public Date getTransactionCreatedDate(String refId) {
		TransactionWorkflow tw = transactionWorkflowRepository.getTransactionCreatedDate(refId);
		return tw == null ? null : tw.getCreateAt();
	}

	@Override
	public Date getTransactionCompletedDate(String refId) {
		TransactionWorkflow tw = transactionWorkflowRepository.getTransactionCompletedDate(refId);
		return tw == null ? null : tw.getCreateAt();
	}
}
