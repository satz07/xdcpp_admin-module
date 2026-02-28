package com.adminremit.auth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.adminremit.auth.dto.KYCDTO;
import com.adminremit.auth.dto.RecipientDTO;
import com.adminremit.auth.dto.RemitterEDDDTO;
import com.adminremit.auth.dto.RemitterTransactionDTO;
import com.adminremit.auth.dto.UsersDTO;
import com.adminremit.auth.repository.BeneficiaryAccountRepository;
import com.adminremit.auth.repository.TransactionRepository;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.repository.BeneficiaryUserRepository;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.user.model.Users;
import com.adminremit.user.repository.UserCalculatorMappingRepository;
import com.adminremit.user.repository.UsersRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private UserCalculatorMappingRepository calculatorMappingRepository;

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private BeneficiaryAccountRepository beficiaryAccRepo;

	@Autowired
	private BeneficiaryUserRepository bebeficiarUserRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<RemitterTransactionDTO> getAllTransactionByEmailId(String emailid) {
		return jdbcTemplate.query(getQueryString(emailid), (rs, rowNum) -> {
			RemitterTransactionDTO rtd = new RemitterTransactionDTO();
			rtd.setBookingDate(rs.getDate("create_at"));

			String formatdate = rs.getString("dvalue_date");
			Date from = null;
			try {
				from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(from);
			rtd.setDisbursementDateTime(formatdate);
			rtd.setDisbursementAmount(rs.getString("to_currency_value"));
			rtd.setExchangeRate(rs.getString("to_currency_value"));
			rtd.setOriginatingAmount(rs.getString("transfer_amount"));
			rtd.setOriginatingCurrency(rs.getString("from_currency_value"));
			rtd.setiPLocation(rs.getString("source_ip"));
			rtd.setPaymentmode(rs.getString("payment_mode"));
			rtd.setReceivemode(rs.getString("receive_mode"));
			rtd.setRecipientName(rs.getString("beneficiary_name"));
			rtd.setStatus(rs.getString("workflow_state"));
			rtd.setTransactionNo(rs.getString("reference_id"));
			return rtd;
		});
	}

	private String getQueryString(String emailid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select distinct T.reference_id, ");
		stringBuilder.append("       T.workflow_state, ");
		stringBuilder.append("       T.create_at, ");
		stringBuilder.append("       U.from_currency_value, ");
		stringBuilder.append("       U.to_currency_value, ");
		stringBuilder.append("       U.transfer_amount, ");
		stringBuilder.append("       U.payment_mode, ");
		stringBuilder.append("       U.receive_mode, ");
		stringBuilder.append("       Ul.source_ip, ");
		stringBuilder.append("       T.disbursement_date_time as dvalue_date,U.beneficiary_name ");
		stringBuilder.append("from transaction_worflow T ");
		stringBuilder.append("         inner join user_calc_mapping U on T.reference_id = U.reference_id ");
		stringBuilder.append("         inner join user_login_details Ul on ul.ref_id = U.reference_id ");
		stringBuilder.append("         where is_completed=false and U.user_id in (select id from users where email='"
				+ emailid + "') order by T.create_at DESC");

		return stringBuilder.toString();
	}

	@Override
	public List<TransactionWorkflow> getPaginatedTransactionList() {
		List<TransactionWorkflow> transactionList = null;
		transactionList = transactionRepo.findAll();
		return transactionList;
	}

	@Override
	public UserCalculatorMapping getUserCalculatorById(String trns_no) {
		UserCalculatorMapping userCalculator = null;
		userCalculator = calculatorMappingRepository.findByRefId(trns_no);
		return userCalculator;
	}

	@Override
	public Users getUserById(Long id) {

		Users users = null;
		users = usersRepo.getOne(id);
		return users;
	}

	@Override
	public BeneficiaryAccount getBeneficiaryById(Long Id) {
		BeneficiaryAccount account = null;
		account = beficiaryAccRepo.getBeneficiaryAccountDetails(Id);
		return account;
	}

	@Override
	public BeneficiaryUser getBeneficiartUserById(Long Id) {
		BeneficiaryUser beneficiaryUser = null;
		beneficiaryUser = bebeficiarUserRepository.getOne(Id);
		return beneficiaryUser;
	}

	@Override
	public TransactionWorkflow getPaginatedTransactionSummary(String trns_no) {
		TransactionWorkflow transaction = null;
		transaction = transactionRepo.getPaginatedTransactionSummary(trns_no);
		return transaction;
	}

	@Override
	public List<RemitterEDDDTO> getEddDataByEmailId(String emailid) {
		return jdbcTemplate.query(getQueryStringEDD(emailid), (rs, rowNum) -> {
			RemitterEDDDTO rtd = new RemitterEDDDTO();
			rtd.setAnnualIncomeSlab(rs.getString("annual_income"));

			rtd.seteDD(rs.getString("reference_id"));
			rtd.seteDDCountry(rs.getString("country_name"));
			if (rs.getString("profession_type").equalsIgnoreCase("other")) {
				rtd.setOtherProfession("other");
				rtd.setProfessionType(" ");
			} else {
				rtd.setOtherProfession(" ");
				rtd.setProfessionType(rs.getString("profession_type"));
			}
			rtd.setProfession(rs.getString("profession"));

			rtd.setSourceOfIncome(rs.getString("source_income"));

			return rtd;
		});
	}

    private String getQueryStringEDD(String emailid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "select c.country_name,u.reference_id,e.source_income,e.profession,e.profession_type,e.annual_income,e.other_income ");
        stringBuilder.append("from edd_master E ");
        stringBuilder.append(" inner join user_calc_mapping u on E.user_calc_mapping = u.uid");
        // WEB3 migration changes - use dynamic table name for countries
        String countriesTable = com.adminremit.config.Web3TableNameUtil.resolveTableName("admin_countries");
        stringBuilder.append(
                " inner join " + countriesTable + " c on c.id = u.from_country_id inner join users us on us.id = u.user_id");
        stringBuilder.append("       where us.email= '" + emailid + "'");
        return stringBuilder.toString();
    }

	@Override
	public List<UsersDTO> getRemitterByEmail(String email) {
		return jdbcTemplate.query(getQueryStringRemitter(email), (rs, rowNum) -> {
			UsersDTO rtd = new UsersDTO();

			String address = rs.getString("flat_number") + "," + rs.getString("street_number") + ","
					+ rs.getString("street_name") + "," + rs.getString("postal_code") + "," + rs.getString("country")
					+ "," + rs.getString("province");

			String name = rs.getString("full_name") + "," + rs.getString("middle_name") + ","
					+ rs.getString("last_name");
			String dobdate = rs.getString("dvalue_date");
			String Regidate = rs.getString("rvalue_date");
			Date dfrom = null;
			Date rfrom = null;
			try {
				dfrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dobdate);
				rfrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Regidate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dobdate = new SimpleDateFormat("DD-MMM-YYYY").format(dfrom);
			Regidate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(rfrom);

			Calendar cal = Calendar.getInstance();
			cal.setTime(dfrom);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			String age = getAge(year, month, day);
			System.out.println("ge::::::::" + age);
			rtd.setAddress(address);
			rtd.setFullName(name);
			rtd.setDateofBirth(dobdate);
			rtd.setRegistrationDateAndTime(Regidate);
			rtd.setEmail(rs.getString("email"));
			rtd.setGender(rs.getString("gender"));
			rtd.setMobileNumber(rs.getString("phone_number"));
			rtd.setNationality(rs.getString("nationality"));
			rtd.setRegistrationIPAddress(rs.getString("ip_address"));
			rtd.setAge(age);
			return rtd;
		});
	}

	private String getQueryStringRemitter(String emailid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(
				"select pd.full_name,pd.last_name,pd.middle_name,pd.dob as dvalue_date,pd.gender,pd.nationality,pd.street_name, ");
		stringBuilder.append("   pd.phone_number,pd.create_at as rvalue_date,pd.flat_number,pd.street_number, ");
		stringBuilder.append("      pd.postal_code,pd.country,pd.province,us.email,us.ip_address ");
		stringBuilder.append("from personal_details pd inner join users us on us.id = pd.user_id");
		stringBuilder.append(" where us.email = '" + emailid + "' ");
		return stringBuilder.toString();
	}

	private String getAge(int year, int month, int day) {
		Calendar dob = Calendar.getInstance();
		Calendar today = Calendar.getInstance();

		dob.set(year, month, day);

		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		Integer ageInt = new Integer(age);
		String ageS = ageInt.toString();

		return ageS;
	}

	@Override
	public List<RecipientDTO> getRecipientByEmail(String email) {
		return jdbcTemplate.query(getQueryStringRecipient(email), (rs, rowNum) -> {
			RecipientDTO rtd = new RecipientDTO();

			rtd.setAddress(rs.getString("address"));
			rtd.setAccountNumber(rs.getString("account_number"));
		//	rtd.setAmlCheckStatus(rs.getString("value"));
			rtd.setAmlDateandTime("" + new Date());
			// rtd.setBankCode(rs.getString("account_number"));
			rtd.setBankName(rs.getString("branchname"));
			rtd.setCountry(rs.getString("country"));
			rtd.setDateAdded(rs.getString("create_at"));
			rtd.setDateModified(rs.getString("update_at"));
			rtd.setDateofBirth(rs.getString("dob"));
			rtd.setName(rs.getString("full_name"));
			rtd.setNameValidationStatus(rs.getString("verification_status"));
			// rtd.setNameValidationValue(rs.getString("account_number"));
			rtd.setNickname(rs.getString("nick_name"));
			rtd.setStatus(rs.getString("account_status"));
			rtd.setvPAHandle(rs.getString("account_holder_name"));
			rtd.setPurpose(rs.getString("receiver_type"));
			rtd.setRelationship(rs.getString("relationship"));
			// rtd.setRelationshipNumber(rs.getString("account_number"));

			return rtd;
		});
	}

	private String getQueryStringRecipient(String emailid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(
				"select country,relationship,full_name,nick_name,b.dob,address,b.receiver_type,b.account_status,account_number,account_title,branchname,b.create_at,"
						+ "b.update_at,bad.account_holder_name,bad.verification_status");
		stringBuilder.append("  from beneficiary_user b ");
		stringBuilder.append("    inner join users u on u.id = b.user_id ");
		stringBuilder.append("    inner join beneficiary_account_details bad on bad.beneficiary_user = b.id");
		//stringBuilder.append("     inner join aml_details a on a.beneficiary_id = b.id");
		stringBuilder.append(" where u.email = '" + emailid + "' ");
		return stringBuilder.toString();
	}

	@Override
	public List<KYCDTO> getKYC1ByEmail(String email) {
		return jdbcTemplate.query(getQueryStringKYC1(email), (rs, rowNum) -> {
			KYCDTO rtd = new KYCDTO();

			rtd.setKycCountry(rs.getString("country_name"));
			rtd.setKycDateandTime("" + new Date());
			rtd.setKycDocument(rs.getString("document_type"));
			rtd.setKycResult(rs.getString("result"));
			rtd.setLevel1KYCID(rs.getString("app_id"));
			rtd.setLevel1KYCPartner(rs.getString("KYCPartner"));
			return rtd;
		});
	}

	private String getQueryStringKYC1(String emailid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select country_name,app_id,'Onfido' as KYCPartner,result,document_type,document_no ");
		stringBuilder.append("  FROM document_details d ");
		stringBuilder.append("   inner join admin_countries c on d.issuing_country = c.iso_code");
		stringBuilder.append(" where user_id= '" + emailid + "' ");
		return stringBuilder.toString();
	}

	@Override
	public List<KYCDTO> getKYC2ByEmail(String email) {
		return jdbcTemplate.query(getQueryStringKYC2(email), (rs, rowNum) -> {
			KYCDTO rtd = new KYCDTO();

			rtd.setLevel2KYCID(rs.getString("verification_id"));
			rtd.setLevel2KYCPartner(rs.getString("KYCPartner"));
			rtd.setKyc2DateandTime(rs.getString("create_at"));
			rtd.setKyc2Document(rs.getString("verification_token"));
			rtd.setKyc2Result(rs.getString("verification_status"));

			return rtd;
		});
	}

	private String getQueryStringKYC2(String emailid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(
				"select verification_id,'GBG - ' as KYCPartner,api_name,verification_status,g.create_at,verification_token,is_error_returned,gbg_error_msg");
		stringBuilder.append(" from gbg_verification_result g ");
		stringBuilder.append(
				"  inner join personal_details p on p.id = g.personal_details_id inner join users u on u.id=p.user_id");
		stringBuilder.append(" where email= '" + emailid + "' ");
		return stringBuilder.toString();
	}
	/*
	 * @Override public List<KYCDTO> getKYCAMLByEmail(String email) { return
	 * jdbcTemplate.query(getQueryStringAML(email), (rs, rowNum) -> { KYCDTO rtd =
	 * new KYCDTO();
	 * 
	 * rtd.setAmlCheckStatus(rs.getString("value")); rtd.setAmlDateandTime(""+new
	 * Date());
	 * 
	 * return rtd; }); }
	 * 
	 * private String getQueryStringAML(String emailid) { StringBuilder
	 * stringBuilder = new StringBuilder(); stringBuilder.append(
	 * "select id,key,value from aml_details a"); stringBuilder.
	 * append(" where app_id in (select app_id from document_details where user_id = '"
	 * +emailid+"')"); stringBuilder.append(" order by a.id desc"); return
	 * stringBuilder.toString(); }
	 */

	@Override
	public TransactionWorkflow getTransactionByReferenceIdAndWorkflowStatus(String refernceId,
			WorkflowStatus workflowStatus) {
		
		return transactionRepo.findByReferenceNoAndWorkflowStatus(refernceId, workflowStatus);
	}
	
	@Override
	public List<TransactionWorkflow> findByReferenceNo(String referenceId) {
		return transactionRepo.findByReferenceNo(referenceId);
	}
	
	@Override
	public TransactionWorkflow getLatestRecordByRefernceId(String referenceId) {
		return transactionRepo.findTopByReferenceNoOrderByIdDesc(referenceId);
	}
	
}
