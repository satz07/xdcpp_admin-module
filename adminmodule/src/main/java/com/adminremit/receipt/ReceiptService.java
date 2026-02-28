package com.adminremit.receipt;

import com.adminremit.auth.models.User;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.user.model.Users;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.emails.MailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReceiptService {
    final private static Logger LOGGER = LoggerFactory.getLogger(ReceiptService.class);
    final private static DateTimeFormatter FILE_NAME_SUFFIX_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
    final private static DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

    @Autowired
    private MailService mailService;


    public String sendEmailForReceipt(
            String userEmail,
            UserCalculatorMapping mapping
    ) throws Exception {

        final String transactionNumber = mapping.getRefId();
        LocalDateTime currentTime = LocalDateTime.now();

        final String fileName = transactionNumber + "_" +  FILE_NAME_SUFFIX_FORMAT.format(currentTime);
        LOGGER.debug("File Name: {}", fileName);
        final String subject = "Receipt :: " + mapping.getRefId();
        final Map<String, Object> attachmentValueMap = new HashMap<>();
        final Map<String, Object> messageValueMap = new HashMap<>();

        final String remitterName = mapping.getUserName();
        final String transactionBookingDate = DISPLAY_FORMAT.format(currentTime);
        final String transactionBookingAmount = mapping.getTransferAmount().toString();
        final String transactionBookingCcy = mapping.getToCurrencyCode();
        final String paymentMethod = mapping.getPaymentMode();
        final String receiverName = mapping.getBeneficiaryName();
        final String transferFee = mapping.getTransactionFee().toString();
        final String exchangeRate = mapping.getToCurrencyValue().toString();
        final String receiverBankAccount = "receiver_bank_account";
        final String transferableAmount = "transferable_amount";
        final String convertedAmount = "converted_amount";
        final String promotionalBenefit = "promotional_benefit";
        final String totalAmountToReceiver = "total_amount_to_receiver";
        final String iAccountName = "i_account_name";
        final String iBankName = "i_bank_name";
        final String iAccountNumber = "i_account_number";
        final String iBsbCode = "i_bsb_code";
        final String expTimestamp = "exp_timestamp";

        messageValueMap.put("remitter_name", remitterName);
        messageValueMap.put("transaction_booking_date", transactionBookingDate);
        messageValueMap.put("transaction_booking_amount", transactionBookingAmount);
        messageValueMap.put("transaction_booking_ccy", transactionBookingCcy);
        messageValueMap.put("transaction_reference_number", transactionNumber);

        attachmentValueMap.put("timestamp", transactionBookingDate);
        attachmentValueMap.put("transaction_number", transactionNumber);
        attachmentValueMap.put("sender_name", remitterName);
        attachmentValueMap.put("payment_method", paymentMethod);
        attachmentValueMap.put("receiver_name",receiverName);
        attachmentValueMap.put("receiver_bank_account",receiverBankAccount);
        attachmentValueMap.put("transfer_amount",transactionBookingAmount);
        attachmentValueMap.put("transfer_fee", transferFee);
        attachmentValueMap.put("transferable_amount",transferableAmount);
        attachmentValueMap.put("exchange_rate",exchangeRate);
        attachmentValueMap.put("converted_amount",convertedAmount);
        attachmentValueMap.put("promotional_benefit",promotionalBenefit);
        attachmentValueMap.put("total_amount_to_receiver",totalAmountToReceiver);
        attachmentValueMap.put("i_account_name",iAccountName);
        attachmentValueMap.put("i_bank_name",iBankName);
        attachmentValueMap.put("i_account_number",iAccountNumber);
        attachmentValueMap.put("i_bsb_code",iBsbCode);
        attachmentValueMap.put("exp_timestamp",expTimestamp);
        final String messageTemplateName = getMessageTemplateName();
        LOGGER.info("Message Template: {}",messageTemplateName);
        final String attachmentTemplateName = getAttachmentTemplateName();
        LOGGER.info("Attachment Template: {}",attachmentTemplateName);
        mailService.sendEmailHtml(userEmail,subject, messageTemplateName, messageValueMap, attachmentTemplateName, attachmentValueMap, fileName);
        return fileName;
    }

    private String getMessageTemplateName(){
        return "email-2-b";
    }

    private String getAttachmentTemplateName(){
        return "interim_receipt_template-1";
    }
}
