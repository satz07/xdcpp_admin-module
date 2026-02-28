package com.adminremit.report.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.adminremit.aml.model.BeneficiaryAmlCheckResponse;
import com.adminremit.aml.model.RemitterAmlCheckResponse;
import com.adminremit.aml.repository.BeneficiaryAmlCheckApiResponseRepository;
import com.adminremit.aml.repository.RemitterAmlCheckApiResponseRepository;
import com.adminremit.auth.repository.BeneficiaryAccountRepository;
import com.adminremit.backoffice.model.PaymentType;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryNameCheck;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.repository.BeneficiaryNameCheckRepository;
import com.adminremit.beneficiary.repository.BeneficiaryUserRepository;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.common.models.Constants;
import com.adminremit.common.service.UtilitiesService;
import com.adminremit.common.util.DateUtil;
import com.adminremit.gbg.model.GbgNonDvsApiRequest;
import com.adminremit.gbg.repository.GBGVerificationResponseRepository;
import com.adminremit.gbg.repository.GbgNonDvsApiRequestRepository;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.masters.repository.PurposeListMasterRepository;
import com.adminremit.onfido.model.OnfidoCheckApiResponse;
import com.adminremit.onfido.repository.OnfidoCheckApiResponseRepository;
import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.model.WorkflowStatusNames;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.repository.TransferAccountRepository;
import com.adminremit.personaldetails.enums.GBGApiNames;
import com.adminremit.personaldetails.model.GBGVerificationRegisterVerificationResponse;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.repository.PersonalDetailsRepository;
import com.adminremit.personaldetails.repository.PersonalDocumentsRepository;
import com.adminremit.receiver.model.ReceiverTransactionModel;
import com.adminremit.receiver.repository.ReceiverTransactionalRepo;
import com.adminremit.report.compliance.dto.AnnualIncomeFromProfession;
import com.adminremit.report.compliance.dto.AverageTicketSize;
import com.adminremit.report.compliance.dto.ComplianceReportDTO;
import com.adminremit.report.compliance.dto.DailyLimit;
import com.adminremit.report.compliance.dto.DuplicateRegistration;
import com.adminremit.report.compliance.dto.HalfYearlyLimit;
import com.adminremit.report.compliance.dto.LastLoginIPDetails;
import com.adminremit.report.compliance.dto.LastTransactionDateAndTime;
import com.adminremit.report.compliance.dto.LastTransactionReceipientName;
import com.adminremit.report.compliance.dto.LastTransactionStatus;
import com.adminremit.report.compliance.dto.LimitConsumedTillDate;
import com.adminremit.report.compliance.dto.LimitEnhancementDocuments;
import com.adminremit.report.compliance.dto.MonthlyLimit;
import com.adminremit.report.compliance.dto.Profession;
import com.adminremit.report.compliance.dto.ProfessionType;
import com.adminremit.report.compliance.dto.ProfileAddress;
import com.adminremit.report.compliance.dto.ProfileDobDetails;
import com.adminremit.report.compliance.dto.ProfileFirstName;
import com.adminremit.report.compliance.dto.ProfileGender;
import com.adminremit.report.compliance.dto.ProfileLastName;
import com.adminremit.report.compliance.dto.ProfileMiddleName;
import com.adminremit.report.compliance.dto.ProfileNationality;
import com.adminremit.report.compliance.dto.PurposeOfRemittance;
import com.adminremit.report.compliance.dto.ReceipientModificationDate;
import com.adminremit.report.compliance.dto.ReceipientModificationLocation;
import com.adminremit.report.compliance.dto.ReceipientNameValidation;
import com.adminremit.report.compliance.dto.ReceipientNameValidationApiStatus;
import com.adminremit.report.compliance.dto.ReceipientOtherRemitter;
import com.adminremit.report.compliance.dto.ReceipientWatchListScreening;
import com.adminremit.report.compliance.dto.RegistrationCountry;
import com.adminremit.report.compliance.dto.RegistrationVsTxnIPDetails;
import com.adminremit.report.compliance.dto.RelationShip;
import com.adminremit.report.compliance.dto.RemitterBankAccountName;
import com.adminremit.report.compliance.dto.RemitterGbgDvs;
import com.adminremit.report.compliance.dto.RemitterGbgNonDvs;
import com.adminremit.report.compliance.dto.RemitterManualKyc;
import com.adminremit.report.compliance.dto.RemitterOnfidoDocumentCheckDetails;
import com.adminremit.report.compliance.dto.RemitterOnfidoDocumentExpiry;
import com.adminremit.report.compliance.dto.RemitterOnfidoSelfieCheck;
import com.adminremit.report.compliance.dto.RemitterWatchListScreening;
import com.adminremit.report.compliance.dto.SourceOfIncome;
import com.adminremit.report.compliance.dto.TotalReceipients;
import com.adminremit.report.compliance.dto.TransactionAmount;
import com.adminremit.report.compliance.dto.TransactionCountry;
import com.adminremit.report.compliance.dto.WeeklyLimit;
import com.adminremit.report.compliance.dto.YearlyLimit;
import com.adminremit.report.vo.ComplainceVO;
import com.adminremit.user.enums.UserLoginAuditStatus;
import com.adminremit.user.model.DdFileInfo;
import com.adminremit.user.model.DocumentVerification;
import com.adminremit.user.model.EDDMaster;
import com.adminremit.user.model.IPTagging;
import com.adminremit.user.model.UserLoginAudit;
import com.adminremit.user.repository.EDDRepository;
import com.adminremit.user.repository.FileRepository;
import com.adminremit.user.repository.LoginAuditRepo;
import com.adminremit.user.repository.RemittUsersRepository;
import com.adminremit.user.repository.UserCalculatorMappingRepository;
import com.adminremit.user.repository.UserDocVerificationRepo;
import com.adminremit.user.repository.UserIPRepository;
import com.andremit.remitt.gbg.enums.GBGRuleIds;

@Service
public class ComplainceServiceImpl implements ComplainceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserCalculatorMappingRepository userCalculatorMappingRepository;
    
    @Autowired
    private LoginAuditRepo loginAuditRepo;
    
    @Autowired
    private UserIPRepository ipRepository;
    
    @Autowired
    private UserDocVerificationRepo userDocVerificationRepo;
    
    @Autowired
    private OnfidoCheckApiResponseRepository onfidoCheckApiResponseRepository;

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;
    
    @Autowired
    private CountriesRepository countriesRepository;
    
    @Autowired
    private GbgNonDvsApiRequestRepository gbgNonDvsApiRequestRepository;
    
    @Autowired
    private GBGVerificationResponseRepository gbgVerificationResponseRepository;
    
    @Autowired
    private PersonalDocumentsRepository personalDocumentsRepository;
    
    @Autowired
    private TransferAccountRepository transferAccountRepository;
    
    @Autowired
    private BeneficiaryUserRepository beneficiaryUserRepository;
    
    @Autowired
    private BeneficiaryNameCheckRepository beneficiaryNameCheckRepository;
    
    @Autowired
    private BeneficiaryAccountRepository beneficiaryAccountRepository;
    
    @Autowired
    private RemittUsersRepository remittUsersRepository;
    
    @Autowired
    private RemitterAmlCheckApiResponseRepository remitterAmlCheckApiResponseRepository;
    
    @Autowired
    private BeneficiaryAmlCheckApiResponseRepository beneficiaryAmlCheckApiResponseRepository;
    
    @Autowired
    private ReceiverTransactionalRepo receiverTransactionalRepo;
    
    @Autowired
    private PurposeListMasterRepository purposeListMasterRepository;
    
    @Autowired
    private EDDRepository eDDRepository;
    
    @Autowired
    private TransactionWorkflowRepository transactionWorkflowRepository;
    
    @Autowired
    private UtilitiesService utilitiesService;
    
    @Autowired
    private FileRepository fileDBRepository;
   
    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;
    
    private static Map<String, String> stateMap;
    
    static {
    	stateMap = new HashMap<>();
    	stateMap.put("ACT", "Australian Capital Territory");
    	stateMap.put("NT", "Northern Territory");
    	stateMap.put("SA", "South Australia");
    	stateMap.put("TAS", "Tasmania");
    	stateMap.put("WA", "Western Australia");
    	stateMap.put("VIC", "Victoria");
    	stateMap.put("QLD", "Queensland");
    	stateMap.put("NSW", "New South Wales");        
    }
    
    private static Map<Integer, String> relationshipMap;
    
    static {
    	relationshipMap = new HashMap<>();
    	relationshipMap.put(1, "Child");
    	relationshipMap.put(2, "Daughter-in-law");
    	relationshipMap.put(3, "Father-in-law");
    	relationshipMap.put(4, "Fiance");
    	relationshipMap.put(5, "Fiancee");
    	relationshipMap.put(6, "Grandchild");
    	relationshipMap.put(7, "Grandparent");
    	relationshipMap.put(8, "Mother-in-law");
    	relationshipMap.put(9, "Parent");
    	relationshipMap.put(10, "Sibling");
    	relationshipMap.put(11, "Sister-in-law");
    	relationshipMap.put(12, "Son-in-law");
    	relationshipMap.put(13, "Spouse");    	        
    }
//    @Override
//    public List<ReconciliationVO> getReconciliationReport() {
//        return jdbcTemplate.query(getQueryString(), (rs, rowNum) -> {
//            ReconciliationVO reconciliationVO = new ReconciliationVO();
//            reconciliationVO.setValueDate(rs.getDate("value_date"));
//            reconciliationVO.setCurrency(rs.getString("from_currency_code"));
//            reconciliationVO.setTransactionType(rs.getString("user_account_type"));
//            reconciliationVO.setAmount(rs.getDouble("amt_received"));
//            reconciliationVO.setTransactionRef(rs.getString("reference_id"));
//            reconciliationVO.setBankRef(rs.getString("bank_ref"));
//            reconciliationVO.setAccountName(rs.getString("sender_name"));
//            reconciliationVO.setUpdatedBy(rs.getString("updated_by"));
//            reconciliationVO.setUpdatedOn(rs.getDate("update_at"));
//            return reconciliationVO;
//        });
//    }


    private String getQueryString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select pd.country_name as REG_COUNTRY, u.country_name, uld_ip.session_ip,");
        stringBuilder.append("pd.nationality, dd.nationality as reg_nationality, pd.full_name,pd.last_name,pd.middle_name,  ");
        stringBuilder.append("pd.middle_name,  ");
        stringBuilder.append("dd.document_type, pdoc.document_type as PERSONAL_DOC_TYPE,  ");
        stringBuilder.append("pd.dob,pd.gender,  ");
        stringBuilder.append("pd.flat_number, pd.street_number, pd.street_name, pd.street_type, pd.province, ");
        stringBuilder.append("dd.first_name as d_first_name,dd.last_name as d_last_name,dd.middle_name as d_middle_name,   ");
        stringBuilder.append("dd.date_of_birth ,dd.gender AS D_GENDER,dd.result,  ");
        stringBuilder.append("tad.sender_name, tad.account_number,  ");
        stringBuilder.append("uld.bene_id_add_ip as reg_ip,uld_bu.bene_id_edit_ip as bu_reg_ip,  ");
        stringBuilder.append("bgp.api_name, bgp.verification_status,  ");
        stringBuilder.append("CASE  ");
        stringBuilder.append("WHEN bu.relationship=1  THEN 'FRIENDS'  ");
        stringBuilder.append("WHEN bu.relationship=2  THEN 'FAMILY'  ");
        stringBuilder.append("WHEN bu.relationship=3  THEN 'WIFE'  ");
        stringBuilder.append("WHEN bu.relationship=4  THEN 'HUSBAND'  ");
        stringBuilder.append("WHEN bu.relationship=5  THEN 'SON'  ");
        stringBuilder.append("WHEN bu.relationship=6  THEN 'BROTHER'  ");
        stringBuilder.append("WHEN bu.relationship=7  THEN 'VENDOR'  ");
        stringBuilder.append("ELSE  'NA'  ");
        stringBuilder.append("END as relationship, ");
        stringBuilder.append("aml.aml_key,plm.purpose,edd.profession_type,edd.annual_income,COALESCE (edd.source_of_income, edd.other_income) as source_of_income, ");
        stringBuilder.append("edd.source_income, ");
        stringBuilder.append("sum_transfer_amount,avg_transfer_amount, last_tw.workflow_state, last_txn.create_at, last_txn.reference_id, ");
        stringBuilder.append("ucm.beneficiary_name, bu_count.bu_id_count,ucm.transfer_amount, ");
        stringBuilder.append("d_limit.daily_limit, week_limit.daily_limit as weekly_limit, month_limit.daily_limit as monthy_limit,half_year_limit.daily_limit as half_yearly_limit, ");
        stringBuilder.append("year_limit.daily_limit as yearly_limit, ");
        stringBuilder.append("glm.daily_limit as master_daily_limit, glm.monthly_limit as master_monthly_limit, glm.weekly_limit as master_weekly_limit, glm.quarterly_limit as quarterly_limit, ");
        stringBuilder.append("u.country_id, ");
        stringBuilder.append("bu.full_name as bu_full_name, bu.create_at as bu_create_at, bu.update_at as bu_update_at, bnc.name_in_response, ");
        stringBuilder.append("ac.country_name as transaction_country, bnc.status as check_status ");
        stringBuilder.append("from user_calc_mapping ucm ");
        // WEB3 migration changes - use dynamic table name for countries
        String countriesTable = com.adminremit.config.Web3TableNameUtil.resolveTableName("admin_countries");
        stringBuilder.append("LEFT JOIN (select pd.*,ac.country_name from personal_details pd INNER JOIN " + countriesTable + " ac ON ac.country_code=pd.country) pd on ucm.user_id = pd.user_id ");
        stringBuilder.append("LEFT JOIN (select *, ROW_NUMBER () OVER (PARTITION BY personal_details_id ORDER BY create_at desc) as row_num from gbg_verification_result) bgp on bgp.personal_details_id = pd.id and row_num=1 ");
        stringBuilder.append("LEFT join (select u.*,ac.country_name from users u INNER JOIN " + countriesTable + " ac ON ac.id=u.country_id) u on u.id = ucm.user_id ");
        stringBuilder.append("LEFT JOIN transfered_acc_details tad on tad.transaction_ref =  ucm.reference_id  ");
        stringBuilder.append("LEFT JOIN document_details dd on u.email = dd.user_id  ");
        stringBuilder.append("LEFT JOIN personal_documents pdoc on pdoc.personal_details =pd.id and dd.document_type = pdoc.document_type  ");
        stringBuilder.append("LEFT JOIN (select *, ROW_NUMBER () OVER (PARTITION BY email ORDER BY timestamp desc) as row_num from user_login_details where audit_type = 'BENEFICIARY_ADD') uld on uld.email =  dd.user_id and uld.row_num = 1 ");
        stringBuilder.append("LEFT JOIN (select *, ROW_NUMBER () OVER (PARTITION BY email ORDER BY timestamp desc) as row_num from user_login_details where audit_type = 'BENEFICIARY_UPDATE') uld_bu on uld_bu.email =  dd.user_id and uld_bu.row_num = 1  ");
        stringBuilder.append("LEFT JOIN (select *, ROW_NUMBER () OVER (PARTITION BY email ORDER BY timestamp desc) as row_num from user_login_details where audit_type = 'LOGIN') uld_ip on uld_ip.email =  dd.user_id and uld_ip.row_num = 1  ");
        stringBuilder.append("LEFT JOIN (SELECT STRING_AGG(aml.key,',' ORDER BY aml.key) as aml_key, user_id ");
        stringBuilder.append("FROM  (select distinct aml.key,user_id from aml_details aml) aml ");
        stringBuilder.append("group by user_id) aml on aml.user_id =  dd.user_id ");
        stringBuilder.append("LEFT JOIN beneficiary_user bu on bu.user_id = ucm.user_id ");
        stringBuilder.append("LEFT JOIN beneficiary_name_check bnc on bnc.beneficiary_id = bu.id ");
        stringBuilder.append("LEFT JOIN (select ptd.*, plm.purpose from receiver_transaction_details ptd inner join purpose_list_master plm on ptd.receiver_purpose_id= plm.id) plm on plm.user_id = ucm.user_id ");
        stringBuilder.append("LEFT JOIN edd_master edd ON edd.user_calc_mapping =  ucm.uid ");
        stringBuilder.append("LEFT JOIN (select sum(transfer_amount) as sum_transfer_amount,ucm.reference_id,avg(transfer_amount) as avg_transfer_amount, count(*) as transaction_count from user_calc_mapping ucm ");
        stringBuilder.append("left join transaction_worflow tw on  ucm.reference_id = tw.reference_id and  ucm.reference_id is not null and is_completed=false ");
        stringBuilder.append("group by ucm.reference_id) sta on sta.reference_id = ucm.reference_id ");
        stringBuilder.append("LEFT JOIN (select create_at,reference_id, ROW_NUMBER () OVER (ORDER BY create_at desc) as row_num from transaction_worflow where workflow_state = 'STAGING_START') last_txn on last_txn.reference_id = ucm.reference_id and last_txn.row_num = 1 ");
        stringBuilder.append("left join (select create_at,reference_id,workflow_state,  ROW_NUMBER () OVER (ORDER BY create_at desc) as row_num from transaction_worflow where is_completed=true) last_tw on  ucm.reference_id = last_tw.reference_id and last_tw.row_num = 1 ");
        stringBuilder.append("left join (select count(id) as bu_id_count, user_id from beneficiary_user group by id) bu_count on bu_count.user_id = ucm.user_id ");
        stringBuilder.append("left join (select sum(transfer_amount) as daily_limit,user_id  from user_calc_mapping ucm where DATE(created_at) >= CURRENT_DATE AND DATE(created_at) < CURRENT_DATE + INTERVAL '1 DAY' group by user_id) d_limit on d_limit.user_id = ucm.user_id ");
        stringBuilder.append("left join (select sum(transfer_amount) as daily_limit,user_id  from user_calc_mapping ucm where DATE(created_at) <= CURRENT_DATE AND DATE(created_at) > CURRENT_DATE - INTERVAL '7 DAY' group by user_id) week_limit on week_limit.user_id = ucm.user_id ");
        stringBuilder.append("left join (select sum(transfer_amount) as daily_limit,user_id  from user_calc_mapping ucm where DATE(created_at) <= CURRENT_DATE AND DATE(created_at) > CURRENT_DATE - INTERVAL '31 DAY' group by user_id) month_limit on month_limit.user_id = ucm.user_id ");
        stringBuilder.append("left join (select sum(transfer_amount) as daily_limit,user_id  from user_calc_mapping ucm where DATE(created_at) <= CURRENT_DATE AND DATE(created_at) > CURRENT_DATE - INTERVAL '133 DAY' group by user_id) half_year_limit on half_year_limit.user_id = ucm.user_id ");
        stringBuilder.append("left join (select sum(transfer_amount) as daily_limit,user_id  from user_calc_mapping ucm where DATE(created_at) <= CURRENT_DATE AND DATE(created_at) > CURRENT_DATE - INTERVAL '365 DAY' group by user_id) year_limit on year_limit.user_id = ucm.user_id ");
        stringBuilder.append("LEFT JOIN global_limit_master glm on ucm.payment_id = glm.payment_code and  ucm.paymentmode_id = glm.receive_code  ");
        stringBuilder.append("LEFT JOIN " + countriesTable + " ac ON ac.country_code=ucm.from_country_code ");
        stringBuilder.append("where ucm.reference_id = ? ");
        stringBuilder.append("order by bgp.create_at desc limit 1 ");
        return stringBuilder.toString();
    }

    @Override
    public ComplainceVO getComplainceReport(String txnRef) {
        return jdbcTemplate.query(getQueryString(), preparedStatement -> preparedStatement.setString(1, txnRef),
                resultSet -> {
                    if (resultSet.next()) {
                        ComplainceVO complainceVO = new ComplainceVO();
                        complainceVO.setNationality1(resultSet.getString("nationality"));
                        complainceVO.setNationality2(resultSet.getString("reg_nationality"));
                        complainceVO.setFullName(resultSet.getString("full_name"));
                        complainceVO.setFirstName(resultSet.getString("d_first_name"));
                        complainceVO.setLastName1(resultSet.getString("last_name"));
                        complainceVO.setLastName2(resultSet.getString("d_last_name"));
                        complainceVO.setMiddleName1(resultSet.getString("middle_name"));
                        complainceVO.setMiddleName2(resultSet.getString("d_middle_name"));
                        complainceVO.setRegistrationCountry1(resultSet.getString("REG_COUNTRY"));
                        complainceVO.setRegistrationCountry2(resultSet.getString("country_name"));
                        complainceVO.setDateOfBirth1(resultSet.getString("dob"));
                        complainceVO.setDateOfBirth2(resultSet.getString("date_of_birth"));
                        complainceVO.setGender1(resultSet.getString("gender"));
                        complainceVO.setGender2(resultSet.getString("D_GENDER"));
                        complainceVO.setSenderName(resultSet.getString("sender_name"));
                        complainceVO.setAcNumber(resultSet.getString("account_number"));
                        complainceVO.setKycRow1DocType1(resultSet.getString("document_type"));
                        complainceVO.setFlatNumber(resultSet.getString("flat_number"));
                        complainceVO.setStreetNumber(resultSet.getString("street_number"));
                        complainceVO.setStreetName(resultSet.getString("street_name"));
                        complainceVO.setStreetType(resultSet.getString("street_type"));
                        complainceVO.setProvince(resultSet.getString("province"));
                        complainceVO.setRegIp1(resultSet.getString("reg_ip"));
                        complainceVO.setRegIp2(resultSet.getString("bu_reg_ip"));
                        complainceVO.setAmlKey(resultSet.getString("aml_key"));
                        complainceVO.setRelationship(resultSet.getString("relationship"));
                        complainceVO.setPurpose(resultSet.getString("purpose"));
                        complainceVO.setProfessionType(resultSet.getString("profession_type"));
                        complainceVO.setAnnualIncome(resultSet.getString("annual_income"));
                        complainceVO.setSourceOfIncome(resultSet.getString("source_of_income"));
                        complainceVO.setSourceOfFund("source_income");
                        complainceVO.setSumTransferAmount(resultSet.getString("sum_transfer_amount"));
                        complainceVO.setAvgTransferAmount(resultSet.getString("avg_transfer_amount"));
                        complainceVO.setWorkflowState(resultSet.getString("workflow_state"));
                        complainceVO.setCreateAt(resultSet.getString("create_at"));
                        complainceVO.setReferenceId(resultSet.getString("reference_id"));
                        complainceVO.setBeneficiaryName(resultSet.getString("beneficiary_name"));
                        complainceVO.setBuIdCount(resultSet.getString("bu_id_count"));
                        complainceVO.setTransferAmount(resultSet.getString("transfer_amount"));
                        complainceVO.setDailyLimit(resultSet.getString("daily_limit"));
                        complainceVO.setWeeklyLimit(resultSet.getString("weekly_limit"));
                        complainceVO.setMonthLimit(resultSet.getString("monthy_limit"));
                        complainceVO.setHalfYearlyLimit(resultSet.getString("half_yearly_limit"));
                        complainceVO.setYearlyLimit(resultSet.getString("yearly_limit"));
                        complainceVO.setMasterDailyLimit(resultSet.getString("master_daily_limit"));
                        complainceVO.setMasterWeeklyLimit(resultSet.getString("master_weekly_limit"));
                        complainceVO.setMasterMonthLimit(resultSet.getString("master_monthly_limit"));
                        complainceVO.setMasterHalfYearlyLimit(resultSet.getString("quarterly_limit"));
                        complainceVO.setMasterYearlyLimit(resultSet.getString("quarterly_limit"));
                        complainceVO.setLoginIp(resultSet.getString("session_ip"));
                        complainceVO.setValidationResponse(resultSet.getString("result"));
                        complainceVO.setValidationResponse(resultSet.getString("result"));
                        complainceVO.setApiName(resultSet.getString("api_name"));
                        complainceVO.setVerificationStatus(resultSet.getString("verification_status"));
                        complainceVO.setCheckFullName(resultSet.getString("bu_full_name"));
                        complainceVO.setCheckResponse(resultSet.getString("name_in_response"));
                        complainceVO.setRecipientModifiedOn1(resultSet.getString("bu_create_at"));
                        complainceVO.setRecipientModifiedOn2(resultSet.getString("bu_update_at"));
                        complainceVO.setTxnCountry1(resultSet.getString("transaction_country"));
                        complainceVO.setCheckStatus(resultSet.getString("check_status"));
                        return complainceVO;
                    }
                    return null;
                });
    }

	@Override
	public ComplianceReportDTO extractReport(String refId) {
		Map<String, Object> objectMap = new HashMap<>();
		ComplianceReportDTO complianceReportDTO = new ComplianceReportDTO();
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingRepository.findByRefId(refId);
		
		populateRegistrationCountry(objectMap, complianceReportDTO, userCalculatorMapping);
		populateLastLoginOfUser(objectMap, complianceReportDTO, userCalculatorMapping);
		populateTxnCountry(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRegistrationVsTxnLocation(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterKycOnfidoDocCheck(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterKycOnfidoDocExpiry(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterKycOnfidoSelfieCheck(objectMap, complianceReportDTO, userCalculatorMapping);
		populateProfileDetails(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterGbgNonDvs(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterGbgDvs(objectMap, complianceReportDTO, userCalculatorMapping);
		populateManualKyc(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterBankAccountName(objectMap, complianceReportDTO, userCalculatorMapping, refId);
		populateDuplicateRegistration(objectMap, complianceReportDTO, userCalculatorMapping);
		populateReceipientNameValidation(objectMap, complianceReportDTO, userCalculatorMapping);
		populateReceipientNameValidationApiStatus(objectMap, complianceReportDTO, userCalculatorMapping);
		populateReceipientModificationLocation(objectMap, complianceReportDTO, userCalculatorMapping);
		populateReceipientModificationDate(objectMap, complianceReportDTO, userCalculatorMapping);		
		populateReceipientOthersRemitter(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRemitterWatchListScreening(objectMap, complianceReportDTO, userCalculatorMapping);
		populateReceipientWatchListScreening(objectMap, complianceReportDTO, userCalculatorMapping);
		populateRelationShip(objectMap, complianceReportDTO, userCalculatorMapping);
		populatePurposeOfRemittance(objectMap, complianceReportDTO, userCalculatorMapping);
		populateLimitEnhancement(objectMap, complianceReportDTO, userCalculatorMapping);
		populateLimitConsumedTillDate(objectMap, complianceReportDTO, userCalculatorMapping);
		populateAverageTicketSize(objectMap, complianceReportDTO, userCalculatorMapping);
		populateLastTransactionDetails(objectMap, complianceReportDTO, userCalculatorMapping, refId);
		populateTotalRecipients(objectMap, complianceReportDTO, userCalculatorMapping);
		populateCurrentTxnAmount(objectMap, complianceReportDTO, userCalculatorMapping);
		populateLimits(objectMap, complianceReportDTO, userCalculatorMapping);
		
		return complianceReportDTO;
	}

	private void populateLimits(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		DailyLimit dailyLimit = new DailyLimit();
		WeeklyLimit weeklyLimit = new WeeklyLimit();
		MonthlyLimit monthlyLimit = new MonthlyLimit();
		HalfYearlyLimit halfYearlyLimit = new HalfYearlyLimit();
		YearlyLimit yearlyLimit = new YearlyLimit();
		
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(remittUser!=null) {
			Map<String,String> limitsMap = utilitiesService.getLimits(remittUser.getEmail());
			objectMap.put("avgTicketSize", limitsMap.get("AverageTicketSize"));
			if(limitsMap!=null) {
				dailyLimit.setDailyLimitAsPerCurrentTxnAmt(limitsMap.get("DailyUserLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("DailyUserLimit"))) : null);
				dailyLimit.setDefinedDailyLimit(limitsMap.get("DailyLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("DailyLimit"))) : null);
				dailyLimit.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
				
				weeklyLimit.setWeeklyLimitAsPerCurrentTxnAmt(limitsMap.get("WeeklyUserLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("WeeklyUserLimit"))) : null);
				weeklyLimit.setDefinedWeeklyLimit(limitsMap.get("WeeklyLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("WeeklyLimit"))) : null);
				weeklyLimit.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
				
				monthlyLimit.setMonthlyLimitAsPerCurrentTxnAmt(limitsMap.get("MonthlyUserLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("MonthlyUserLimit"))) : null);
				monthlyLimit.setDefinedMonthlyLimit(limitsMap.get("MonthlyLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("MonthlyLimit"))) : null);
				monthlyLimit.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
				
				halfYearlyLimit.setHalfYearlyLimitAsPerCurrentTxnAmt(limitsMap.get("QuaterlyUserLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("QuaterlyUserLimit"))) : null);
				halfYearlyLimit.setDefinedhalfYearlyLimit(limitsMap.get("QuarterlyLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("QuarterlyLimit"))) : null);
				halfYearlyLimit.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
				
				yearlyLimit.setYearlyLimitAsPerCurrentTxnAmt(limitsMap.get("AnnuallyUserLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("AnnuallyUserLimit"))) : null);
				yearlyLimit.setDefinedYearlyLimit(limitsMap.get("AnnualLimit")!=null ? formatNumber(new BigDecimal(limitsMap.get("AnnualLimit"))) : null);
				yearlyLimit.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
			}				
		}		
		
		complianceReportDTO.setDailyLimit(dailyLimit);
		complianceReportDTO.setWeeklyLimit(weeklyLimit);
		complianceReportDTO.setMonthlyLimit(monthlyLimit);
		complianceReportDTO.setHalfYearlyLimit(halfYearlyLimit);
		complianceReportDTO.setYearlyLimit(yearlyLimit);
	}

	private void populateCurrentTxnAmount(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		TransactionAmount transactionAmount = new TransactionAmount();
		
		if(userCalculatorMapping!=null) {
			transactionAmount.setTransactionAmount(userCalculatorMapping.getTransferAmount()!=null ? formatNumber(userCalculatorMapping.getTransferAmount()) : null);
			transactionAmount.setTransactionCurrency(userCalculatorMapping.getFromCurrencyCode());
		}
		
		complianceReportDTO.setTransactionAmount(transactionAmount);
	}

	private void populateTotalRecipients(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		TotalReceipients totalReceipients = new TotalReceipients();
		if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null) {
			long recipentCount = beneficiaryUserRepository.getCountOfBeneficiaryForUser(userCalculatorMapping.getUser().getId());
			objectMap.put("recipentCount", recipentCount);
			
			totalReceipients.setTotalNumberOfReceipients(String.valueOf(recipentCount));
		}
		complianceReportDTO.setTotalReceipients(totalReceipients);
	}

	@SuppressWarnings("unchecked")
	private void populateLastTransactionDetails(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping, String refId) {
		LastTransactionDateAndTime lastTransactionDateAndTime = new LastTransactionDateAndTime();
		LastTransactionStatus lastTransactionStatus = new LastTransactionStatus();
		LastTransactionReceipientName lastTransactionReceipientName = new LastTransactionReceipientName();
		
		List<UserCalculatorMapping> userCalculatorMappingList = objectMap.get("userCalculatorMappingList")!=null ? (List<UserCalculatorMapping>)objectMap.get("userCalculatorMappingList") : null;
		
		if(userCalculatorMappingList!=null && !userCalculatorMappingList.isEmpty()) {
			List<String> referenceIdList = userCalculatorMappingList.stream()
																	.filter(u->u!=null)
																	.map(u->u.getRefId())
																	.collect(Collectors.toList());
			
			if(referenceIdList!=null && !referenceIdList.isEmpty()) {
				referenceIdList.remove(refId);
				
				if(!referenceIdList.isEmpty()) {
					TransactionWorkflow transactionWorkflow= transactionWorkflowRepository.findTopByReferenceNoInAndWorkflowStatusOrderByIdDesc(referenceIdList, WorkflowStatus.STAGING_START);
					objectMap.put("lastTransaction", transactionWorkflow);
					
					if(transactionWorkflow!=null) {						
						if(transactionWorkflow.getCreateAt()!=null) {
							LocalDateTime bookingDateTime = DateUtil.getDateTimeInAESTZone(new java.sql.Timestamp(transactionWorkflow.getCreateAt().getTime()).toLocalDateTime());
							if(bookingDateTime!=null) {
								lastTransactionDateAndTime.setLastTransactionDateTime(DateUtil.formatLocalDateTime(bookingDateTime, Constants.AEST_DATE_TIME_PATTERN).toUpperCase());
							}
						}
						
						TransactionWorkflow lastStatusTransactionWorkflow = transactionWorkflowRepository.findTopByReferenceNoOrderByIdDesc(transactionWorkflow.getReferenceNo());
						objectMap.put("lastStatusTransactionWorkflow", lastStatusTransactionWorkflow);
						
						TransactionWorkflow txnWorkflow = objectMap.get("lastStatusTransactionWorkflow")!=null ? (TransactionWorkflow)objectMap.get("lastStatusTransactionWorkflow") : null;
						
						if(txnWorkflow!=null && transactionWorkflow.getWorkflowStatus()!=null) {
							WorkflowStatusNames workflowStatusNames = WorkflowStatusNames.valueOf(transactionWorkflow.getWorkflowStatus().name());
							
							if(workflowStatusNames!=null) {
								lastTransactionStatus.setLastTransactionStatus(workflowStatusNames.getName());
							}							
						}					
						
						if(transactionWorkflow.getUserCalculatorMapping()!=null) {
							Optional<BeneficiaryUser> beneficiaryUserOptional = beneficiaryUserRepository.findById(transactionWorkflow.getUserCalculatorMapping().getBeneficiaryId());
							
							if(beneficiaryUserOptional.isPresent()) {
								BeneficiaryUser beneficiaryUser = beneficiaryUserOptional.get();
								lastTransactionReceipientName.setLastTxnReceipientName(beneficiaryUser.getName());
							}							
						}
					}
				}
			}			
		}
		
		complianceReportDTO.setLastTransactionDateAndTime(lastTransactionDateAndTime);
		complianceReportDTO.setLastTransactionStatus(lastTransactionStatus);
		complianceReportDTO.setLastTransactionReceipientName(lastTransactionReceipientName);		
	}

	private void populateAverageTicketSize(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		AverageTicketSize averageTicketSize = new AverageTicketSize();
		
		String calculatedAverageTicketSize = (String)objectMap.get("avgTicketSize");
		averageTicketSize.setAvgRemittersProcessedTxns(calculatedAverageTicketSize!=null ? formatNumber(new BigDecimal(calculatedAverageTicketSize)) : null);
		averageTicketSize.setTransactionCurrency(userCalculatorMapping!=null ? userCalculatorMapping.getFromCurrencyCode() : null);
		
		complianceReportDTO.setAverageTicketSize(averageTicketSize);		
	}

	private void populateLimitConsumedTillDate(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		LimitConsumedTillDate limitConsumedTillDate = new LimitConsumedTillDate();
		
		if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null) {
			List<UserCalculatorMapping> userCalculatorMappingList = userCalculatorMappingRepository.getAllReferenceIdForUser(userCalculatorMapping.getUser().getId());
			objectMap.put("userCalculatorMappingList", userCalculatorMappingList);
			
			List<String> referenceIdList = userCalculatorMappingList.stream()
																	.filter(u->u!=null)
					                                                .map(u->u.getRefId())
					                                                .collect(Collectors.toList());
			
			if(referenceIdList!=null && !referenceIdList.isEmpty()) {
				List<TransactionWorkflow> transactionWorkflowList = transactionWorkflowRepository.findAllCancelledTransactions(referenceIdList);
				
				if(transactionWorkflowList!=null && !transactionWorkflowList.isEmpty()) {
					List<String> cancelledRefIdList = transactionWorkflowList.stream()
							                                                 .filter(t->t!=null)
							                                                 .map(t->t.getReferenceNo())
							                                                 .collect(Collectors.toList());
					
					if(cancelledRefIdList!=null && referenceIdList!=null) {
						referenceIdList.removeAll(cancelledRefIdList);
					}
				}
				
				if(referenceIdList!=null && !referenceIdList.isEmpty()) {
					Optional<BigDecimal> totalAmountOptional = userCalculatorMappingList.stream()
			  																			.filter(u->(u!=null && referenceIdList.contains(u.getRefId())))
			  																			.map(u->u.getTransferAmount())
			  																			.reduce(BigDecimal::add);
					
					if(totalAmountOptional.isPresent()) {
						BigDecimal totalAmount = totalAmountOptional.get();
						limitConsumedTillDate.setLimitConsumed(totalAmount!=null ? formatNumber(totalAmount) : null);
						limitConsumedTillDate.setTransactionCurrency(userCalculatorMapping.getFromCurrencyCode());
					}
				}
			}
		}
		complianceReportDTO.setLimitConsumedTillDate(limitConsumedTillDate);		
	}

	private String formatNumber(BigDecimal amount) {
		return new DecimalFormat("#,###.00").format(amount);
	}

	private void populateLimitEnhancement(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		
		SourceOfIncome sourceOfIncome = new SourceOfIncome();
		Profession profession = new Profession();
		ProfessionType professionType = new ProfessionType();
		AnnualIncomeFromProfession annualIncomeFromProfession = new AnnualIncomeFromProfession();
		LimitEnhancementDocuments limitEnhancementDocuments = new LimitEnhancementDocuments();
		
		
		if(userCalculatorMapping!=null) {
			EDDMaster eddMaster = eDDRepository.findByUserCalculatorMapping(userCalculatorMapping.getUid());
			objectMap.put("eddMaster", eddMaster);
			
			if(eddMaster!=null) {
				sourceOfIncome.setSourceOfIncome(eddMaster.getSourceIncome());
				if(Constants.OTHER_SOURCE_OF_INCOME.equalsIgnoreCase(sourceOfIncome.getSourceOfIncome())) {
					sourceOfIncome.setOtherIncome(eddMaster.getOtherIncome());
				}
				profession.setProfession(eddMaster.getProfession());
				
				professionType.setProfessionType(eddMaster.getProfessionType());
				if(Constants.OTHER_PROFESSION_TYPE.equalsIgnoreCase(professionType.getProfessionType())) {
					professionType.setOtherProfession(eddMaster.getOtherProfession());
				}
				
				annualIncomeFromProfession.setAnnualIncome(eddMaster.getAnnualIncome());
			}
			
			if(userCalculatorMapping.getUser()!=null) {
				List<DdFileInfo> fileInfoList = fileDBRepository.findAllByUserId(userCalculatorMapping.getUser().getId());
				
				if(fileInfoList!=null && !fileInfoList.isEmpty()) {
					if(fileInfoList.size() == 2) {
						
						if(fileInfoList.get(0).getImageUrl()!=null) {
							String[] fileName1LocationArray = fileInfoList.get(0).getImageUrl().split("/");
							
							if(fileName1LocationArray!=null && fileName1LocationArray.length!=0) {
								String fileName1 = fileName1LocationArray[fileName1LocationArray.length-1];
								limitEnhancementDocuments.setLinkToDocs1(fileInfoList.get(0).getImageUrl());
								limitEnhancementDocuments.setDoc1FileName(fileName1);
							}
						}
							
						if(fileInfoList.get(1).getImageUrl()!=null) {
							String[] fileName2LocationArray = fileInfoList.get(1).getImageUrl().split("/");
							
							if(fileName2LocationArray!=null && fileName2LocationArray.length!=0) {
								String fileName2 = fileName2LocationArray[fileName2LocationArray.length-1];
								limitEnhancementDocuments.setLinkToDocs2(fileInfoList.get(1).getImageUrl());
								limitEnhancementDocuments.setDoc2FileName(fileName2);
							}
						}
							
						
					}else {
						if(fileInfoList.get(0).getImageUrl()!=null) {
							String[] fileName1LocationArray = fileInfoList.get(0).getImageUrl().split("/");
							
							if(fileName1LocationArray!=null && fileName1LocationArray.length!=0) {
								String fileName1 = fileName1LocationArray[fileName1LocationArray.length-1];
								limitEnhancementDocuments.setLinkToDocs1(fileInfoList.get(0).getImageUrl());
								limitEnhancementDocuments.setDoc1FileName(fileName1);
							}
						}						
					}					
				}
			}			
		}
		complianceReportDTO.setSourceOfIncome(sourceOfIncome);
		complianceReportDTO.setProfession(profession);
		complianceReportDTO.setProfessionType(professionType);
		complianceReportDTO.setAnnualIncomeFromProfession(annualIncomeFromProfession);
		complianceReportDTO.setLimitEnhancementDocuments(limitEnhancementDocuments);
	}

	private void populatePurposeOfRemittance(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		PurposeOfRemittance purposeOfRemittance = new PurposeOfRemittance();
		
		if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null) {
			Optional<ReceiverTransactionModel> receiverTransactionModelOptional = receiverTransactionalRepo.findByUidAndUserId(userCalculatorMapping.getId(), userCalculatorMapping.getUser().getId());
			if(receiverTransactionModelOptional.isPresent()) {
				ReceiverTransactionModel receiverTransactionModel = receiverTransactionModelOptional.get();
				objectMap.put("receiverTransactionModel", receiverTransactionModel);
				
				if(receiverTransactionModel!=null) {
					Optional<PurposeListMaster> purposeListMasterOptional = purposeListMasterRepository.findById(receiverTransactionModel.getPurposeList());
					if(purposeListMasterOptional.isPresent()) {
						PurposeListMaster purposeListMaster = purposeListMasterOptional.get();
						objectMap.put("purposeListMaster", purposeListMaster);
						
						if(purposeListMaster!=null) {
							purposeOfRemittance.setPurpose(purposeListMaster.getPurpose());
						}
					}
				}
				
			}
		}				
		complianceReportDTO.setPurposeOfRemittance(purposeOfRemittance);
	}

	private void populateRelationShip(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		RelationShip relationShip = new RelationShip();
		BeneficiaryUser beneficiaryUser = (BeneficiaryUser)objectMap.get("beneficiaryUser");
		
		if(beneficiaryUser!=null) {
			if(beneficiaryUser.getReceiverType() == 2) {
				relationShip.setRelationShip((beneficiaryUser.getBeneficiaryRelationship()!=null && relationshipMap!=null)  ? relationshipMap.get(beneficiaryUser.getBeneficiaryRelationship().intValue()) : null);
				relationShip.setReceipientName(beneficiaryUser.getName());
				
				PersonalDetails personalDetails = (PersonalDetails)objectMap.get("personalDetails");
				if(personalDetails!=null) {
					relationShip.setRemitterName(personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName());
				}				
			}else {
				relationShip.setRelationShip(Constants.BLANK);
				relationShip.setReceipientName(Constants.BLANK);
				relationShip.setRemitterName(Constants.BLANK);
			}
			complianceReportDTO.setRelationShip(relationShip);
		}		
	}

	private void populateReceipientWatchListScreening(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientWatchListScreening receipientWatchListScreening = new ReceipientWatchListScreening();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(userCalculatorMapping!=null && remittUser!=null) {
			BeneficiaryAmlCheckResponse beneficiaryAmlCheckResponse = beneficiaryAmlCheckApiResponseRepository.findTopByUidAndUserIdAndBeneficiaryIdOrderByIdDesc(userCalculatorMapping.getUid(), remittUser.getEmail(), userCalculatorMapping.getBeneficiaryId());
			objectMap.put("beneficiaryAmlCheckResponse", beneficiaryAmlCheckResponse);
			
			if(beneficiaryAmlCheckResponse!=null) {
				receipientWatchListScreening.setReceipientWatchListResponse(beneficiaryAmlCheckResponse.getReportResult());
				receipientWatchListScreening.setCheckId(beneficiaryAmlCheckResponse.getCheckId()!=null ? beneficiaryAmlCheckResponse.getCheckId() : "");
			}
		}
		complianceReportDTO.setReceipientWatchListScreening(receipientWatchListScreening);
	}

	private void populateRemitterWatchListScreening(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RemitterWatchListScreening remitterWatchListScreening = new RemitterWatchListScreening();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(userCalculatorMapping!=null && remittUser!=null) {
			RemitterAmlCheckResponse remitterAmlCheckResponse = remitterAmlCheckApiResponseRepository.findTopByUidAndUserIdOrderByIdDesc(userCalculatorMapping.getUid(), remittUser.getEmail());
			objectMap.put("remitterAmlCheckResponse", remitterAmlCheckResponse);
			
			if(remitterAmlCheckResponse!=null) {
				remitterWatchListScreening.setRemitterWatchListResponse(remitterAmlCheckResponse.getReportResult()!=null ? remitterAmlCheckResponse.getReportResult() : "");
				remitterWatchListScreening.setCheckId(remitterAmlCheckResponse.getCheckId()!=null ? remitterAmlCheckResponse.getCheckId() : "");
			}
		}
		complianceReportDTO.setRemitterWatchListScreening(remitterWatchListScreening);
	}

	private void populateReceipientOthersRemitter(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientOtherRemitter receipientOtherRemitter = new ReceipientOtherRemitter();
		
		BeneficiaryUser beneficiaryUser = (BeneficiaryUser)objectMap.get("beneficiaryUser");
		
		if(beneficiaryUser!=null) {
			BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
			
			if(beneficiaryAccount!=null && beneficiaryAccount.getBeneficiaryUser()!=null) {
				List<BeneficiaryAccount> beneficiaryAccountOtherThanGivenBeneficiaryList = beneficiaryAccountRepository.getBeneficiaryAccountOtherThanGivenBeneficiaryId(beneficiaryAccount.getAccountNumber(), beneficiaryAccount.getIfscCode(), beneficiaryAccount.getBeneficiaryUser().getId());
				objectMap.put("beneficiaryAccountOtherThanGivenBeneficiaryList", beneficiaryAccountOtherThanGivenBeneficiaryList);
				
				if(beneficiaryAccountOtherThanGivenBeneficiaryList!=null && !beneficiaryAccountOtherThanGivenBeneficiaryList.isEmpty()) {					
					List<Long> userIds = beneficiaryAccountOtherThanGivenBeneficiaryList.stream()
																						.filter(a->a.getBeneficiaryUser()!=null)
					                                               						.map(a->a.getBeneficiaryUser().getUser())
					                                               						.collect(Collectors.toList());
					
					if(userIds!=null && !userIds.isEmpty()) {
						List<RemittUser> remittUserList = remittUsersRepository.findAllById(userIds);
						objectMap.put("remittUserList", remittUserList);
						
						if(remittUserList!=null && !remittUserList.isEmpty()) {
							receipientOtherRemitter.setIsReceipientHavingOtherRemitter(Constants.RECEIPIENT_HAVING_OTHER_REMITTERS);
							
							List<String> emailIds = remittUserList.stream()
									                              .filter(r->r!=null)
									                              .map(r->r.getEmail())
									                              .collect(Collectors.toList());
							if(emailIds!=null && !emailIds.isEmpty()) {
								receipientOtherRemitter.setEmailIdOfRemitters(String.join(Constants.MULTI_FIELD_VALUES_DELIMITER, emailIds));
							}					
						}else {
							receipientOtherRemitter.setIsReceipientHavingOtherRemitter(Constants.RECEIPIENT_NOT_HAVING_OTHER_REMITTERS);
						}						
					}
				}
			}
		}
		complianceReportDTO.setReceipientOtherRemitter(receipientOtherRemitter);		
	}

	private void populateReceipientModificationLocation(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientModificationLocation receipientModificationLocation = new ReceipientModificationLocation();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		BeneficiaryUser beneficiaryUser = (BeneficiaryUser)objectMap.get("beneficiaryUser");
		
		if(remittUser!=null && beneficiaryUser!=null) {
			UserLoginAudit userBeneficiaryAddAudit = loginAuditRepo.findTopByEmailAndAuditTypeAndBeneficiaryIdOrderByIdDesc(remittUser.getEmail(), UserLoginAuditStatus.BENEFICIARY_ADD, beneficiaryUser.getId());
			UserLoginAudit userBeneficiaryModificationAudit = loginAuditRepo.findTopByEmailAndAuditTypeAndBeneficiaryIdOrderByIdDesc(remittUser.getEmail(), UserLoginAuditStatus.BENEFICIARY_UPDATE, beneficiaryUser.getId());
			
			objectMap.put("userBeneficiaryAddAudit", userBeneficiaryAddAudit);
			objectMap.put("userBeneficiaryModificationAudit", userBeneficiaryModificationAudit);
			
			if(userBeneficiaryAddAudit!=null) {
				
				IPTagging beneficiaryAddIpTagging = ipRepository.findByIpAddress(userBeneficiaryAddAudit.getBeneficiaryIdAddIp());
				objectMap.put("beneficiaryAddIpTagging", beneficiaryAddIpTagging);
				
				IPTagging beneficiaryModificationIpTagging = ipRepository.findByIpAddress(userBeneficiaryAddAudit.getBeneficiaryIdEditIp());
				objectMap.put("beneficiaryModificationIpTagging", beneficiaryModificationIpTagging);
				
				if(beneficiaryAddIpTagging!=null) {
					receipientModificationLocation.setIpBasedLocationOfReceipientAdded((beneficiaryAddIpTagging.getCountry()!=null && !Constants.NULL_VALUE.equalsIgnoreCase(beneficiaryAddIpTagging.getCountry()) ? beneficiaryAddIpTagging.getCountry() : Constants.BLANK));
				}
				
				if(beneficiaryModificationIpTagging!=null) {
					receipientModificationLocation.setIpBasedLocationOfReceipientModified((beneficiaryModificationIpTagging.getCountry()!=null && !Constants.NULL_VALUE.equalsIgnoreCase(beneficiaryModificationIpTagging.getCountry()) ? beneficiaryModificationIpTagging.getCountry() : Constants.BLANK));
				}
			}
		}
		
		complianceReportDTO.setReceipientModificationLocation(receipientModificationLocation);		
	}

	private void populateReceipientModificationDate(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientModificationDate receipientModificationDate = new ReceipientModificationDate();
		UserLoginAudit userLoginAuditForAddingBeneficiary = (UserLoginAudit)objectMap.get("userBeneficiaryAddAudit");
		UserLoginAudit userLoginAuditForModifyingBeneficiary = (UserLoginAudit)objectMap.get("userBeneficiaryModificationAudit");
		
		if(userLoginAuditForAddingBeneficiary!=null && userLoginAuditForAddingBeneficiary.getAuditTimeStamp()!=null) {
			receipientModificationDate.setDateOfReceipientAdded(userLoginAuditForAddingBeneficiary.getAuditTimeStamp()!=null ? DateUtil.convertToSpecificDateFormat(getDateFormatPattern(userLoginAuditForAddingBeneficiary.getAuditTimeStamp().toString()), Constants.RECEIPIENT_ADDITION_MODIFICATION_OUTPUT_FORMAT, userLoginAuditForAddingBeneficiary.getAuditTimeStamp().toString()) : null);
		}
		
		if(userLoginAuditForModifyingBeneficiary!=null && userLoginAuditForModifyingBeneficiary.getAuditTimeStamp()!=null) {			
			receipientModificationDate.setDateOfReceipientAdded(userLoginAuditForModifyingBeneficiary.getAuditTimeStamp()!=null ? DateUtil.convertToSpecificDateFormat(getDateFormatPattern(userLoginAuditForModifyingBeneficiary.getAuditTimeStamp().toString()), Constants.RECEIPIENT_ADDITION_MODIFICATION_OUTPUT_FORMAT, userLoginAuditForModifyingBeneficiary.getAuditTimeStamp().toString()) : null);
		}
		
		complianceReportDTO.setReceipientModificationDate(receipientModificationDate);
	}

	private void populateReceipientNameValidationApiStatus(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientNameValidationApiStatus receipientNameValidationApiStatus = new ReceipientNameValidationApiStatus();
		
		if(userCalculatorMapping!=null) {
			BeneficiaryNameCheck beneficiaryNameCheck = beneficiaryNameCheckRepository.findByBeneficiaryIdAndUidAndPaymentType(userCalculatorMapping.getBeneficiaryId(), userCalculatorMapping.getUid(), PaymentType.PENNY_DROP);
			if(beneficiaryNameCheck!=null) {
				receipientNameValidationApiStatus.setStatusResponseFromYesBank(beneficiaryNameCheck.getYesBankAPITransactionStatus().name());
			}
		}
		
		complianceReportDTO.setReceipientNameValidationApiStatus(receipientNameValidationApiStatus);		
	}

	private void populateReceipientNameValidation(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		ReceipientNameValidation receipientNameValidation = new ReceipientNameValidation();
		
		if(userCalculatorMapping!=null) {
			Optional<BeneficiaryUser> beneficiaryUserOptional = beneficiaryUserRepository.findById(userCalculatorMapping.getBeneficiaryId());
			BeneficiaryUser beneficiaryUser = beneficiaryUserOptional.isPresent() ? beneficiaryUserOptional.get() : null;
			objectMap.put("beneficiaryUser", beneficiaryUser);
			
			BeneficiaryNameCheck beneficiaryNameCheck = beneficiaryNameCheckRepository.findByBeneficiaryIdAndUidAndPaymentType(userCalculatorMapping.getBeneficiaryId(), userCalculatorMapping.getUid(), PaymentType.PENNY_DROP);
			objectMap.put("beneficiaryNameCheck", beneficiaryNameCheck);
			
			if(beneficiaryUser!=null) {
				receipientNameValidation.setReceipeientNameByUser(beneficiaryUser.getName());
			}
			
			if(beneficiaryNameCheck!=null) {
				receipientNameValidation.setReceipientNameByYesBank(beneficiaryNameCheck.getNameInResponse());
			}			
		}
		complianceReportDTO.setReceipientNameValidation(receipientNameValidation);
	}

	private void populateDuplicateRegistration(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		DuplicateRegistration duplicateRegistration = new DuplicateRegistration();
		PersonalDetails personalDetails = (PersonalDetails)objectMap.get("personalDetails");
		if(personalDetails!=null) {
			duplicateRegistration.setRemitterNameAndDob(personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName()+" "+(personalDetails.getDob()!=null ? DateUtil.convertToSpecificDateFormat(getDateFormatPattern(personalDetails.getDob().toString()), Constants.DUPLICATE_REGISTRATION_DOB_OUTPUT_FORMAT, personalDetails.getDob().toString()) : null));
			
			List<PersonalDetails> otherRemitterPersonalDetailsList =  personalDetailsRepository.findByfullNameAndMiddleNameAndLastNameAndDob(personalDetails.getFullName(), personalDetails.getMiddleName(), personalDetails.getLastName(), personalDetails.getDob(), personalDetails.getId());
			objectMap.put("otherRemitterPersonalDetailsList", otherRemitterPersonalDetailsList);
			
			if(otherRemitterPersonalDetailsList!=null && !otherRemitterPersonalDetailsList.isEmpty()) {
				List<String> emailIds = otherRemitterPersonalDetailsList.stream()
						                                                .filter(p->p!=null && p.getUsers()!=null)
				                                						.map(p->p.getUsers().getEmail())
				                                						.collect(Collectors.toList());
				if(emailIds!=null && !emailIds.isEmpty()) {
					duplicateRegistration.setEmailIdOfDuplicateNameAndDob(String.join(Constants.MULTI_FIELD_VALUES_DELIMITER, emailIds));
				}			                                
			}
		}
		complianceReportDTO.setDuplicateRegistration(duplicateRegistration);		
	}

	private void populateRemitterBankAccountName(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping, String refId) {
		RemitterBankAccountName remitterBankAccountName = new RemitterBankAccountName();
		
		PersonalDetails personalDetails = (PersonalDetails)objectMap.get("personalDetails");
		if(personalDetails!=null) {
			remitterBankAccountName.setRemitterBankAccountName(personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName());
		}
		
		List<TransferAccountDetails> transferAccountDetailsList = transferAccountRepository.findAllBytransactionRefNo(refId);
		objectMap.put("transferAccountDetailsList", transferAccountDetailsList);
		
		if(transferAccountDetailsList!=null && !transferAccountDetailsList.isEmpty()) {
			TransferAccountDetails transferAccountDetails = transferAccountDetailsList.get(0);
			remitterBankAccountName.setSenderAcctHolderNameFromFile(transferAccountDetails!=null ? transferAccountDetails.getSenderName() : null);
		}
		
		complianceReportDTO.setRemitterBankAccountName(remitterBankAccountName);
	}

	private void populateManualKyc(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		RemitterManualKyc remitterManualKyc = new RemitterManualKyc();
		
		if(complianceReportDTO!=null && complianceReportDTO.getRemitterGbgNonDvs()!=null) {
			String ruleType = complianceReportDTO.getRemitterGbgNonDvs().getRuleType();
			
			if(Constants.RULE_SET_N.equalsIgnoreCase(ruleType)) {
				remitterManualKyc.setManualKycApplicable(Constants.YES_FLAG);
				
				if(userCalculatorMapping.getUser()!=null) {
					List<DdFileInfo> fileInfoList = fileDBRepository.findAllByUserId(userCalculatorMapping.getUser().getId());
					
					if(fileInfoList!=null && !fileInfoList.isEmpty()) {
						if(fileInfoList.size() == 2) {
							
							if(fileInfoList.get(0).getImageUrl()!=null) {
								String[] fileName1LocationArray = fileInfoList.get(0).getImageUrl().split("/");
								
								if(fileName1LocationArray!=null && fileName1LocationArray.length!=0) {
									String fileName1 = fileName1LocationArray[fileName1LocationArray.length-1];
									remitterManualKyc.setDocumentLink1(fileInfoList.get(0).getImageUrl());
									remitterManualKyc.setDocumentName1(fileName1);
								}
							}
								
							if(fileInfoList.get(1).getImageUrl()!=null) {
								String[] fileName2LocationArray = fileInfoList.get(1).getImageUrl().split("/");
								
								if(fileName2LocationArray!=null && fileName2LocationArray.length!=0) {
									String fileName2 = fileName2LocationArray[fileName2LocationArray.length-1];
									remitterManualKyc.setDocumentLink2(fileInfoList.get(1).getImageUrl());
									remitterManualKyc.setDocumentName2(fileName2);
								}
							}
								
							
						}else {
							if(fileInfoList.get(0).getImageUrl()!=null) {
								String[] fileName1LocationArray = fileInfoList.get(0).getImageUrl().split("/");
								
								if(fileName1LocationArray!=null && fileName1LocationArray.length!=0) {
									String fileName1 = fileName1LocationArray[fileName1LocationArray.length-1];
									remitterManualKyc.setDocumentLink1(fileInfoList.get(0).getImageUrl());
									remitterManualKyc.setDocumentName1(fileName1);
								}
							}						
						}					
					}
				}
				
			}else {
				remitterManualKyc.setManualKycApplicable(Constants.NO_FLAG);
			}		
		}
		
		complianceReportDTO.setRemitterManualKyc(remitterManualKyc);		
	}

	private void populateRemitterGbgDvs(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		RemitterGbgDvs remitterGbgDvs = new RemitterGbgDvs();
		
		PersonalDetails personalDetails = (PersonalDetails)objectMap.get("personalDetails");
		if(personalDetails!=null) {
			PersonalDocuments personalDocuments = personalDocumentsRepository.findTopByPersonalDetailsOrderByIdDesc(personalDetails);
			List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponseList = gbgVerificationResponseRepository.findByPersonalDetailsAndGbgApiNames(personalDetails, GBGApiNames.DVS);
			objectMap.put("personalDocuments", personalDocuments);
			objectMap.put("gbgDvsVerificationResultList", gbgVerificationRegisterVerificationResponseList);
			
			if(personalDocuments!=null && personalDocuments.getDocumentTypes()!=null) {
				remitterGbgDvs.setGbgDvsDocumentTypeSelectedByUser(personalDocuments.getDocumentTypes().name());
				
				if(gbgVerificationRegisterVerificationResponseList!=null && !gbgVerificationRegisterVerificationResponseList.isEmpty()) {
					GBGVerificationRegisterVerificationResponse gbgVerificationRegisterVerificationResponse = gbgVerificationRegisterVerificationResponseList.get(gbgVerificationRegisterVerificationResponseList.size() - 1);
					remitterGbgDvs.setGbgDvsResponse(gbgVerificationRegisterVerificationResponse!=null ? gbgVerificationRegisterVerificationResponse.getLocalOverallVerificationStatus() : null);
				}
			}
		}
		
		complianceReportDTO.setRemitterGbgDvs(remitterGbgDvs);
	}

	private void populateRemitterGbgNonDvs(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		RemitterGbgNonDvs remitterGbgNonDvs = new RemitterGbgNonDvs();
		PersonalDetails personalDetails = (PersonalDetails)objectMap.get("personalDetails");
		if(personalDetails!=null) {
			GbgNonDvsApiRequest gbgNonDvsApiRequest = gbgNonDvsApiRequestRepository.findByPersonalDetailsId(personalDetails.getId());
			List<GBGVerificationRegisterVerificationResponse> gbgVerificationRegisterVerificationResponseList = gbgVerificationResponseRepository.findByPersonalDetailsAndGbgApiNames(personalDetails, GBGApiNames.NON_DVS);
			objectMap.put("gbgNonDvsApiRequest", gbgNonDvsApiRequest);
			objectMap.put("gbgVerificationRegisterVerificationResponseList", gbgVerificationRegisterVerificationResponseList);
			
			if(gbgVerificationRegisterVerificationResponseList!=null && !gbgVerificationRegisterVerificationResponseList.isEmpty()) {
				GBGVerificationRegisterVerificationResponse gbgVerificationRegisterVerificationResponse = gbgVerificationRegisterVerificationResponseList.get(gbgVerificationRegisterVerificationResponseList.size() - 1);
				remitterGbgNonDvs.setGbgNonDvsResponse(gbgVerificationRegisterVerificationResponse!=null ? gbgVerificationRegisterVerificationResponse.getLocalOverallVerificationStatus() : null);
			}
			
			if(gbgNonDvsApiRequest!=null && gbgNonDvsApiRequest.getRuleId()!=null) {
				GBGRuleIds gbRuleIds = GBGRuleIds.valueOf(gbgNonDvsApiRequest.getRuleId());
				
				if(gbRuleIds!=null)
				remitterGbgNonDvs.setRuleType(gbRuleIds.getCode());
			}
		}
		complianceReportDTO.setRemitterGbgNonDvs(remitterGbgNonDvs);
	}

	private void populateProfileDetails(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		DocumentVerification documentVerification = (DocumentVerification)objectMap.get("documentVerification");
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		ProfileFirstName profileFirstName = new ProfileFirstName();
		ProfileMiddleName profileMiddleName = new ProfileMiddleName();
		ProfileLastName profileLastName = new ProfileLastName();
		ProfileDobDetails profileDobDetails =new ProfileDobDetails();
		ProfileGender profileGender = new ProfileGender();
		ProfileNationality profileNationality = new ProfileNationality();
		ProfileAddress profileAddress = new ProfileAddress();
		
		if(remittUser!=null) {
			PersonalDetails personalDetails = personalDetailsRepository.findTopByUserIdOrderByIdDesc(remittUser.getId());
			objectMap.put("personalDetails", personalDetails);
			
			if(personalDetails!=null) {
				profileFirstName.setFirstNameByUser(personalDetails.getFullName());
				profileMiddleName.setMiddleNameByUser(personalDetails.getMiddleName());
				profileLastName.setProfileLastNameByUser(personalDetails.getLastName());
				profileDobDetails.setDobByUser(personalDetails.getDob()!=null ? DateUtil.convertToSpecificDateFormat(getDateFormatPattern(personalDetails.getDob().toString()), Constants.PROFILE_DOB_OUTPUT_FORMAT, personalDetails.getDob().toString()) : null);
				profileGender.setGenderByUser(personalDetails.getGender()!=null ? personalDetails.getGender().name() : null);
				profileNationality.setNationalityByUser(personalDetails.getNationality());
				profileAddress.setAddressByUser(personalDetails.getFlatNumber()+ " "+personalDetails.getStreetNumber()+ " "+personalDetails.getStreetName());
				profileAddress.setPostalCodeByUser(personalDetails.getPostalCode());
				profileAddress.setStateByUser((personalDetails.getProvince()!=null && stateMap!=null) ? stateMap.get(personalDetails.getProvince()) : null);
				profileAddress.setSuburbByUser(personalDetails.getSuburb());
				
				if(personalDetails.getCountry()!=null) {
					Countries countries = countriesRepository.findByCountryCode(personalDetails.getCountry());
					objectMap.put("countries", countries);
					profileAddress.setCountryByUser(countries.getCountryName());
				}
			}
			
			if(documentVerification!=null) {
				profileFirstName.setFirstNameByOcr(documentVerification.getFirstName());
				profileMiddleName.setMiddleNameByOcr(documentVerification.getMiddleName());
				profileLastName.setProfileLastNameByOcr(documentVerification.getLastName());
				profileDobDetails.setDobByOcr(documentVerification.getDateOfBirth()!=null ? DateUtil.convertToSpecificDateWithNoTimeFormat(Constants.OCR_DOB_INPUT_FORMAT, Constants.OCR_DOB_OUTPUT_FORMAT, documentVerification.getDateOfBirth().toString()) : documentVerification.getDateOfBirth());
				profileGender.setGenderByOcr(documentVerification.getGender());
				profileNationality.setNationalityByOCr(documentVerification.getNationality());
			}
		}
		
		complianceReportDTO.setProfileFirstName(profileFirstName);
		complianceReportDTO.setProfileMiddleName(profileMiddleName);
		complianceReportDTO.setProfileLastName(profileLastName);
		complianceReportDTO.setProfileAddress(profileAddress);
		complianceReportDTO.setProfileDobDetails(profileDobDetails);
		complianceReportDTO.setProfileGender(profileGender);
		complianceReportDTO.setProfileNationality(profileNationality);		
	}

	private void populateRemitterKycOnfidoSelfieCheck(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RemitterOnfidoSelfieCheck remitterOnfidoSelfieCheck = new RemitterOnfidoSelfieCheck();
		DocumentVerification documentVerification = (DocumentVerification)objectMap.get("documentVerification");
		
		if(documentVerification!=null) {
			RemittUser remittUser = (RemittUser)objectMap.get("user");
			if(remittUser!=null) {
				OnfidoCheckApiResponse onfidoCheckApiResponse = onfidoCheckApiResponseRepository.findByUserIdAndAppIdAndReportName(remittUser.getEmail(), documentVerification.getApplicationId(), Constants.ONFIDO_SELFIE_CHECK_REPORT_NAME);
				objectMap.put("onfidoCheckApiResponse", onfidoCheckApiResponse);
				
				if(onfidoCheckApiResponse!=null) {
					remitterOnfidoSelfieCheck.setOnfidoSelfieCheckResponse(onfidoCheckApiResponse.getReportResult());
				}
			}			
		}
		complianceReportDTO.setRemitterOnfidoSelfieCheck(remitterOnfidoSelfieCheck);
	}

	private void populateRemitterKycOnfidoDocExpiry(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RemitterOnfidoDocumentExpiry remitterOnfidoDocumentExpiry = new RemitterOnfidoDocumentExpiry();
		DocumentVerification documentVerification = (DocumentVerification)objectMap.get("documentVerification");
		
		if(documentVerification!=null) {
			remitterOnfidoDocumentExpiry.setDateOfExpiryFromOnfido(documentVerification.getDateOfExpiry()!=null ? DateUtil.convertToSpecificDateWithNoTimeFormat(Constants.REMITTER_KYC_ONFIDO_DOC_EXPIRY_INPUT_FORMAT, Constants.REMITTER_KYC_ONFIDO_DOC_EXPIRY_OUTPUT_FORMAT, documentVerification.getDateOfExpiry()) : Constants.BLANK);
		}
		
		complianceReportDTO.setRemitterOnfidoDocumentExpiry(remitterOnfidoDocumentExpiry);		
	}

	private void populateRemitterKycOnfidoDocCheck(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RemitterOnfidoDocumentCheckDetails remitterOnfidoDocumentCheckDetails = new RemitterOnfidoDocumentCheckDetails();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(remittUser!=null) {
			DocumentVerification documentVerification = userDocVerificationRepo.findTopByUserIdOrderByIdDesc(remittUser.getEmail());
			objectMap.put("documentVerification", documentVerification);
			
			if(documentVerification!=null) {
				OnfidoCheckApiResponse onfidoCheckApiResponse = onfidoCheckApiResponseRepository.findByUserIdAndAppIdAndReportName(remittUser.getEmail(), documentVerification.getApplicationId(), Constants.ONFIDO_DOCUMENT_CHECK_REPORT_NAME);
				remitterOnfidoDocumentCheckDetails.setDocumentTypeSelectedByUser(documentVerification.getDocumentType());
				remitterOnfidoDocumentCheckDetails.setResponseFromOnfido(documentVerification.getResult());
				remitterOnfidoDocumentCheckDetails.setCheckId(onfidoCheckApiResponse!=null && onfidoCheckApiResponse.getCheckId()!=null ? onfidoCheckApiResponse.getCheckId() : "");
			}
		}
		complianceReportDTO.setRemitterOnfidoDocumentCheckDetails(remitterOnfidoDocumentCheckDetails);
	}

	private void populateRegistrationVsTxnLocation(Map<String, Object> objectMap,
			ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RegistrationVsTxnIPDetails registrationVsTxnIPDetails = new RegistrationVsTxnIPDetails();
		registrationVsTxnIPDetails.setRegistrationIpLocation((complianceReportDTO!=null && complianceReportDTO.getRegistrationCountry()!=null) ? complianceReportDTO.getRegistrationCountry().getRegistrationIpBasedCountry() : Constants.BLANK);
		registrationVsTxnIPDetails.setTxnIpLocation((complianceReportDTO!=null && complianceReportDTO.getTransactionCountry()!=null &&!complianceReportDTO.getTransactionCountry().equals(Constants.NULL_VALUE)) ? complianceReportDTO.getTransactionCountry().getTxnIpBasedCountry() : Constants.BLANK);
		
		complianceReportDTO.setRegistrationVsTxnIPDetails(registrationVsTxnIPDetails);		
	}

	private void populateTxnCountry(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		TransactionCountry transactionCountry = new TransactionCountry();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(remittUser!=null && userCalculatorMapping!=null) {
			transactionCountry.setUserTxnCountry(remittUser.getCountries()!=null ? remittUser.getCountries().getCountryName() : Constants.DEFAULT_REGISTRATION_COUNTRY);
			UserLoginAudit userTxnAudit = loginAuditRepo.findTopByEmailAndAuditTypeAndRefIdOrderByIdDesc(remittUser.getEmail(), UserLoginAuditStatus.TRANSACTION, userCalculatorMapping.getRefId());
			objectMap.put("userTxnAudit", userTxnAudit);
			
			if(userTxnAudit!=null) {
				String txnIpAddress = userTxnAudit.getSourceIp();
				IPTagging ipTagging = ipRepository.findByIpAddress(txnIpAddress);
				
				if(ipTagging!=null) {
					transactionCountry.setTxnIpBasedCountry((ipTagging.getCountry()!=null && !ipTagging.getCountry().equalsIgnoreCase(Constants.NULL_VALUE)) ? ipTagging.getCountry() : Constants.BLANK);
				}
			}		
		}
		
		complianceReportDTO.setTransactionCountry(transactionCountry);
	}

	private void populateLastLoginOfUser(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO,
			UserCalculatorMapping userCalculatorMapping) {
		
		LastLoginIPDetails lastLoginIPDetails = new LastLoginIPDetails();
		RemittUser remittUser = (RemittUser)objectMap.get("user");
		
		if(remittUser!=null) {
			UserLoginAudit userLastLoginAudit = loginAuditRepo.findTopByEmailAndAuditTypeOrderByIdDesc(remittUser.getEmail(), UserLoginAuditStatus.LOGIN);
			objectMap.put("userLastLoginAudit", userLastLoginAudit);
			
			if(userLastLoginAudit!=null) {
				String lastLoginIpAddress = userLastLoginAudit.getSessionIp();
				IPTagging lastLoginIPTagging = ipRepository.findByIpAddress(lastLoginIpAddress);
				objectMap.put("lastLoginIPTagging", lastLoginIPTagging);
				
				if(lastLoginIPTagging!=null) {
					lastLoginIPDetails.setLastLoginIP((lastLoginIPTagging.getCountry()!=null && !lastLoginIPTagging.getCountry().equalsIgnoreCase(Constants.NULL_VALUE)) ? lastLoginIPTagging.getCountry() : Constants.BLANK);
				}
			}
		}
		
		
		complianceReportDTO.setLastLoginIPDetails(lastLoginIPDetails);		
	}

	private void populateRegistrationCountry(Map<String, Object> objectMap, ComplianceReportDTO complianceReportDTO, UserCalculatorMapping userCalculatorMapping) {
		RegistrationCountry registrationCountry = new RegistrationCountry();
		if(userCalculatorMapping!=null) {
			RemittUser remittUser = userCalculatorMapping.getUser();
			objectMap.put("user", remittUser);
			
			if(remittUser!=null) {
				registrationCountry.setUserSelectedCountry(remittUser.getCountries()!=null ? remittUser.getCountries().getCountryName() : Constants.DEFAULT_REGISTRATION_COUNTRY);
				UserLoginAudit userSignUpAudit = loginAuditRepo.findTopByEmailAndAuditTypeOrderByIdDesc(remittUser.getEmail(), UserLoginAuditStatus.SIGNUP);
				objectMap.put("userSignUpAudit", userSignUpAudit);
				
				if(userSignUpAudit!=null) {
					String signUpIpAddress = userSignUpAudit.getRegisterIp();
					IPTagging ipTagging = ipRepository.findByIpAddress(signUpIpAddress);
					objectMap.put("ipTagging", ipTagging);
					
					if(ipTagging!=null) {
						registrationCountry.setRegistrationIpBasedCountry((ipTagging.getCountry()!=null && !ipTagging.getCountry().equalsIgnoreCase(Constants.NULL_VALUE)) ? ipTagging.getCountry() : Constants.BLANK);
					}
				}
			}
		}
		
		complianceReportDTO.setRegistrationCountry(registrationCountry);
	}
	
	private String getDateFormatPattern(String inputDate) {
		String millisecondString = DateUtil.substringAfterLast(inputDate, ".");
		String inputDateFormatPattern = null;
		
		if(millisecondString!=null) {
			inputDateFormatPattern = DateUtil.getDateFormatPattern(millisecondString.length());
		}
		
		return inputDateFormatPattern;
	}
}

