package com.adminremit.masters.service;

import com.adminremit.masters.dto.HolidayUploadDTO;
import com.adminremit.masters.dto.HolidaysRequest;
import com.adminremit.masters.dto.HolidaysResponse;
import com.adminremit.masters.models.Holiday;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface HolidaysService {
     void save(Holiday holiday);
     void editHoliday(HolidaysRequest holidaysRequest, Long holidayId);
     List<HolidaysResponse> getHolidays();
     void deleteHoliday(Long holidayId);
     void importHolidays(final HolidayUploadDTO file);
     ByteArrayInputStream exportHolidays();
     Optional<Holiday> getExistingHolidayByConditions(Long holidayId);
     List<HolidaysResponse> getCheckerHolidays();
     
     public List<String> getLimitedHolidaysBySendingCountry(long sendingCountryCode);
}
