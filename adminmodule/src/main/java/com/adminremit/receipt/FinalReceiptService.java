package com.adminremit.receipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.repository.BeneficiaryUserRepository;
import com.adminremit.emails.MailService;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.repository.PaymentReceiveModeRepository;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.itextpdf.text.DocumentException;

@Service
public class FinalReceiptService {
	
	final private static  Logger LOGGER = LoggerFactory.getLogger(FinalReceiptService.class);
	
	final private static NumberFormat CCY_FORMAT = NumberFormat.getInstance(Locale.US);
	final private static DecimalFormat EXCHANGE_AMT_FORMAT = new DecimalFormat("#,###.0000");
	final private static DateTimeFormatter FILE_NAME_SUFFIX_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
	
	@Autowired
    private MailService mailService;
	
	@Autowired
    private PersonalDetailsService personalDetailsService;
	
	@Autowired
	private PaymentReceiveModeRepository paymentReceiveModeRepository;
	
	@Autowired
	private TransactionWorkflowRepository transactionWorkflowRepository;
	
	@Autowired
	private BeneficiaryUserRepository beneficiaryUserRepository;
	
	public String sendEmailOnTransactionComplete(final String userEmail, UserCalculatorMapping userCalculatorMapping) throws MessagingException, IOException, DocumentException {
		final Map<String, Object> messageValueMap = getMailRelatedContent(userCalculatorMapping);
		LOGGER.info("Mail Message Value Map for Final Receipt: {}",messageValueMap);
		
		//final Map<String, Object> attachmentValueMap = getAttachmentRelatedContent(userCalculatorMapping);
		//LOGGER.info("Mail Attachment Value Map for Final Receipt: {}",attachmentValueMap);
		
		final String mailTemplateName = getMailTemplateName();
		LOGGER.info("Mail Template for Final Receipt: {}",mailTemplateName);
		
		//final String attachmentTemplateName = getAttachmentTemplateName();
        //LOGGER.info("Mail Attachment Template for Final Receipt : {}",attachmentTemplateName);
		
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedAEDT = zonedUTC.withZoneSameInstant(ZoneId.of("Australia/Sydney"));
        final String fileName = userCalculatorMapping.getRefId() + "_" +  FILE_NAME_SUFFIX_FORMAT.format(zonedAEDT);
        LOGGER.debug("File Name: {}", fileName);
        
        String subject =  "Money Credited";
        if(userCalculatorMapping.getReceiveMode().equals("CAS")) {
        	subject =  "Money Delivered";
        }
        mailService.sendEmailHtml(userEmail,subject, mailTemplateName, messageValueMap);
        
        return fileName;		
	}
	
	public String downloadPdfReceipt(final String userEmail, UserCalculatorMapping userCalculatorMapping) throws MessagingException, IOException, DocumentException {
		final Map<String, Object> pdfReceiptValueMap = getPdfReceiptRelatedContent(userEmail, userCalculatorMapping);
		final String attachmentTemplateName = getAttachmentTemplateName();
        LOGGER.info("Final PDF Receipt Template: {}",pdfReceiptValueMap);
        
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedAEDT = zonedUTC.withZoneSameInstant(ZoneId.of("Australia/Sydney"));
        final String fileName = userCalculatorMapping.getRefId() + "_" +  FILE_NAME_SUFFIX_FORMAT.format(zonedAEDT);
        LOGGER.debug("File Name: {}", fileName);
        
        return mailService.downloadPdfReceipt(attachmentTemplateName, pdfReceiptValueMap, fileName);
	}

	private Map<String, Object> getPdfReceiptRelatedContent(String userEmail, UserCalculatorMapping userCalculatorMapping) {
		return getAttachmentRelatedContent(userCalculatorMapping);
	}

	private String getAttachmentTemplateName() {
		return "final-receipt-template";
	}

	private String getMailTemplateName() {
		return "funds_credited_4";
	}

	private Map<String, Object> getAttachmentRelatedContent(UserCalculatorMapping userCalculatorMapping) {
		final Map<String, Object> attachmentValueMap = new HashMap<>();
		
		final String iAccountName = "XDC Payments Pty Ltd";
        final String iBankName = "YES BANK";
        final String iAccountNumber = "319627648";
        final String iBsbCode = "802-985";
		
		TransactionWorkflow transactionWorkflow = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatus(userCalculatorMapping.getRefId(), WorkflowStatus.STAGING_START);
		final String transactionBookingDate = transactionWorkflow.getCreateAt().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
		attachmentValueMap.put("timestamp", transactionBookingDate);
		
		ZonedDateTime nowinAEST =  ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
		final String currentDateTime = nowinAEST.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
		attachmentValueMap.put("date_time", currentDateTime);
		
		attachmentValueMap.put("transaction_number", userCalculatorMapping.getRefId());
		attachmentValueMap.put("sender_name", userCalculatorMapping.getUserName());
		
		long paymentModeId = userCalculatorMapping.getPaymentCodeid();
		long receivedModeId = userCalculatorMapping.getReceiveModeId();
		
		List<Long> paymentReceiveModeIdList = Arrays.asList(paymentModeId, receivedModeId);
		List<PaymentReceiveMode> paymentReceiveModeList = paymentReceiveModeRepository.findAllById(paymentReceiveModeIdList);
		
		Optional<PaymentReceiveMode> paymentMode = getPaymentMode(paymentModeId, paymentReceiveModeList);		
		String paymentModeDescription = paymentMode.isPresent() ? paymentMode.get().getDescription() : null;
		
		Optional<PaymentReceiveMode> receiveMode = getReceiveMode(receivedModeId, paymentReceiveModeList);
		String receiveModeDescription = receiveMode.isPresent() ? receiveMode.get().getDescription() : null;
		attachmentValueMap.put("payment_method", paymentModeDescription);
		
		Optional<BeneficiaryUser> beneficiaryUser = beneficiaryUserRepository.findById(userCalculatorMapping.getBeneficiaryId());
		String beneficiaryName = beneficiaryUser.isPresent() ? beneficiaryUser.get().getName() : null;
		attachmentValueMap.put("receiver_name", beneficiaryName);		
		attachmentValueMap.put("payout_method", receiveModeDescription);
		
		final String receiverBankAccount = userCalculatorMapping.getBeneficiaryAccountNumber();
		attachmentValueMap.put("receiver_bank_account",receiverBankAccount);
		
		final String transactionBookingAmount = formatNumberWithCurrency(CCY_FORMAT, userCalculatorMapping.getTransferAmount(), userCalculatorMapping.getFromCurrencyCode());
		attachmentValueMap.put("transfer_amount", transactionBookingAmount);
		
		final String transferFee = formatNumberWithCurrency(CCY_FORMAT, userCalculatorMapping.getTransactionFee(), userCalculatorMapping.getFromCurrencyCode());
		attachmentValueMap.put("transfer_fee", transferFee);
		
		final String transferableAmount = formatNumberWithCurrency(CCY_FORMAT, (userCalculatorMapping.getTransferAmount().subtract(userCalculatorMapping.getTransactionFee())), userCalculatorMapping.getFromCurrencyCode());
		attachmentValueMap.put("transferable_amount", transferableAmount);
		
		final String exchangeRate = formatNumberWithCurrency(EXCHANGE_AMT_FORMAT, userCalculatorMapping.getToCurrencyValue(), StringUtils.EMPTY);
		attachmentValueMap.put("exchange_rate", exchangeRate);
		
		final String convertedAmount = formatNumberWithCurrency(CCY_FORMAT, userCalculatorMapping.getTotalConvertedValue(), userCalculatorMapping.getToCurrencyCode());
		attachmentValueMap.put("converted_amount", convertedAmount);
		attachmentValueMap.put("amount_to_recipient", convertedAmount);
		attachmentValueMap.put("i_account_name",iAccountName);
        attachmentValueMap.put("i_bank_name",iBankName);
        attachmentValueMap.put("i_account_number",iAccountNumber);
        attachmentValueMap.put("i_bsb_code",iBsbCode);
		
		//final String totalAmountToReceiver =formatNumberWithCurrency(CCY_FORMAT, (userCalculatorMapping.getTransferAmount().subtract(userCalculatorMapping.getTransactionFee())).multiply(userCalculatorMapping.getToCurrencyValue()), userCalculatorMapping.getFromCurrencyCode());
		
		
		return attachmentValueMap;
	}

	private Optional<PaymentReceiveMode> getPaymentMode(long paymentModeId, List<PaymentReceiveMode> paymentReceiveModeList) {
		Optional<PaymentReceiveMode> paymentMode = paymentReceiveModeList.stream()
				                                                         .filter(p->(p.getId() == paymentModeId))
				                                                         .findFirst();
		return paymentMode;
	}
	
	private Optional<PaymentReceiveMode> getReceiveMode(long receiveModeId, List<PaymentReceiveMode> paymentReceiveModeList) {
		Optional<PaymentReceiveMode> receiveMode = paymentReceiveModeList.stream()
				                                                         .filter(p->(p.getId() == receiveModeId))
				                                                         .findFirst();
		return receiveMode;
	}

	private Map<String, Object> getMailRelatedContent(UserCalculatorMapping userCalculatorMapping) {
		final Map<String, Object> messageValueMap = new HashMap<>();
		com.adminremit.personaldetails.model.PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userCalculatorMapping.getUser().getId());
		final String remitterName =  personalDetails.getFullName();
		messageValueMap.put("remitter_name", remitterName);
		messageValueMap.put("receive_amount", userCalculatorMapping.getToCurrencyCode() + " " + userCalculatorMapping.getTotalConvertedValue());
		messageValueMap.put("refrence_number", userCalculatorMapping.getRefId());
		messageValueMap.put("send_amount", userCalculatorMapping.getFromCurrencyCode() + " " + userCalculatorMapping.getTransferAmount());
		messageValueMap.put("recive_mode", userCalculatorMapping.getReceiveMode());
		
		return messageValueMap;
	}
	
	private String formatNumberWithCurrency(final NumberFormat formatter, BigDecimal val, String ccy){
        try{
            final String amountString = formatter.format(val);
            return ccy + " " + amountString;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return "N/A";
        }
    }
}
