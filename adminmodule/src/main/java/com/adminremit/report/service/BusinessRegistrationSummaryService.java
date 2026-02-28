package com.adminremit.report.service;

import com.adminremit.report.vo.business.BusinessRegistrationSummary;

public interface BusinessRegistrationSummaryService {
	
	public BusinessRegistrationSummary generateReport(String emailId);

}
