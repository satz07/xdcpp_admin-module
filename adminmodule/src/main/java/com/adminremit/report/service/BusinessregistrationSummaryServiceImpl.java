package com.adminremit.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.masters.models.Countries;
import com.adminremit.masters.repository.CountriesRepository;
import com.adminremit.report.model.business.BusinessActivityVo;
import com.adminremit.report.model.business.BusinessInfo;
import com.adminremit.report.model.business.BusinessSubActivity;
import com.adminremit.report.model.business.CompanyInfo;
import com.adminremit.report.model.business.DirectorDetails;
import com.adminremit.report.repo.BusinessActivityRepository;
import com.adminremit.report.repo.BusinessInfoRepository;
import com.adminremit.report.repo.BusinessSubActivityRepository;
import com.adminremit.report.repo.CompanyInfoRepository;
import com.adminremit.report.repo.DirectorDetailsRepository;
import com.adminremit.report.vo.business.BeneficialOwner;
import com.adminremit.report.vo.business.BeneficialOwnersBaseDetails;
import com.adminremit.report.vo.business.BusinessActivity;
import com.adminremit.report.vo.business.BusinessDetails;
import com.adminremit.report.vo.business.BusinessRegistrationSummary;
import com.adminremit.report.vo.business.CompanyType;
import com.adminremit.report.vo.business.Director;
import com.adminremit.report.vo.business.Directordetails;
import com.adminremit.user.model.Users;
import com.adminremit.user.repository.UsersRepository;

@Service
public class BusinessregistrationSummaryServiceImpl implements BusinessRegistrationSummaryService {
	
	@Autowired
	private BusinessInfoRepository businessInfoRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private CountriesRepository countriesRepository;
	
	@Autowired
	private DirectorDetailsRepository directorDetailsRepository;
	
	@Autowired
	private BusinessActivityRepository businessActivityRepository;
	
	@Autowired
	private BusinessSubActivityRepository businessSubActivityRepository;
			
	@Override
	public BusinessRegistrationSummary generateReport(String emailId) {
		Users users = usersRepository.findByEmail(emailId);
		BusinessRegistrationSummary businessRegistrationSummary = new BusinessRegistrationSummary();
		populateBusinessDetails(businessRegistrationSummary, users);
		populateCompanyType(businessRegistrationSummary, users);
		populateBusinessActivity(businessRegistrationSummary, users);
		populateDirectorDetails(businessRegistrationSummary, users);
		populateBeneficiaryOwners(businessRegistrationSummary, users);
		
		return businessRegistrationSummary;
	}

	private void populateBeneficiaryOwners(BusinessRegistrationSummary businessRegistrationSummary, Users users) {
		BeneficialOwnersBaseDetails beneficialOwnersBaseDetails = new BeneficialOwnersBaseDetails();
		List<BeneficialOwner> beneficialOwnerList = new ArrayList<>();
		if(users!=null && users.getType()!=null && users.getType().equalsIgnoreCase("business")) {
			List<DirectorDetails> directorDetailsList = directorDetailsRepository.findByCompanyId(businessRegistrationSummary.getBusinessDetails().getId());
			if(directorDetailsList!=null && !directorDetailsList.isEmpty()) {
				
				Optional<DirectorDetails> directorDetailsOptional = directorDetailsList.stream()
				                   													  .filter(directorDetails->directorDetails!=null && directorDetails.getIsBeneficiary()!=null && directorDetails.getIsBeneficiary().equalsIgnoreCase("Beneficiary"))
				                                                                      .findAny();
				DirectorDetails director = directorDetailsOptional.orElse(null);
				if(director!=null) {
					beneficialOwnersBaseDetails.setCompanyIssuedShareCapital("Yes");
				}else {
					beneficialOwnersBaseDetails.setCompanyIssuedShareCapital("No");
				}
				
				Optional<DirectorDetails> beneficiaaryOwnerOptional = directorDetailsList.stream()
							  															 .filter(directorDetails->directorDetails!=null && directorDetails.getIsBeneficiaryOwner()!=null && directorDetails.getIsBeneficiary()!=null && directorDetails.getIsBeneficiary().equalsIgnoreCase("Beneficiary") && directorDetails.getIsBeneficiaryOwner().equalsIgnoreCase("Beneficiary Owner"))
							  															 .findAny();
				DirectorDetails directorDetailsBeneOwner = beneficiaaryOwnerOptional.orElse(null);
				if(directorDetailsBeneOwner!=null) {
					beneficialOwnersBaseDetails.setRegistrationDoneByBeneficialOwner("Yes");
				}else {
					beneficialOwnersBaseDetails.setRegistrationDoneByBeneficialOwner("No");
				}
				
				Optional<DirectorDetails> notBeneficiaaryOwnerOptional = directorDetailsList.stream()
							 															 .filter(directorDetails->directorDetails!=null && directorDetails.getIsBeneficiary()!=null && directorDetails.getIsBeneficiary().equalsIgnoreCase("Other Beneficiary") && directorDetails.getIsSenior())
							 															 .findAny();
				
				DirectorDetails notBeneficiaaryOwner = notBeneficiaaryOwnerOptional.orElse(null);
				if(notBeneficiaaryOwner!=null) {
					beneficialOwnersBaseDetails.setRegistrationDoneBySeniorMgmtOfficial("Yes");
				}else {
					beneficialOwnersBaseDetails.setRegistrationDoneBySeniorMgmtOfficial("No");
				}
				
				for(DirectorDetails directorDetails : directorDetailsList) {
					if(directorDetails!=null && directorDetails.getIsBeneficiaryOwner()!=null && directorDetails.getIsBeneficiary().equalsIgnoreCase("Beneficiary")) {
						BeneficialOwner beneficialOwner = new BeneficialOwner();
						beneficialOwner.setFullName(directorDetails.getFirstName()+" "+directorDetails.getMiddleName()+" "+directorDetails.getLastName());
						if(directorDetails.getDob()!=null) {
							//String formattedDateOfBirth = DateUtil.convertToSpecificDateWithNoTimeFormat("yyyy-MM-dd", "dd-MM-yyyy", directorDetails.getDob());							
							beneficialOwner.setDateOfBirth(directorDetails.getDob());
						}						
						
						beneficialOwnerList.add(beneficialOwner);
					}
					if(directorDetails!=null && directorDetails.getIsBeneficiaryOwner()!=null && directorDetails.getIsBeneficiary().equalsIgnoreCase("Other Beneficiary")) {
						BeneficialOwner beneficialOwner = new BeneficialOwner();
						beneficialOwner.setFullName(directorDetails.getFirstName()+" "+directorDetails.getMiddleName()+" "+directorDetails.getLastName());
						
						if(directorDetails.getDob()!=null) {
							//String formattedDateOfBirth = DateUtil.convertToSpecificDateWithNoTimeFormat("yyyy-MM-dd", "dd-MM-yyyy", directorDetails.getDob());							
							beneficialOwner.setDateOfBirth(directorDetails.getDob());
						}
						beneficialOwner.setRole(directorDetails.getRoleName());
						beneficialOwnerList.add(beneficialOwner);						
					}
						
				}
				beneficialOwnersBaseDetails.setBeneficialOwnerList(beneficialOwnerList);
				
			}
		}
		businessRegistrationSummary.setBeneficialOwnersBaseDetails(beneficialOwnersBaseDetails);	
	}

	private void populateDirectorDetails(BusinessRegistrationSummary businessRegistrationSummary, Users users) {
		Director director = new Director();
		List<Directordetails> directorList = new ArrayList<>();
		if(users!=null && users.getType()!=null && users.getType().equalsIgnoreCase("business")) {
			List<DirectorDetails> directorDetailsList = directorDetailsRepository.findByCompanyId(businessRegistrationSummary.getBusinessDetails().getId());
			
			if(directorDetailsList!=null && !directorDetailsList.isEmpty()) {
				for(DirectorDetails directorDetails : directorDetailsList) {
					if(directorDetails!=null && directorDetails.getDirectorType()!=null && directorDetails.getDirectorType().equalsIgnoreCase("Director")) {
						Directordetails directordetails = new Directordetails();
						directordetails.setFullName(directorDetails.getFirstName()+" "+directorDetails.getMiddleName()+" "+directorDetails.getLastName());
						directorList.add(directordetails);
					}
					if(directorDetails!=null && directorDetails.getDirectorType()!=null && directorDetails.getDirectorType().equalsIgnoreCase("Not a Director")) {
						Directordetails directordetails = new Directordetails();
						directordetails.setFullName(directorDetails.getFirstName()+" "+directorDetails.getMiddleName()+" "+directorDetails.getLastName());
						directorList.add(directordetails);
					}
				}
				
				Optional<DirectorDetails> directorDetailsOptional = directorDetailsList.stream()
				                   														.filter(directorDetails->directorDetails!=null && directorDetails.getDirectorType()!=null && directorDetails.getDirectorType().equalsIgnoreCase("Director"))
				                   														.findAny();
				DirectorDetails directorDetails = directorDetailsOptional.orElse(null);
				if(directorDetails!=null) {
					director.setRegistrationDoneByDirector("Yes");
				}else {
					director.setRegistrationDoneByDirector("No");
				}
				
				director.setDirectordetailsList(directorList);
			}
		}
		businessRegistrationSummary.setDirector(director);
	}

	private void populateBusinessActivity(BusinessRegistrationSummary businessRegistrationSummary, Users users) {
		BusinessActivity businessActivity = new BusinessActivity();
		
		if(users!=null && users.getType()!=null && users.getType().equalsIgnoreCase("business")) {
			BusinessInfo businessInfo = businessInfoRepository.findOneByUserId(users.getId());
			
			if(businessInfo!=null) {				
				if(businessInfo.getBusinessActivity()!=null) {
					BusinessActivityVo businessActivityVo = businessActivityRepository.findOneById(Long.parseLong(businessInfo.getBusinessActivity()));
					
					if(businessActivityVo!=null) {
						businessActivity.setBusinessActivity(businessActivityVo.getBusinessActivityName());
					}					
				}
				
				if(businessInfo.getBusinessSubActivity()!=null) {
					BusinessSubActivity businessSubActivity = businessSubActivityRepository.findOneById(Long.parseLong(businessInfo.getBusinessSubActivity()));
					if(businessSubActivity!=null) {
						businessActivity.setBusinessSubActivity(businessSubActivity.getBusinessSubActivityName());
					}
				}
				
				businessActivity.setAnnualBusinessTurnOver(businessInfo.getCompanyTurnover());
				businessActivity.setNumberOfEmployees(businessInfo.getCompanyEmployees());
				businessActivity.setOnlinePresence(businessInfo.getOnlinePresence());
				businessActivity.setWebsite(businessInfo.getCompanyWebSite());
				businessActivity.setSocialMediaLinks(businessInfo.getSocialMediaLinks());
			}
		}
		businessRegistrationSummary.setBusinessActivity(businessActivity);
	}

	private void populateCompanyType(BusinessRegistrationSummary businessRegistrationSummary, Users users) {
		CompanyType companyType = new CompanyType();
		
		if(users!=null && users.getType()!=null && users.getType().equalsIgnoreCase("business")) {
			CompanyInfo companyInfo = companyInfoRepository.findOneByBusinessId(businessRegistrationSummary.getBusinessDetails().getId());
			
			if(companyInfo!=null) {
				companyType.setId(companyInfo.getId());
				companyType.setTypeOfCompany(companyInfo.getCompanyType());
				companyType.setAfslLicensed(companyInfo.getAfslLicensed());
				companyType.setRegulatorName(companyInfo.getRegulatorName());
				companyType.setLicenseDetails(companyInfo.getLicenseNumber());
				companyType.setCompanyListedOnAusExchange(companyInfo.getListedOnStockExchange());
				companyType.setStockSymbol(companyInfo.getStockSymbol());
				companyType.setSubsidiaryAusPublicCompany(companyInfo.getSubsidiaryAusPublicCompany());
				companyType.setAustralianListedCompanyName(companyInfo.getListedCompanyName());
				companyType.setAustralianListedCompanyStockSymbol(companyInfo.getListedStockSymbol());
			}
		}
		businessRegistrationSummary.setCompanyType(companyType);
	}

	private void populateBusinessDetails(BusinessRegistrationSummary businessRegistrationSummary, Users users) {		
		BusinessDetails businessDetails = new BusinessDetails();
		
		if(users!=null && users.getType()!=null && users.getType().equalsIgnoreCase("business")) {
			BusinessInfo businessInfo = businessInfoRepository.findOneByUserId(users.getId());
			
			if (businessInfo != null) {
				businessDetails.setId(businessInfo.getId());
				Countries countries = countriesRepository.findByIsoCode(businessInfo.getRegisteredCountry());
				
				if(countries!=null) {
					businessDetails.setCountry(countries.getCountryName());
					businessDetails.setRegisteredCountry(countries.getCountryName());
				}				
				businessDetails.setBusnessStructure(businessInfo.getBusinessStructure());
				businessDetails.setFullBusinessName(businessInfo.getCompanyName());
				businessDetails.setAustralianCompnayNumber(businessInfo.getAcn());
				businessDetails.setAustralianBusinessNumber(businessInfo.getAbn());
				businessDetails.setRegisteredUnitNumber(businessInfo.getRegisteredUnitNumber());
				businessDetails.setRegisteredStreetNumber(businessInfo.getRegisteredStreetNumber());
				businessDetails.setRegisteredStreetName(businessInfo.getRegisteredStreetName());
				businessDetails.setRegisteredSuburb(businessInfo.getRegisteredSuburb());
				businessDetails.setRegisteredState(businessInfo.getRegisteredState());
				businessDetails.setRegisteredPostCode(businessInfo.getRegisteredPostCode());
				businessDetails.setBusinessUnitNumber(businessInfo.getBusinessUnitNumber());
				businessDetails.setBusinessStreetNumber(businessInfo.getBusinessStreetNumber());
				businessDetails.setBusinessStreetName(businessInfo.getBusinessStreetName());
				businessDetails.setBusinessSuburb(businessInfo.getBusinessSuburb());
				businessDetails.setBusinessPostCode(businessInfo.getBusinessPostCode());
				businessDetails.setBusinessState(businessInfo.getBusinessState());
				businessDetails.setBusinessCountry(businessInfo.getBusinessCountry1());
			}
		}
		
		businessRegistrationSummary.setBusinessDetails(businessDetails);		
	}
}
