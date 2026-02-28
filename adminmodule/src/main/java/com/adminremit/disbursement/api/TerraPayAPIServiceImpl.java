package com.adminremit.disbursement.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.service.BeneficiaryAccountService;
import com.adminremit.beneficiary.service.BeneficiaryUserService;
import com.adminremit.common.util.DateUtil;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.PurposeListMaster;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.PurposeListMasterService;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.personaldetails.service.PersonalDocumentsService;
import com.adminremit.report.constants.ReportsConstants;
import com.adminremit.terrapay.dto.TrxInitiationRequest;
import com.adminremit.terrapay.dto.TrxStatusRequest;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UserCalculatorMappingService;
import com.adminremit.user.service.UsersService;
import com.google.gson.Gson;

import okhttp3.Credentials;

@Service
@Qualifier("terraPayService")
public class TerraPayAPIServiceImpl  implements RemitAPIService {

	@Value("${terrapay.remitt.url}")
    private String remittServiceURL;

	@Value("${terrapay.status.url}")
    private String enquiryServiceURL;

	@Value("${terrapay.uname}")
    private String terraPayUName;

	@Value("${terrapay.pword}")
    private String terraPayPassword;


	 @Autowired
	 private UsersService usersService;

    @Autowired
    private BeneficiaryUserService beneficiaryUserService;

    @Autowired
    private BeneficiaryAccountService beneficiaryAccountService;

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private PersonalDocumentsService personalDocumentsService;

    @Autowired
    private PurposeListMasterService purposeListMasterService;

    @Autowired
	private FYServiceCallClient client;

    @Autowired
	private UserCalculatorMappingService userCalculatorMappingService;

    @Autowired
	private CountriesService countriesService;

	private static final Logger logger = LoggerFactory.getLogger(TerraPayAPIServiceImpl.class);

	private Map<String,String> buildHeader(){
		Map<String,String> headers =new HashMap<String,String>();
		String credential = Credentials.basic(terraPayUName, terraPayPassword);
        headers.put("Authorization", credential);
		return headers;
	}


	public String getSessionId() {
		return RandomStringUtils.randomAlphanumeric(50).toUpperCase();
	}

	@Override
	public Map<String,String> initiateTransaction(UserCalculatorMapping userCalculatorMapping) throws Exception {
		 Users users = usersService.getUserById(userCalculatorMapping.getUser().getId());
         logger.info("Send Transaction initiated for  "+userCalculatorMapping.getRefId());
         BeneficiaryUser beneficiaryUser = beneficiaryUserService.getBeneficiary(userCalculatorMapping.getBeneficiaryId());
         BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
         PersonalDetails personalDetails = personalDetailsService.getLatestByUser(users.getId());
         PersonalDocuments personalDocuments = personalDocumentsService.fetchOne(personalDetails);
         Countries countries =countriesService.findByCountryCode(userCalculatorMapping.getToCountryCode());
         Countries fromCountries =countriesService.findByCountryCode(userCalculatorMapping.getFromCountryCode());
         String paymentModeType=beneficiaryAccount.getReceiveMode().getPaymentCodeMaster().getPaymentCode();
         TrxInitiationRequest terrapayRequest = new TrxInitiationRequest();
         terrapayRequest.setAccountId(userCalculatorMapping.getBeneficiaryAccountNumber());
         terrapayRequest.setBankCode(beneficiaryAccount.getIfscCode());
         terrapayRequest.setBankName(beneficiaryAccount.getBranchName());
         terrapayRequest.setBankSubCode("");
         terrapayRequest.setDescriptionText("");
         terrapayRequest.setFynteRefernceNumber(userCalculatorMapping.getRefId());
         terrapayRequest.setOriginCountry(userCalculatorMapping.getFromCountryCode());
        if(paymentModeType.equals(AccountType.WAL.name())){
            terrapayRequest.setPaymentMethod("W");
            terrapayRequest.setProvider(beneficiaryAccount.getIfscCode());
        }
        else  if(paymentModeType.equals(AccountType.BAC.name())) {
            terrapayRequest.setPaymentMethod("B");
            terrapayRequest.setProvider(beneficiaryAccount.getInstaCode());
        }
         //terrapayRequest.setProvider(beneficiaryAccount.getInstaCode());
         terrapayRequest.setReceivingCountry(userCalculatorMapping.getToCountryCode());
         terrapayRequest.setReceivingCurrency(userCalculatorMapping.getToCurrencyCode());
         terrapayRequest.setRecipientAddressLine1(beneficiaryUser.getAddress());
         terrapayRequest.setRecipientAddressLine2(beneficiaryUser.getAddress2());
         terrapayRequest.setRecipientAddressLine3("");
         terrapayRequest.setRecipientCity(beneficiaryUser.getCity());
         terrapayRequest.setRecipientDateOfBirth(beneficiaryUser.getDob()!=null?DateUtil.formatDateAndTimeInGivenFormat("yyyy-MM-dd",beneficiaryUser.getDob()):"");
         terrapayRequest.setRecipientFirstName(beneficiaryUser.getFullName());
         terrapayRequest.setRecipientFullName(beneficiaryUser.getFullName()+(beneficiaryUser.getMiddleName() !=null ?(" "+beneficiaryUser.getMiddleName()):"")+" "+beneficiaryUser.getLastName());
         terrapayRequest.setRecipientIDExpiryDate(null);
         terrapayRequest.setRecipientIDNumber(null);
         terrapayRequest.setRecipientIdIssueDate(null);
         terrapayRequest.setRecipientIdIssuerCountry(null);
         terrapayRequest.setRecipientIdType(null);
         terrapayRequest.setRecipientLastName(beneficiaryUser.getLastName());
         terrapayRequest.setRecipientMobile(countries.getDialingCode()+beneficiaryUser.getMobileNumber());
         terrapayRequest.setRecipientNationality(null);
         terrapayRequest.setRecipientPostalCode(beneficiaryUser.getPincode());
         terrapayRequest.setRecipientStateProvince(beneficiaryUser.getProvince());
         if (beneficiaryUser.getReceiverType().equals(3L)) {
        	 terrapayRequest.setRelationshipSender("Self");
 		}
         else if (beneficiaryUser.getReceiverType().equals(2L)) {
 			if(beneficiaryUser.getBeneficiaryRelationship() != null) {
 				terrapayRequest.setRelationshipSender(ReportsConstants.terraPayRelationshipMap.get(beneficiaryUser.getBeneficiaryRelationship().intValue()));
 			}else {
 				terrapayRequest.setRelationshipSender("Friend");
 			}
 		}
         else{

        	 terrapayRequest.setRelationshipSender("BUSINESS");
 		}

         if(beneficiaryUser.getPurpose() !=null) {
 	        Long purposeId= Long.valueOf(beneficiaryUser.getPurpose());
 	        PurposeListMaster purposeListMaster =purposeListMasterService.getById(purposeId);
 	        terrapayRequest.setRemittancePurpose(purposeListMaster.getPurpose());
         }else {
        	 terrapayRequest.setRemittancePurpose("Family Maintenance");
         }

         StringBuilder remitterAddress = new StringBuilder();
         remitterAddress.append(personalDetails.getFlatNumber());
         remitterAddress.append(",");
         remitterAddress.append(personalDetails.getStreetNumber());
         remitterAddress.append(",");
         remitterAddress.append(personalDetails.getStreetName());
         terrapayRequest.setRemitterAddressLine1(remitterAddress.toString());
         terrapayRequest.setRecipientAddressLine2(null);
         terrapayRequest.setRecipientAddressLine3(null);
         terrapayRequest.setRemitterCity(personalDetails.getSuburb());
         terrapayRequest.setRemitterDateOfBirth(personalDetails.getDob()!=null?DateUtil.formatDateAndTimeInGivenFormat("yyyy-MM-dd",personalDetails.getDob()):"");
         terrapayRequest.setRemitterFirstName(personalDetails.getFullName());
         terrapayRequest.setRemitterFullName(personalDetails.getFullName()+(personalDetails.getMiddleName() !=null ?(" "+personalDetails.getMiddleName()):"")+" "+personalDetails.getLastName());
         terrapayRequest.setRemitterGender(personalDetails.getGender().name().substring(0, 1));

         terrapayRequest.setRemitterIDExpiryDate(personalDocuments.getPersonalDocumentDetails().getDocumentExpDate());
         terrapayRequest.setRemitterIDNumber(personalDocuments.getDocumentNumber());
         terrapayRequest.setRemitterIdIssueDate(null);
         terrapayRequest.setRemitterIdIssuerCountry(personalDetails.getCountry());
         terrapayRequest.setRemitterIdType(personalDocuments.getDocumentTypes().name().toLowerCase());

         terrapayRequest.setRemitterLastName(personalDetails.getLastName());
         terrapayRequest.setRemitterMiddleName(personalDetails.getMiddleName());
         terrapayRequest.setRemitterMobile(fromCountries.getDialingCode()+personalDetails.getPhoneNumber());
         terrapayRequest.setRemitterNationality(personalDetails.getNationality().substring(0,2));
         terrapayRequest.setRemitterPostalCode(personalDetails.getPostalCode());
         terrapayRequest.setRemitterStateProvince(personalDetails.getProvince());
         terrapayRequest.setRemitterTitle(null);
         terrapayRequest.setSendingCurrency(userCalculatorMapping.getToCurrencyCode());
         terrapayRequest.setRequestAmount(userCalculatorMapping.getTotalConvertedValue().doubleValue());
         terrapayRequest.setSendingCurrency(userCalculatorMapping.getFromCurrencyCode());
         terrapayRequest.setSourceOfFunds("Others");
         StringBuilder builder= new StringBuilder();
         Map<String,String> map = new HashMap<String,String>();
         try {
        	  String trxJSONRequest = new Gson().toJson(terrapayRequest);
              builder.append("\nTerrapay Initiate Transfer  requested for \n"+trxJSONRequest);
	     	  Map<String,Object> trxResponse = client.connectOk(remittServiceURL, trxJSONRequest, null,buildHeader(),"POST");
	     	  if(!trxResponse.get("status").equals(200)) {
	     		  throw new Exception(trxResponse.get("rbody").toString());
	     	  }
	     	  map = (Map<String,String>) new Gson().fromJson(trxResponse.get("rbody").toString(), map.getClass());
	     	  builder.append("\nTerrapay Transfer  response \n"+trxResponse.toString());
     		 } catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				builder.append("\n failed with "+sw.toString());
				throw e;
			}finally {
				logger.info(builder.toString());
			}

		 return map;
	}


	@Override
	public Map<String, String> getStatus(String referenceId) throws Exception {
		StringBuilder builder= new StringBuilder();
		UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(referenceId);
		TrxStatusRequest statusRequest = new TrxStatusRequest();
		statusRequest.setOriginCountry(userCalculatorMapping.getFromCountryCode());
		BeneficiaryAccount beneficiaryAccount = beneficiaryAccountService.getById(userCalculatorMapping.getBeneficiaryAccountId());
        String paymentModeType=beneficiaryAccount.getReceiveMode().getPaymentCodeMaster().getPaymentCode();

		if(paymentModeType.equals(AccountType.WAL.name())){
			statusRequest.setPaymentMethod("W");
        }
        else  if(paymentModeType.equals(AccountType.BAC.name())) {
        	statusRequest.setPaymentMethod("B");
        }
		statusRequest.setTerraPayReferenceNumber(referenceId);
        Map<String,String> map = new HashMap<String,String>();

        try {
       	  String trxJSONRequest = new Gson().toJson(statusRequest);
             builder.append("\nTerrapay Status Enquiry  requested for \n"+trxJSONRequest);
	     	  Map<String,Object> trxResponse = client.connectOk(enquiryServiceURL, trxJSONRequest, null,buildHeader(),"POST");
	     	  map = (Map<String,String>) new Gson().fromJson(trxResponse.get("rbody").toString(), map.getClass());
	     	  builder.append("\nTerrapay Status Enquiry  response \n"+trxResponse.toString());
    		 } catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				builder.append("\n failed with "+sw.toString());
				throw e;
			}finally {
				logger.info(builder.toString());
			}

		 return map;
	}

}
