package com.adminremit.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;							
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adminremit.common.models.Constants;

public class DateUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	private DateUtil() {
		
	}	
	
	public static String formatCurrentDateByGivenPattern(String pattern) {		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
		return now.format(formatter);
	}
	
	public static String formatCurrentDateMinusDaysByGivenPattern(String pattern, long days) {		
		LocalDateTime now = LocalDateTime.now().minusDays(days);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
		return now.format(formatter);
	}
	
	public static Date convertStringToDate(String dateStr, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date formattedDate = null;
		try {
			formattedDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return formattedDate;
	}
	
	public static LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static String addDaysToLocalDateAndConvertToString(LocalDate localDate, long numOfDaysToAdd) {
		LocalDate newLocalDate = localDate.plusDays(numOfDaysToAdd);
		return newLocalDate.toString();
	}
	
	public static long diffBetweenTwoDatesInMilliSeconds(Date d1, Date d2) {
		return d2.getTime() - d1.getTime();
	}
	
	public static long getNumberOfMinutesBetweenDatesExcludingWeekendAndHoliday(LocalDateTime startDateTime, LocalDateTime endDateTime, List<LocalDate> holidays) {
		long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);
		
		List<LocalDateTime> allDateRange = Stream.iterate(startDateTime, date -> date.plusDays(1))
                								 .limit(daysBetween+1)
                								 .collect(Collectors.toList());
		
		
		allDateRange.add(endDateTime);
		LOG.info("All Date Range===>"+allDateRange);
		
		long totalMinutesExcludingWeekendsAndHolidays = 0;
		
		for(int i=0; i<(allDateRange.size()-1); i++) {
			if(holidays!=null && holidays.contains(allDateRange.get(i).toLocalDate())){
				continue;
			}else {
				long minutes = ChronoUnit.MINUTES.between(allDateRange.get(i), allDateRange.get(i+1));
					totalMinutesExcludingWeekendsAndHolidays = totalMinutesExcludingWeekendsAndHolidays+minutes;
			}				
		}
		
		
		long finalHours = totalMinutesExcludingWeekendsAndHolidays / 60;
		long finalMinutes = totalMinutesExcludingWeekendsAndHolidays % 60;		
		LOG.info("Total final Hours And Minutes===>"+finalHours+":"+finalMinutes);
				
		return totalMinutesExcludingWeekendsAndHolidays;
	}
	
	public static LocalDate convertDateStringToLocalDate(String date) {
		return LocalDate.parse(date, formatter);		
	}
	
	public static LocalDate getCurrentLocalDate() {
		return  LocalDate.now();
	}
	
	public static String convertLocalDateToString(LocalDate localDate) {
		if(localDate!=null) {
			return localDate.toString();
		}
		
		return null;
	}
	
	public static LocalDateTime getDateTimeInAESTZone(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Australia/Sydney"));
    	
    	return zonedDateTime.toLocalDateTime();
	}
	
	public static String convertToSpecificDateFormat(String inputFormat, String outputFormat, String input) {
				
		if(inputFormat!=null) {
			DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern(inputFormat); 
			DateTimeFormatter newPattern = DateTimeFormatter.ofPattern(outputFormat);
			
			LocalDateTime datetime = LocalDateTime.parse(input, oldPattern);
			return datetime.format(newPattern);
		}
		
		return null;		
	}
	
	public static String convertToSpecificDateWithNoTimeFormat(String inputFormat, String outputFormat, String input) {
		DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern(inputFormat); 
		DateTimeFormatter newPattern = DateTimeFormatter.ofPattern(outputFormat);
		
		LocalDateTime datetime = LocalDate.parse(input, oldPattern).atStartOfDay();
		return datetime.format(newPattern);
	}
	
	public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);		
	}
	
	public static String getDateMonthYear(String inputDate) {
		return inputDate;
	}
	
	public static String substringAfterLast(String str, String separator) {
      if (isEmpty(str)) {
          return str;
      }
      if (isEmpty(separator)) {
          return "";
      }
      int pos = str.lastIndexOf(separator);
      if (pos == -1 || pos == (str.length() - separator.length())) {
          return "";
      }
      return str.substring(pos + separator.length());
    }
	
	public static boolean isEmpty(String str) {
	    return str == null || str.length() == 0;
	}
	
	public static String getDateFormatPattern(int millisecondsLength) {
		switch(millisecondsLength){ 	    
	    case 1: return Constants.RECEIPIENT_ADDITION_MODIFICATION_INPUT_FORMAT_1;  
	    case 2: return Constants.RECEIPIENT_ADDITION_MODIFICATION_INPUT_FORMAT_2;       
	    case 3: return Constants.RECEIPIENT_ADDITION_MODIFICATION_INPUT_FORMAT_3;   
	      
	    default:return null;  
	   }
	}
	
	public static String formatDateAndTimeInGivenFormat(String formatPattern, Date dateToBeFormatted) {
		DateFormat formatterDateAndTime = new SimpleDateFormat(formatPattern);
		return formatterDateAndTime.format(dateToBeFormatted);
	}
}
