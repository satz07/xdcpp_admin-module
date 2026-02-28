package com.adminremit.common.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.masters.models.GlobalLimitMaster;
import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.user.repository.RemittUsersRepository;
import com.adminremit.user.service.CurrencyService;
import com.adminremit.user.service.UserCalculatorMappingService;

@Service
public class UtilitiesService {
	@Autowired
    private CurrencyService currencyService;
	
	@Autowired
	private RemittUsersRepository remittUsersRepository;
	
	@Autowired
	private UserCalculatorMappingService userCalculatorMappingService;

	public HashMap<String,String> getLimits(String emailAddress){
		HashMap<String,String> limits = new HashMap<String, String>();
		
		try {
			GlobalLimitMaster globalLimitMaster = currencyService.getLimits();
			
			limits.put("AnnualLimit", globalLimitMaster.getAnnual_limit().toString());
			limits.put("QuarterlyLimit", globalLimitMaster.getQuarterly_limit().toString());
			limits.put("MonthlyLimit", globalLimitMaster.getMonthly_limit().toString());
			limits.put("WeeklyLimit", globalLimitMaster.getWeekly_limit().toString());
			limits.put("DailyLimit", globalLimitMaster.getDaily_limit().toString());
	
			RemittUser user = remittUsersRepository.findByEmail(emailAddress);
			List<UserCalculatorMapping> userCalculatorMappings = userCalculatorMappingService.findAllByUser(user);
			BigDecimal daily = BigDecimal.valueOf(0.0); // In AUD
	        BigDecimal weekly = BigDecimal.valueOf(0.0); // in AUD
	        BigDecimal monthly = BigDecimal.valueOf(0.0); // In AUD
	        BigDecimal quarterly = BigDecimal.valueOf(0.0); // In AUD
	        BigDecimal yearly = BigDecimal.valueOf(0.0); // In AUD
	        long day = 24*60*60*1000;
	        long totalTransactions = 0;
	        for (int i = 0; i < userCalculatorMappings.size(); i++) {
	            String status = userCalculatorMappings.get(i).getStatus();
	            if (status != "BACKOFFICE_CANCEL_CONFIRMED" && status != "BACKOFFICE_CANCEL" && status != "CANCELLED" && status != "CANCELLED_BY_USER") {
	                if (!(userCalculatorMappings.get(i).getTransactionWorflow().size() > 0)) {
	                    continue;
	                }
	                
	                totalTransactions++;
	                //userCalculatorMappings.get(i).getTransferAmount();
	                // Within Past 24 Hours
	                if (userCalculatorMappings.get(i).getCreatedAt().getTime() > (System.currentTimeMillis() - day)) {
	                    daily = daily.add(new BigDecimal(userCalculatorMappings.get(i).getTransferAmount().toString()));
	                }

	                // Within Past Week
	                Calendar cal = Calendar.getInstance();
	                int week = cal.get(Calendar.WEEK_OF_YEAR);
	                int year = cal.get(Calendar.YEAR);

	                Calendar targetCalendar = Calendar.getInstance();
	                targetCalendar.setTime(userCalculatorMappings.get(i).getCreatedAt());
	                int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
	                int targetYear = targetCalendar.get(Calendar.YEAR);

	                if (week == targetWeek && year == targetYear) {
	                    weekly = weekly.add(new BigDecimal(userCalculatorMappings.get(i).getTransferAmount().toString()));
	                }

	                // Within Current Month
	                Calendar cal1 = Calendar.getInstance();
	                cal1.setTime(userCalculatorMappings.get(i).getCreatedAt());

	                Calendar targetCalendar1 = Calendar.getInstance();
	                targetCalendar1.setTime(new Date());

	                if (cal1.get(Calendar.YEAR) == targetCalendar1.get(Calendar.YEAR)) {
	                    if (cal1.get(Calendar.MONTH) == targetCalendar1.get(Calendar.MONTH)) {
	                        monthly = monthly.add(new BigDecimal(userCalculatorMappings.get(i).getTransferAmount().toString()));
	                    }
	                }

	                // Within Current Quarter
	                Calendar cal2 = Calendar.getInstance();
	                cal2.setTime(userCalculatorMappings.get(i).getCreatedAt());

	                Calendar targetCalendar2 = Calendar.getInstance();
	                targetCalendar2.setTime(new Date());

	                int current_quarter = (cal2.get(Calendar.MONTH) / 3) + 1;
	                int trans_quarter = (targetCalendar2.get(Calendar.MONTH) / 3) + 1;

	                if (current_quarter == trans_quarter) {
	                    quarterly = quarterly.add(new BigDecimal(userCalculatorMappings.get(i).getTransferAmount().toString()));
	                }

	                // Within Current Year
	                Calendar cal3 = Calendar.getInstance();
	                cal3.setTime(userCalculatorMappings.get(i).getCreatedAt());

	                Calendar targetCalendar3 = Calendar.getInstance();
	                targetCalendar3.setTime(new Date());

	                if (cal3.get(Calendar.YEAR) == targetCalendar3.get(Calendar.YEAR)) {
	                    yearly = yearly.add(new BigDecimal(userCalculatorMappings.get(i).getTransferAmount().toString()));
	                }
	            }
	        }
	        
	        BigDecimal avgTicketSize = yearly.divide(BigDecimal.valueOf(totalTransactions));
	        limits.put("DailyUserLimit", daily.toString());
	        limits.put("WeeklyUserLimit", weekly.toString());
	        limits.put("MonthlyUserLimit", monthly.toString());
	        limits.put("QuaterlyUserLimit", quarterly.toString());
	        limits.put("AnnuallyUserLimit", yearly.toString());
	        limits.put("AverageTicketSize", avgTicketSize.toString());
	        limits.put("LimitConsumed", yearly.toString());
	        
		}
		catch(Exception ex) {
			//Log.info("Error occurred while getting the limits" + ex.getMessage());
		}
		
		return limits;
	}
	
}
