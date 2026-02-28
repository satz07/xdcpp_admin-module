package com.adminremit.user.aml;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.aml.model.AMLVerification;
import com.adminremit.aml.model.BeneficiaryAmlCheckRequest;
import com.adminremit.aml.model.BeneficiaryAmlCheckResponse;
import com.adminremit.aml.model.RemitterAmlCheckRequest;
import com.adminremit.aml.model.RemitterAmlCheckResponse;
import com.adminremit.aml.repository.AMLRepository;
import com.adminremit.aml.repository.BeneficiaryAmlCheckApiRequestRepository;
import com.adminremit.aml.repository.BeneficiaryAmlCheckApiResponseRepository;
import com.adminremit.aml.repository.RemitterAmlCheckApiRequestRepository;
import com.adminremit.aml.repository.RemitterAmlCheckApiResponseRepository;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.beneficiary.repository.BeneficiaryUserRepository;
import com.adminremit.config.OnfidoConfiguration;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import com.adminremit.report.model.business.BusinessInfo;
import com.adminremit.report.repo.BusinessInfoRepository;
import com.adminremit.user.model.DocumentVerification;
import com.adminremit.user.service.DocVerificationService;
import com.adminremit.user.service.UserCalculatorMappingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfido.Onfido;
import com.onfido.models.Applicant;
import com.onfido.models.Check;
import com.onfido.models.Check.Request;
import com.onfido.models.Report;

@Service
public class KYCCheckServiceImpl implements KYCCheckService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KYCCheckServiceImpl.class);

    @Autowired
    OnfidoConfiguration configuration;

    @Autowired
    DocVerificationService docVerificationService;
    
    @Autowired
    PersonalDetailsService personService;

   @Autowired
    AMLRepository amlRepository;
   
   @Autowired
   private BeneficiaryUserRepository beneficiaryUserRepository;
   
   @Autowired
   private RemitterAmlCheckApiRequestRepository remitterAmlCheckApiRequestRepository;
   
   @Autowired
   private RemitterAmlCheckApiResponseRepository remitterAmlCheckApiResponseRepository;
   
   @Autowired
   private UserCalculatorMappingService userCalculatorMappingService;
   
   @Autowired
   private BeneficiaryAmlCheckApiRequestRepository beneficiaryAmlCheckApiRequestRepository;
   
   @Autowired
   private BeneficiaryAmlCheckApiResponseRepository beneficiaryAmlCheckApiResponseRepository;
   
   @Autowired
   private BusinessInfoRepository businessInfoRepository;
	
	public Map<String,Object> amlCheck(String uid, Long personalId)  {
        boolean isComplete=false;
        Map<String, Object> properties = new HashMap<String, Object>();
        RemitterAmlCheckRequest remitterAmlCheckRequest = new RemitterAmlCheckRequest();
        RemitterAmlCheckResponse remitterAmlCheckResponse = new RemitterAmlCheckResponse();
        
        try {

            properties = new HashMap<String, Object>();
            Onfido onfido = configuration.getOnfidoConfig();
            DocumentVerification documentVerification = null;
            PersonalDetails personal = null;
            String username = null;
            BusinessInfo businessInfo = null;
            String dob = "";
            String firstName = "";
            String lastName = "";
            
            if (uid != null) {
            	UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByUid(uid);            	
                LOG.info("UID    "+uid);
                
                if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null && userCalculatorMapping.getUser().getUserType()!=null 
                		&& userCalculatorMapping.getUser().getUserType().equalsIgnoreCase("PERSONAL")) {
                	username = userCalculatorMapping.getUser().getEmail();
                	documentVerification = docVerificationService.getDocumentByUserId(username);
                    personal = personService.getById(personalId);
                    dob = personal.getDob().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    firstName = personal.getFullName() + " " + personal.getMiddleName();
                    lastName = personal.getLastName();
                }  
                
                if(userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null && userCalculatorMapping.getUser().getUserType()!=null 
                		&& userCalculatorMapping.getUser().getUserType().equalsIgnoreCase("BUSINESS")) {
                	username = userCalculatorMapping.getUser().getEmail();
                	documentVerification = docVerificationService.getDocumentByUserId(username);
                	businessInfo = businessInfoRepository.findOneByUserId(userCalculatorMapping.getUser().getId());
                	firstName = businessInfo!=null ? businessInfo.getCompanyName() : "";
                	lastName = ".";
                }
                
                
                /*String dob = documentVerification.getDateOfBirth();
                String firstName = documentVerification.getFirstName()+" "+documentVerification.getMiddleName();
                String lastName = documentVerification.getLastName();*/
                
                /*String dob = personal.getDob().toInstant().atZone(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String firstName = personal.getFullName() + " " + personal.getMiddleName();
                String lastName = personal.getLastName();*/
                
                String appid = documentVerification.getApplicationId();
                LOG.info("Before Update " + dob +" firstName "+firstName +" lastName"+lastName+" appid"+appid);
                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate parsedDate = LocalDate.parse(dob, formatter);
                Applicant applicant = onfido.applicant.update(appid, Applicant.request().dob(parsedDate).firstName(firstName).lastName(lastName));*/

                Applicant newApplicant = onfido.applicant.create(Applicant.request().firstName(firstName).lastName(lastName));
                LOG.info("After creating onfido request...");
             	String applicantId = newApplicant.getId();
             	LOG.info("Before updating onfido request...");
             	Applicant applicant = onfido.applicant.update(applicantId, Applicant.request().firstName(firstName).lastName(lastName));
             	LOG.info("After updating onfido request...");
             	String reportName = "watchlist_standard";
                Check.Request  checkRequest = Check.request()
                        .applicantId(applicantId)
                        .reportNames(reportName);
                LOG.info("Before watchlist check request...");
                populateAndSaveRemitterAmlCheckRequest(uid, username, dob, firstName, lastName, appid, applicantId, checkRequest, reportName, remitterAmlCheckRequest);
                Check check = onfido.check.create(checkRequest);
                LOG.info("Check Id :" + check.getId() + "   Result:" + check.getResult() + "  ReportID" + check.getReportIds());
                isComplete = false;
                while (!isComplete) {
                    try {

                        Report report = onfido.report.find(check.getReportIds().get(0));
                        LOG.info("Report Result for watch List" + report.getStatus());
                        if (report.getStatus().equalsIgnoreCase("complete")) {
                            isComplete = true;
                            properties = report.getProperties();
                            LOG.info("Watchlist  properties" + properties);
                            LOG.info("Watchlist SubResult " + report.getSubResult());
                            LOG.info("Watchlist Result " + report.getResult());
                            LOG.info("Watchlist BreakDown " + report.getBreakdown());

                            for (Map.Entry<String,Object> entry : report.getBreakdown().entrySet())
                            {
                                LOG.info("Key = " + entry.getKey() +", Value = " + entry.getValue());

                                for (Map.Entry<String,Object> entry1 : ((Map<String,Object>)entry.getValue()).entrySet())
                                {
                                    AMLVerification amlVerification = new AMLVerification();

                                    amlVerification.setApplicationId(appid);
                                    amlVerification.setSessionid(uid);
                                    amlVerification.setKey(entry.getKey());
                                    amlVerification.setValue(entry1.getValue().toString());
                                    amlVerification.setUserId(documentVerification.getUserId());
                                    amlRepository.save(amlVerification);
                                }

                            }
                            
                            populateAndSaveRemitterAmlCheckResponse(uid, username, dob, firstName, lastName, appid, applicantId, check, report, remitterAmlCheckRequest, reportName, remitterAmlCheckResponse);

                    }
                        // }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOG.error("Error while Onfido check request for AML check::::"+e);
                    }

                }


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LOG.error("Error while connecting to Onfido server for AML check::::"+e);
        }
        return properties;
    }

	private void populateAndSaveRemitterAmlCheckResponse(String uid, String username, String dob, String firstName,
			String lastName, String appid, String applicantId, Check check, Report report,
			RemitterAmlCheckRequest remitterAmlCheckRequest, String reportName, RemitterAmlCheckResponse remitterAmlCheckResponse) throws JsonProcessingException {
		remitterAmlCheckResponse.setApplicantAppId(applicantId);
		remitterAmlCheckResponse.setCheckApiResponse(convertAmlCheckResponseObjectToJsonString(check));
		remitterAmlCheckResponse.setCheckId(check.getId());
		remitterAmlCheckResponse.setCheckResult(check.getResult());
		remitterAmlCheckResponse.setCheckStatus(check.getStatus());
		remitterAmlCheckResponse.setRemitterAmlCheckRequest(remitterAmlCheckRequest);
		remitterAmlCheckResponse.setReportApiResponse(convertAmlReportResponseObjectToJsonString(report));
		remitterAmlCheckResponse.setReportId(report.getId());
		remitterAmlCheckResponse.setReportName(reportName);
		remitterAmlCheckResponse.setReportResult(report.getResult());
		remitterAmlCheckResponse.setReportStatus(report.getStatus());
		remitterAmlCheckResponse.setUid(uid);
		remitterAmlCheckResponse.setUserId(username);
		
		remitterAmlCheckApiResponseRepository.save(remitterAmlCheckResponse);
	}

	private RemitterAmlCheckRequest populateAndSaveRemitterAmlCheckRequest(String uid, String username, String dob, String firstName,
			String lastName, String appId, String applicantId, Request checkRequest, String reportName, RemitterAmlCheckRequest remitterAmlCheckRequest) throws JsonProcessingException {
		remitterAmlCheckRequest.setApiRequest(convertAmlCheckRequestObjectToJsonString(checkRequest));
		remitterAmlCheckRequest.setApplicantAppId(applicantId);
		remitterAmlCheckRequest.setDob(dob);
		remitterAmlCheckRequest.setFirstName(firstName);
		remitterAmlCheckRequest.setLastName(lastName);
		remitterAmlCheckRequest.setOnfidoAppId(appId);
		remitterAmlCheckRequest.setReportNames(reportName);
		remitterAmlCheckRequest.setUid(uid);
		remitterAmlCheckRequest.setUserId(username);
		
		remitterAmlCheckApiRequestRepository.save(remitterAmlCheckRequest);
		return remitterAmlCheckRequest;
		
	}

	@Override
	public Map<String, Object> amlCheckForBeneficiary(Long beneficiaryId, String uid) {
		Optional<BeneficiaryUser> beneficiaryUser = beneficiaryUserRepository.findById(beneficiaryId);
		Map<String, Object> properties = new HashMap<String, Object>();
		LOG.info("Starting AML check for Beneficiary Id::::"+beneficiaryId);
		
		BeneficiaryAmlCheckRequest beneficiaryAmlCheckRequest = new BeneficiaryAmlCheckRequest();
		BeneficiaryAmlCheckResponse beneficiaryAmlCheckResponse = new BeneficiaryAmlCheckResponse();
		
		
		if(beneficiaryUser.isPresent()) {
			BeneficiaryUser beneUser = beneficiaryUser.get();
			
			boolean isComplete=false;
	        
	        try {
	             	properties = new HashMap<String, Object>();
	             	Onfido onfido = configuration.getOnfidoConfig();
	             	String applicantId = null;
	             	String firstName = null;
	             	String trimmedFirstName = null;
	             	String lastName = null;
	             	
	             	String fullName = beneUser.getFullName();
	             	if(fullName!=null) {
	             		
	             		if(beneUser.getLastName() !=null) {
	             			
	            			firstName = beneUser.getFullName();
	            			lastName = beneUser.getLastName();	             			
	             			
	             			Applicant newApplicant = onfido.applicant.create(Applicant.request().firstName(firstName).lastName(lastName));
	    	             	applicantId = newApplicant.getId();
	    	             	Applicant applicant = onfido.applicant.update(applicantId, Applicant.request().firstName(firstName).lastName(lastName));
	    	             	LOG.info("Before Update "+" fullName "+beneUser.getName() +" firstName::"+firstName +" lastName:::"+lastName+" appid:::"+applicantId);
	             		}else {
	             			firstName = fullName;
	             			lastName = ".";
	             			Applicant newApplicant = onfido.applicant.create(Applicant.request().firstName(firstName).lastName(lastName));
	    	             	applicantId = newApplicant.getId();
	    	             	LOG.info("Before Update "+" fullName "+beneUser.getName() +" firstName::"+firstName +" lastName:::"+lastName+" appid:::"+applicantId);
	             		}
	             	}
	             	
	                LOG.info("Beneficiary Id::::"+beneficiaryId);
	                LOG.info("Onfido Applicant Id:::"+applicantId+" Beneficiary Id::::"+beneficiaryId);
	                
	                // Viren - commenting following line because report for beneficiary AML check is showing the dot in last name.
	                //
	                String reportName = "watchlist_standard";
	                Check.Request  checkRequest = Check.request()
	                        .applicantId(applicantId)
	                        .reportNames(reportName);
	                
	                populateAndSaveBeneficiaryAmlCheckRequest(uid, firstName, lastName, applicantId, checkRequest, reportName, beneficiaryId, beneficiaryAmlCheckRequest);

	                Check check = onfido.check.create(checkRequest);
	                LOG.info("Check Id :" + check.getId() + "   Result:" + check.getResult() + "  ReportID" + check.getReportIds());
	                isComplete = false;
	                while (!isComplete) {
	                    try {

	                        Report report = onfido.report.find(check.getReportIds().get(0));
	                        LOG.info("Report Result for watch List" + report.getStatus());
	                        if (report.getStatus().equalsIgnoreCase("complete")) {
	                            isComplete = true;
	                            properties = report.getProperties();
	                            LOG.info("Beneficiary Id::"+beneficiaryId);
	                            LOG.info("Beneficiary Watchlist  properties" + properties);
	                            LOG.info("Beneficiary Watchlist SubResult " + report.getSubResult());
	                            LOG.info("Beneficiary Watchlist Result " + report.getResult());
	                            LOG.info("Beneficiary Watchlist BreakDown " + report.getBreakdown());

	                            for (Map.Entry<String,Object> entry : report.getBreakdown().entrySet())
	                            {
	                                LOG.info("Key = " + entry.getKey() +", Value = " + entry.getValue());

	                                for (Map.Entry<String,Object> entry1 : ((Map<String,Object>)entry.getValue()).entrySet())
	                                {
	                                    AMLVerification amlVerification = new AMLVerification();

	                                    amlVerification.setApplicationId(applicantId);
	                                    amlVerification.setSessionid(uid);
	                                    amlVerification.setKey(entry.getKey());
	                                    amlVerification.setValue(entry1.getValue().toString());
	                                    amlVerification.setBeneficiaryId(String.valueOf(beneficiaryId));
	                                    amlRepository.save(amlVerification);
	                                }
	                            }
	                            
	                            populateAndSaveBeneficiaryAmlCheckResponse(uid, firstName, lastName, applicantId, check, report, beneficiaryAmlCheckRequest, reportName, beneficiaryId, beneficiaryAmlCheckResponse);
	                        }
	                        Thread.sleep(500);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	         }catch(Exception e){
	            e.printStackTrace();
	            LOG.error("Error while connecting to Onfido server for AML check of beneficiary id::"+beneficiaryId);
	        }
	        return properties;
		}else {
			LOG.info("Beneficiary Record is not found for the beneficiary id::::"+beneficiaryId);
		}
		return properties;
	}
	
	private void populateAndSaveBeneficiaryAmlCheckResponse(String uid, String firstName, String lastName,
			String applicantId, Check check, Report report, BeneficiaryAmlCheckRequest beneficiaryAmlCheckRequest,
			String reportName, long beneficiaryId, BeneficiaryAmlCheckResponse beneficiaryAmlCheckResponse) throws JsonProcessingException {
		
		beneficiaryAmlCheckResponse.setApplicantAppId(applicantId);
		beneficiaryAmlCheckResponse.setBeneficiaryAmlCheckRequest(beneficiaryAmlCheckRequest);
		beneficiaryAmlCheckResponse.setBeneficiaryId(beneficiaryId);
		beneficiaryAmlCheckResponse.setCheckApiResponse(convertAmlCheckResponseObjectToJsonString(check));
		beneficiaryAmlCheckResponse.setCheckId(check.getId());
		beneficiaryAmlCheckResponse.setCheckResult(check.getResult());
		beneficiaryAmlCheckResponse.setCheckStatus(check.getStatus());
		beneficiaryAmlCheckResponse.setReportApiResponse(convertAmlReportResponseObjectToJsonString(report));
		beneficiaryAmlCheckResponse.setReportId(report.getId());
		beneficiaryAmlCheckResponse.setReportName(reportName);
		beneficiaryAmlCheckResponse.setReportResult(report.getResult());
		beneficiaryAmlCheckResponse.setReportStatus(report.getStatus());
		beneficiaryAmlCheckResponse.setUid(uid);
		
		UserCalculatorMapping userCalculatorMapping = getUserCalculatorMappingByUid(uid);
		beneficiaryAmlCheckResponse.setUserId((userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null) ? userCalculatorMapping.getUser().getEmail() : null);
		
		beneficiaryAmlCheckApiResponseRepository.save(beneficiaryAmlCheckResponse);
	}

	private BeneficiaryAmlCheckRequest populateAndSaveBeneficiaryAmlCheckRequest(String uid, String firstName,
			String lastName, String applicantId, Request checkRequest, String reportName, long beneficiaryId, BeneficiaryAmlCheckRequest beneficiaryAmlCheckRequest) throws JsonProcessingException {
		
		beneficiaryAmlCheckRequest.setApiRequest(convertAmlCheckRequestObjectToJsonString(checkRequest));
		beneficiaryAmlCheckRequest.setApplicantAppId(applicantId);
		beneficiaryAmlCheckRequest.setBeneficiaryId(beneficiaryId);
		beneficiaryAmlCheckRequest.setFirstName(firstName);
		beneficiaryAmlCheckRequest.setLastName(lastName);
		beneficiaryAmlCheckRequest.setReportNames(reportName);
		beneficiaryAmlCheckRequest.setUid(uid);
		
		UserCalculatorMapping userCalculatorMapping = getUserCalculatorMappingByUid(uid);
		beneficiaryAmlCheckRequest.setUserId((userCalculatorMapping!=null && userCalculatorMapping.getUser()!=null) ? userCalculatorMapping.getUser().getEmail() : null);
		
		beneficiaryAmlCheckApiRequestRepository.save(beneficiaryAmlCheckRequest);
		return beneficiaryAmlCheckRequest;
	}
	
	private UserCalculatorMapping getUserCalculatorMappingByUid(String uid) {
		return userCalculatorMappingService.findByUid(uid);
	}

	private String convertAmlCheckRequestObjectToJsonString(Request checkRequest) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();  
		return objMapper.writeValueAsString(checkRequest);  
	}
	
	private String convertAmlCheckResponseObjectToJsonString(Check check) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();  
		return objMapper.writeValueAsString(check);  
	}
	
	private String convertAmlReportResponseObjectToJsonString(Report report) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();  
		return objMapper.writeValueAsString(report);  
	}
	
}
