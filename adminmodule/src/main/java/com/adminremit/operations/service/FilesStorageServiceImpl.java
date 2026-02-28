package com.adminremit.operations.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.backoffice.service.StagingCustomSearch;
import com.adminremit.backoffice.service.StartRemitterService;
import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.enums.VerificationStatus;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.common.models.Constants;
import com.adminremit.common.util.DateUtil;
import com.adminremit.emails.MailService;
import com.adminremit.masters.models.BankMaster;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.operations.dto.FileUploadDTO;
import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.FileDBRepository;
import com.adminremit.operations.repository.OperationFileDetailsRepository;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.repository.TransferAccountRepository;
import com.adminremit.operations.repository.UserCalculatorRepository;
import com.adminremit.payments.enums.TransactionStatus;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.user.aml.KYCCheckService;
import com.adminremit.user.dto.CurrencyConvert;
import com.adminremit.user.model.CurrencyCalculatorModel;
import com.adminremit.user.service.CurrencyService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(FilesStorageServiceImpl.class);
    private final Path root = Paths.get("uploads");
    @Value("${file.upload.operations}")
    private String fileUploadLocation;

    final private static NumberFormat CCY_FORMAT = NumberFormat.getInstance(Locale.US);
    final private static DecimalFormat EXCHANGE_AMT_FORMAT = new DecimalFormat("#,###.00");

    @Autowired
    TransferAccountService transferAccountService;

    @Autowired
    StagingCustomSearch stagingCustomSearch;

    @Autowired
    FilesStorageService filesStorageService;

    @Autowired
    FileDBRepository fileDBRepository;

    @Autowired
    OperationFileDetailsRepository operationFileDetailsRepository;

    @Autowired
    TransferAccountRepository transferAccountRepository;

    @Autowired
    BankDetailsService bankDetailsService;

    @Autowired
    CurrenciesService currenciesService;

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private KYCCheckService kycCheckService;

    @Autowired
    private MailService mailService;


    @Autowired
    UserCalculatorRepository userCalculatorMapping;

    @Autowired
    TransactionWorkflowService transactionWorkflowService;

    @Autowired
    private TransactionWorkflowRepository transactionWorkflowRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private BeneficiaryUserService beneficiaryUserService;

    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;

    @Autowired
    private StartRemitterService startRemitterService;

    @Override
    public boolean save(FileInfo fileInfo) throws IOException, ParseException {
        LOG.info("Reading file from path:" + fileInfo.getUrl());
        File fileExcel = new File(fileInfo.getUrl());
        LOG.info("Read the file.");
        boolean isSucess = false;

        try (FileInputStream excelFile = new FileInputStream(fileExcel);
             Workbook workbooks = WorkbookFactory.create(excelFile)) {

            LOG.info("Opened file stream...");

            LOG.info("Trying to read current sheet at index 0");
            Sheet currentSheet = workbooks.getSheetAt(0);
            int numberRows = currentSheet.getPhysicalNumberOfRows();
            LOG.info("numberRows   " + numberRows);
            for (int i = 1; i < currentSheet.getPhysicalNumberOfRows(); i++) {
                Row row = currentSheet.getRow(i);

                TransferAccountDetails transferAccountDetails = new TransferAccountDetails();

                LOG.info("=================>" + row.getCell(0));
                transferAccountDetails.setValueDate(row.getCell(0).getDateCellValue());
                transferAccountDetails.setTransactionType(row.getCell(1).getStringCellValue());
                transferAccountDetails.setCurrencyCode(row.getCell(2).getStringCellValue());
                transferAccountDetails.setAmountReceived(new BigDecimal(row.getCell(3).getNumericCellValue()));
                transferAccountDetails.setTransactionRefNo(row.getCell(4).getStringCellValue());
                transferAccountDetails.setBankRef(row.getCell(5).getStringCellValue());
                transferAccountDetails.setSenderName(row.getCell(6).getStringCellValue());
                transferAccountDetails.setBsbNumber(new BigDecimal(row.getCell(7).getNumericCellValue()));
                transferAccountDetails.setAccountNumber(new BigDecimal(row.getCell(8).getNumericCellValue()));
                transferAccountDetails.setFileInfo(fileInfo);
                LOG.info("get RefNo" + transferAccountDetails.getTransactionRefNo());

                transferAccountService.save(transferAccountDetails);
            }
            // check for Matching
        } catch (Exception e) {
            e.printStackTrace();
        }
        isSucess = true;
        return isSucess;
    }

    @Override
    public String saveUploadedFiles(MultipartFile file) throws IOException {
        // Make sure directory exists!
        File uploadDir = new File(fileUploadLocation);
        uploadDir.mkdirs();
        StringBuilder sb = new StringBuilder();
        if (!file.isEmpty()) {
            String uploadFilePath = fileUploadLocation + "/" + DateUtil.formatCurrentDateByGivenPattern(Constants.FILE_UPLOAD_DATE_TIME_PATTERN) + "_" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            LOG.info("the path calculated in the uploadfilePath::" + path);
            Files.write(path, bytes);
            sb.append(uploadFilePath);
        }
        return sb.toString();
    }

    /**
     * Here the method  isAccountAlreadyPresent is implemented to check the upload account Number  with the transfer account details
     */
    private OperationFileUpload operationFileUpload;

    public OperationFileUpload isAccountAlreadyPresent() {

        LOG.info("entered in FileStorageServiceImpl to fetch the account details uploaded ");
        return this.operationFileUpload;
    }

    @Override
    public boolean save(FileUploadDTO form) {
        boolean isSuccess = false;
        boolean isFileExist = true;
        try {
            for (MultipartFile file : form.getInterimFile()) {
                LOG.info("uploaded file name and ::" + file.getOriginalFilename().trim());
                if (file != null && !file.getOriginalFilename().isEmpty()) {
                    FileInfo fileInfo = fileDBRepository.findAllByName(file.getOriginalFilename().trim());
                    if (fileInfo != null) {
                        isFileExist = true;
                    } else {
                        isFileExist = false;
                    }
                }
            }


            LOG.info("File Uploaded" + isFileExist);
            if (!isFileExist) {
                LOG.info("Entered in the operation File Upload::" + isFileExist);
                operationFileUpload = new OperationFileUpload();
                operationFileUpload.setUploadType(form.getUploadType());
                operationFileUpload.setAccountType(form.getAccountType());
                operationFileUpload.setAccountType(form.getAccountType());
                operationFileUpload.setStatementType(form.getStatementType());
                operationFileUpload.setCurrencyCode(form.getCurrencyCode());
                operationFileUpload.setBankDetails(form.getBankDetails());
                operationFileUpload.setAccountNumber(form.getAccountNumber());
                Currencies currencies = currenciesService.getById(form.getCurrencyCode());
                BankMaster bankMaster = bankDetailsService.getBankDetailsById(form.getBankDetails()).get(0);
                operationFileUpload.setBankName(bankMaster.getBankName());
                operationFileUpload.setCurrencyName(currencies.getCurrencyCode());
                operationFileUpload.setStatus(PENDING);

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = null;
                if (principal instanceof UserDetails) {
                    username = ((UserDetails) principal).getUsername();
                } else {
                    username = principal.toString();
                }

                operationFileUpload.setLoginUser(username);
                LOG.info("All details set in the Operation File Details::" + principal);
                OperationFileUpload operationFileUpload1 = operationFileDetailsRepository.save(operationFileUpload);
                LOG.info("After Saving File " + operationFileUpload1.getId());

                int fileCount = 0;
                for (MultipartFile file : form.getInterimFile()) {
                    LOG.info("File :" + file.getOriginalFilename());
                    String filePath = filesStorageService.saveUploadedFiles(file);
                    LOG.info("File uploaded on saveUpload File service");
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setName(file.getOriginalFilename());
                    fileInfo.setUrl(filePath);
                    fileInfo.setOperationFile(operationFileUpload1);
                    FileInfo fileInfo1 = fileDBRepository.save(fileInfo);
                    try {
                        fileCount++;
                        filesStorageService.save(fileInfo1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                operationFileUpload1.setFileCount(fileCount);

                operationFileDetailsRepository.save(operationFileUpload1);
                isSuccess = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error file upload the file");
        }
        return isSuccess;
    }

    @Override
    public boolean update(FileUploadDTO form) {
        boolean isSuccess = false;
        try {
            OperationFileUpload operationFileUpload = operationFileDetailsRepository.findById(form.getId()).get();
            if (operationFileUpload != null) {
                if (form.getIsApproved()) {
                    operationFileUpload.setStatus(APPROVED);
                    Set<FileInfo> fileInfoSet = operationFileUpload.getFileInfo();
                    for (FileInfo fileInfo : fileInfoSet) {
                        matchAndUnMatched(fileInfo);
                    }

                } else {
                    operationFileUpload.setStatus(PENDING);
                }
                OperationFileUpload operationFileUpload1 = operationFileDetailsRepository.save(operationFileUpload);
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error file upload the file");
        }

        return isSuccess;

    }

    @Override
    public List<FileInfo> getFileList(OperationFileUpload operationFileUpload) throws IOException {

        List<FileInfo> fileInfos = fileDBRepository.findAllByOperationFile(operationFileUpload);
        return fileInfos;

    }

    @Override
    public FileInfo viewFile(String id) throws IOException {

        FileInfo fileInfos = fileDBRepository.findAllById(id);
        return fileInfos;

    }

    public void matchAndUnMatched(FileInfo fileInfo) throws Exception {
        List<TransferAccountDetails> transferAccountDetails = transferAccountRepository.findAllByFileInfo(fileInfo);

        for (TransferAccountDetails transferAccountDetail : transferAccountDetails) {
            LOG.info("Data from Excel Record ==>" + transferAccountDetail.getTransactionRefNo());
            List<UserCalculatorMapping> userCalculatorMappings = userCalculatorMapping.findAllByRefId(transferAccountDetail.getTransactionRefNo());
            if (userCalculatorMappings != null && userCalculatorMappings.size() > 0) {
                if (userCalculatorMappings.get(0).getTransferAmount().compareTo(transferAccountDetail.getAmountReceived()) == 0
                        && userCalculatorMappings.get(0).getFromCurrencyCode().equalsIgnoreCase(transferAccountDetail.getCurrencyCode().trim())) {

                    processPennyDropAndAmlCheck(userCalculatorMappings);

                    transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.STAGING_START);
                    //transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0),WorkflowStatus.COMPLIANCE_START);
                    transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.DISBURSEMENT_START);
                    List<TransactionWorkflow> twf = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMappings.get(0).getRefId());
                    String bookingDate = "";

                    for (int j = 0; j < twf.size(); j++) {
                        if (twf.get(j).getWorkflowStatus() == WorkflowStatus.STAGING_START) {
                            bookingDate = twf.get(j).getCreateAt().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
                            break;
                        }
                    }

                    // send across email alert to the user 13
                    String email = userCalculatorMappings.get(0).getEmail();
                    final Map<String, Object> messageValueMap = new HashMap<>();
                    String subject = "Money Received";
                    LOG.info("Sending email for funds received..." + userCalculatorMappings.get(0).getRefId());
                    String templateName = "money_received_20";

                    messageValueMap.put("remitter_name", getFirstName(userCalculatorMappings.get(0).getUser().getId()));
                    messageValueMap.put("refrence_number", userCalculatorMappings.get(0).getRefId());
                    messageValueMap.put("send_amount", userCalculatorMappings.get(0).getFromCurrencyCode() + " " + userCalculatorMappings.get(0).getTransferAmount());
                    messageValueMap.put("booking_date", bookingDate);

                    try {
                        mailService.sendEmailHtml(email, subject, templateName, messageValueMap);
                    } catch (Exception ex) {
                        LOG.info("Money Received email sending failed for txn ref id:" + userCalculatorMappings.get(0).getRefId());
                        LOG.info("Error message:" + ex.getMessage());
                    }

                    try {
                        processExchangeRateFallBack(userCalculatorMappings.get(0));
                    } catch (RecordNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (userCalculatorMappings.get(0).getTransferAmount().compareTo(transferAccountDetail.getAmountReceived()) > 0
                        && userCalculatorMappings.get(0).getFromCurrencyCode().equalsIgnoreCase(transferAccountDetail.getCurrencyCode().trim())) {
                    transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.STAGING_START);
                    transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.MATCHED_EXCEPTION);

                }
                // TODO : Viren - Send across this email when amount book is greater than the amount received.
                /*else if(userCalculatorMappings.get(0).getTransferAmount().compareTo(transferAccountDetail.getAmountReceived()) < 0
                        && userCalculatorMappings.get(0).getFromCurrencyCode().equalsIgnoreCase(transferAccountDetail.getCurrencyCode().trim())){
                    transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMappings.get(0),WorkflowStatus.STAGING_START);
                    transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0),WorkflowStatus.UNMATCHED_ENTRIES);
                    
                    List<TransactionWorkflow> twf = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMappings.get(0).getRefId());
                    String bookingDate = "";
                    
                    for(int j=0; j<twf.size(); j++) {
                    	if(twf.get(j).getWorkflowStatus() == WorkflowStatus.STAGING_START) {
                    		bookingDate = twf.get(j).getCreateAt().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
                    		break;
                    	}
                    }
                    
                    // send across email alert to the user 13
                    String email = userCalculatorMappings.get(0).getEmail();
    				final Map<String, Object> messageValueMap = new HashMap<>();
                    String subject = "Partial Money Received";
                    LOG.info("Sending email for Partial Money Received..." + userCalculatorMappings.get(0).getRefId());
                    String templateName = "partial_money_received_21";
                    
                    messageValueMap.put("remitter_name",getFirstName(userCalculatorMappings.get(0).getUser().getId()));
                    messageValueMap.put("refrence_number",userCalculatorMappings.get(0).getRefId());
                    messageValueMap.put("send_amount", userCalculatorMappings.get(0).getFromCurrencyCode() + " " + userCalculatorMappings.get(0).getTransferAmount());
                    messageValueMap.put("booking_date", bookingDate);
                    
                    try {
                    	mailService.sendEmailHtml(email,subject,templateName,messageValueMap);	
                    }
                    catch(Exception ex) {
                    	LOG.info("Money Received email sending failed for txn ref id:" + userCalculatorMappings.get(0).getRefId());
                    	LOG.info("Error message:" + ex.getMessage());
                    }

                }*/
                else if (userCalculatorMappings.get(0).getTransferAmount().compareTo(transferAccountDetail.getAmountReceived()) < 0
                        && userCalculatorMappings.get(0).getFromCurrencyCode().equalsIgnoreCase(transferAccountDetail.getCurrencyCode().trim())) {

                    transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.STAGING_START);
                    transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.UNMATCHED_ENTRIES);
                    //send across email alert to the user 14
                } else {
                    transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.STAGING_START);
                    transactionWorkflowService.createTransactionWorkFlow(userCalculatorMappings.get(0), WorkflowStatus.UNMATCHED_ENTRIES);

                }
            } else {
                transactionWorkflowService.createTransactionWorkFlowUnamatched(WorkflowStatus.UNMATCHED_ENTRIES, transferAccountDetail);
            }

        }

    }

    private void processPennyDropAndAmlCheck(List<UserCalculatorMapping> userCalculatorMappings) throws Exception {
        //UserCalculatorMapping userCalculatorMapping = userCalculatorMappings.get(0);

        for (UserCalculatorMapping userCalculatorMapping : userCalculatorMappings) {
            doAmlCheck(userCalculatorMapping);
            if (userCalculatorMapping.getToCountryCode().equals("IN"))
                doPennyDrop(userCalculatorMapping);
            else {
                userCalculatorMapping.setStatus(TransactionStatus.CONFIRMED.name());
                currencyService.updateCurrencyCalcMapping(userCalculatorMapping);
            }
        }
    }

    private void doPennyDrop(UserCalculatorMapping userCalculatorMapping) throws Exception {
        String uid = userCalculatorMapping.getUid();
        boolean isBeneficiaryNameMatched = false;

        if (uid != null) {
            BeneficiaryUser beneficiaryUser1 = null;
            BeneficiaryAccount beneficiaryAccount = null;
            if (userCalculatorMapping != null) {

                LOG.info("Get Beneficiary Mapping " + userCalculatorMapping.getBeneficiaryId());
                beneficiaryUser1 = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
                beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
                LOG.info("Get Beneficiary Details  " + beneficiaryUser1.getName());

                if (beneficiaryUser1 != null && beneficiaryAccount != null && beneficiaryAccount.getAccountType() != null) {
                    if (beneficiaryAccount.getAccountType().equals(AccountType.BANK_ACCOUNT)) {
                        if (beneficiaryAccount.getVerificationStatus().equals(VerificationStatus.VERIFICATION_PENDING)) {
                            LOG.info("Checking beneficiary name...start...");
                            BeneficiaryNameCheck beneficiaryNameCheck = startRemitterService.checkBeneficiaryName(String.valueOf(uid));
                            LOG.info("Checking beneficiary name...end...");
                            if (beneficiaryNameCheck != null && beneficiaryNameCheck.getNameInResponse() != null) {
                                LOG.info("Inside if condition...after checkbeneficiary");
                                BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(beneficiaryNameCheck.getBeneficiaryId());
                                if (beneficiaryUser.getName().equalsIgnoreCase(beneficiaryNameCheck.getNameInResponse())) {
                                    LOG.info("Inside if condition...beneficiary name matched");
                                    beneficiaryAccount.setVerificationStatus(VerificationStatus.VERIFIED);
                                    beneficiaryAccountService.update(beneficiaryAccount);
                                    isBeneficiaryNameMatched = true;

                                } else {
                                    LOG.info("Inside else condition...beneficiary name matched");
                                    beneficiaryAccount.setVerificationStatus(VerificationStatus.BENEFICIARY_NAME_NOT_MATCHED);
                                    beneficiaryAccountService.update(beneficiaryAccount);
                                    isBeneficiaryNameMatched = true;
                                }

                            }
                        } else {
                            isBeneficiaryNameMatched = true;
                        }
                    }
                }

                if (isBeneficiaryNameMatched) {
                    userCalculatorMapping.setStatus(TransactionStatus.CONFIRMED.name());
                } else {
                    userCalculatorMapping.setStatus(TransactionStatus.BENEFICIARY_NAME_NOT_MATCHED.name());
                }
                currencyService.updateCurrencyCalcMapping(userCalculatorMapping);
            }
        }
    }

    private void doAmlCheck(UserCalculatorMapping userCalculatorMapping) {
        String uid = userCalculatorMapping.getUid();
        PersonalDetails pd = personalDetailsService.getLatestByUser(userCalculatorMapping.getUser().getId());

        if (uid != null) {
            kycCheckService.amlCheck(String.valueOf(uid), Long.valueOf(pd.getId().toString()));
        }

        if ((Long) userCalculatorMapping.getId() != null) {
            if (userCalculatorMapping.getBeneficiaryId() != null) {
                kycCheckService.amlCheckForBeneficiary(userCalculatorMapping.getBeneficiaryId(), String.valueOf(uid));
            }
        }
    }

    private String getFirstName(Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
        return personalDetails.getFullName();
    }

    private void processExchangeRateFallBack(UserCalculatorMapping userCalcMapping) throws RecordNotFoundException {
        TransactionWorkflow transactionWorkflow = transactionWorkflowRepository.findByReferenceNoAndWorkflowStatusAndIsGuarantedRateApplicable(userCalcMapping.getRefId(), WorkflowStatus.STAGING_START, false);

        if (transactionWorkflow != null && !transactionWorkflow.isGuarantedRateApplicable()) {
            LOG.info("Transaction with reference number:::" + transactionWorkflow.getReferenceNo() + " is eligible for exchange rate fallback...");
            CurrencyConvert currencyConvert = new CurrencyConvert();
            currencyConvert.setFromCountryId(userCalcMapping.getFromCountryId());
            currencyConvert.setToCountryId(userCalcMapping.getToCountryId());
            currencyConvert.setUid(userCalcMapping.getUid());
            currencyConvert.setAmt(userCalcMapping.getTransferAmount());

            CurrencyCalculatorModel currencyCalculatorModel = currencyService.getCurrencyCalculatorDetails(currencyConvert);
            LOG.info("Revised fx base rate:::" + currencyCalculatorModel.getFxBaseRate());
            LOG.info("Revised margin:::" + currencyCalculatorModel.getMarginSpread());
            LOG.info("Revised fees:::" + currencyCalculatorModel.getStrTransactionFee());
            LOG.info("Revised rate given to transaction:::" + transactionWorkflow.getReferenceNo() + " is===>" + currencyCalculatorModel.getStrTotalConvertedValue());

            // Viren: TODO send across email for exchange rate fall back
            UserCalculatorMapping ucm = transactionWorkflow.getUserCalculatorMapping();
            String email = ucm.getEmail();
            final Map<String, Object> messageValueMap = new HashMap<>();
            String subject = "Change in Exchange Rate";
            LOG.info("Sending email for funds credited..." + ucm.getRefId());
            String templateName = "excange_rate_fallback_18";

            final String transactionBookingAmount = formatNumberWithCurrency(
                    CCY_FORMAT,
                    ucm.getTotalConvertedValue(),
                    ucm.getToCurrencyCode()
            );

            Calendar now = Calendar.getInstance();
            TimeZone timeZone = now.getTimeZone(); //display current TimeZone using getDisplayName() method of TimeZone class.
            String txnRateDate = "";

            if (timeZone.getDisplayName() == "Australia/Sydney") {
                txnRateDate = ucm.getGurrantedRateDate().toInstant().atZone(timeZone.toZoneId()).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
            } else {
                txnRateDate = ucm.getGurrantedRateDate().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));
            }

            messageValueMap.put("remitter_name", getFirstName(ucm.getUser().getId()));
            messageValueMap.put("refrence_number", ucm.getRefId());
            messageValueMap.put("receive_amount", transactionBookingAmount);
            messageValueMap.put("gurranted_rate_date", txnRateDate);

            try {
                mailService.sendEmailHtml(email, subject, templateName, messageValueMap);
            } catch (Exception ex) {
                LOG.info("Exchange rate email sending failed for txn ref id:" + ucm.getRefId());
                LOG.info("Error message:" + ex.getMessage());
            }
        }
    }

    private String formatNumberWithCurrency(final NumberFormat formatter, BigDecimal val, String ccy) {
        try {
            final String amountString = formatter.format(val);
            return ccy + " " + amountString;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return "N/A";
        }
    }

}

