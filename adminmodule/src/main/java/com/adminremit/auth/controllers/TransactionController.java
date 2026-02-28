package com.adminremit.auth.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.dto.TransactionDTO;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.auth.service.RegistrationService;
import com.adminremit.auth.service.TransactionService;
import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.receiver.model.ReceiverTransactionModel;
import com.adminremit.receiver.service.ReceiverPurposeService;
import com.adminremit.report.constants.ReportsConstants;
import com.adminremit.user.model.Users;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private RegistrationService registerService;
	
	@Autowired
	private ReceiverPurposeService receiverPurposeService;
	
	@RequestMapping(value = "reports/transaction-report-old", method = RequestMethod.GET)
	public ModelAndView getTransaction(ModelAndView modelAndView) throws ParseException {
		DateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		

		List<TransactionDTO> transactionDTO = new ArrayList<TransactionDTO>();
		List<TransactionWorkflow> transactionList = transactionService.getPaginatedTransactionList();

		if (transactionList != null) {
			TransactionDTO dto = null;
			 for (TransactionWorkflow trs : transactionList) {
				 	try {
						dto = new TransactionDTO(trs);
						
						TransactionWorkflow transactionWorkflow = transactionService.getTransactionByReferenceIdAndWorkflowStatus(trs.getReferenceNo(), WorkflowStatus.STAGING_START);
						String bookingDate = null;
						String bookingTime = null;
						String utrDate = null;
						String utrTime = null;
						
						if(transactionWorkflow!=null && transactionWorkflow.getCreateAt()!=null) {
							bookingDate = formatterDate.format(transactionWorkflow.getCreateAt());
							bookingTime = formatterTime.format(transactionWorkflow.getCreateAt());
						}					
						
						
						UserCalculatorMapping cal = transactionService.getUserCalculatorById(trs.getReferenceNo());
						PersonalDetails details = registerService.findByUserId(cal.getUser().getId());
						BeneficiaryAccount account = transactionService.getBeneficiaryById(cal.getBeneficiaryAccountId());
						BeneficiaryUser beneficiaryUser = transactionService.getBeneficiartUserById(account.getBeneficiaryUser().getId());
						Users users = transactionService.getUserById(cal.getUser().getId());
						
						if(cal!=null) {
							ReceiverTransactionModel receiverTransactionModel = receiverPurposeService.getReceiverTypeByUid(cal.getId());
							if(receiverTransactionModel!=null) {
								PurposeListMaster purposeListMaster = receiverPurposeService.getPurposeListById(receiverTransactionModel.getPurposeList());
								
								if(purposeListMaster!=null) {
									dto.setPurpose(purposeListMaster.getPurpose());
								}
							}
							
							List<TransactionWorkflow> transactionWorkflowList = transactionService.findByReferenceNo(cal.getRefId());
							if(transactionWorkflowList!=null && !transactionWorkflowList.isEmpty()) {
								for(TransactionWorkflow t : transactionWorkflowList) {
									if(t.getDisbursementBankStatus()!=null) {
										dto.setDisbursementBankStatus(t.getDisbursementBankStatus());
										break;
									}
								}
								
								for(TransactionWorkflow t : transactionWorkflowList) {
									if(t.getDisbursementDateTime()!=null) {
										dto.setDisbursementDateTime(t.getDisbursementDateTime());
										break;
									}
								}
								
								for(TransactionWorkflow t : transactionWorkflowList) {
									if(t.getUtrNumber()!=null) {
										dto.setUtrNumber(t.getUtrNumber());
										break;
									}
								}
								
								for(TransactionWorkflow t : transactionWorkflowList) {
									if(t.getUtrTime()!=null) {
																				
										utrDate = formatterDate.format(t.getUtrTime());
										utrTime = formatterTime.format(t.getUtrTime());
										
										dto.setUtrDate(utrDate);
										dto.setUtrTime(utrTime);
										break;
									}
								}
									
							}
							
							TransactionWorkflow latestTransactionWorkflow = transactionService.getLatestRecordByRefernceId(cal.getRefId());
                            if(latestTransactionWorkflow!=null) {
                            	dto.setStatus(latestTransactionWorkflow.getWorkflowStatus()!=null ? latestTransactionWorkflow.getWorkflowStatus().name() : null);
                            }
						}
						
						if(account!=null) {
							dto.setVpaHandle(account.getAccountType()!=null && account.getAccountType().equals(AccountType.UPI) ? account.getAccountNumber() : null);
						}
						
						if(beneficiaryUser!=null && beneficiaryUser.getBeneficiaryRelationship()!=null) {
							dto.setRelationShip(ReportsConstants.relationShipMap.get(beneficiaryUser.getBeneficiaryRelationship()));
						}
						
						BigDecimal exchangeRate;
						BigDecimal marginSpread;
						
						if(cal.getFxBaseRate() == null || cal.getFxBaseRate().isEmpty()) {
							//exchangeRate = cal.getToCurrencyValue();
							dto.setFxBaseRate(cal.getToCurrencyValue());
							dto.setMargin(trs.getMargin());
						}
						else {
							dto.setFxBaseRate(new BigDecimal(cal.getFxBaseRate()));
							dto.setMargin(trs.getMargin());
							//exchangeRate = new BigDecimal(cal.getFxBaseRate()).subtract(new BigDecimal(cal.getMarginSpread()));
						}
									
						//dto.setExchangeRate(exchangeRate);
				
						dto.setUserCal(cal);
						dto.setAccount(account);
						dto.setUser(users);
						dto.setBeneficiaryUser(beneficiaryUser);
						dto.setBookingDate(bookingDate);
						dto.setBookingTime(bookingTime);
						dto.setPersonalDetails(details);
						dto.setRemitterFullname(details.getFullName()+" "+details.getLastName()+" "+details.getMiddleName());
						
						transactionDTO.add(dto);
				 	}
				 	catch (Exception e) {
				        e.printStackTrace();
				    }
			 	} 
			
			 	modelAndView.addObject("transactionLists", transactionDTO);
			 	modelAndView.setViewName("report/transaction-report");
			 	
			 	return modelAndView;
		} else {
			modelAndView.setViewName("report/transaction-report");
			return modelAndView;
		}
	}

	@RequestMapping(value = "reports/transaction-summary", method = RequestMethod.GET)
	public ModelAndView showReportPage(ModelAndView modelAndView) {
		modelAndView.setViewName("report/transaction-summary");
		return modelAndView;
	}

	@RequestMapping(value = "summary/list", method = RequestMethod.POST)
	public ModelAndView getTransaction(@RequestParam(value = "transactionId", required = false) String transactionId,
			ModelAndView modelAndView) throws ParseException {
	
		List<TransactionDTO> transactionDTO = new ArrayList<TransactionDTO>();

		TransactionWorkflow transactionList = transactionService.getPaginatedTransactionSummary(transactionId);
		if (transactionList != null) {
			 try {
			TransactionDTO dto = null;

			dto = new TransactionDTO(transactionList);
			UserCalculatorMapping cal = transactionService.getUserCalculatorById(transactionList.getReferenceNo());

//			BigDecimal fxBaseRate;
//			fxBaseRate = cal.getTotalConvertedValue()
//					.subtract(cal.getTransferAmount().multiply(cal.getToCurrencyValue()))
//					.subtract(cal.getTransactionFee());
//			dto.setFxBaseRate(fxBaseRate);
			dto.setUserCal(cal);
			BeneficiaryAccount account = transactionService.getBeneficiaryById(cal.getBeneficiaryAccountId());
			dto.setAccount(account);

			Users users = transactionService.getUserById(cal.getUser().getId());
			System.out.println("UserID"+cal.getUser().getId());
			dto.setUser(users);

			BeneficiaryUser beneficiaryUser = transactionService
					.getBeneficiartUserById(account.getBeneficiaryUser().getId());

			dto.setBeneficiaryUser(beneficiaryUser);
			transactionDTO.add(dto);

			PersonalDetails details=registerService.findByUserId(cal.getUser().getId());
			
			dto.setPersonalDetails(details);
			
			dto.setRemitterFullname(details.getFullName()+" "+details.getLastName()+" "+details.getMiddleName());
			
			
			System.out.println(dto.getRemitterFullname());
			modelAndView.addObject("transactionLists", transactionDTO);
			modelAndView.setViewName("report/transaction-summary");
		
			
		} catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
				return modelAndView;
		} else {
			modelAndView.addObject("transactionLists", transactionDTO);
			modelAndView.setViewName("report/transaction-summary");
			return modelAndView;
		}
	}
}
