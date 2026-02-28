package com.adminremit.masters.service;

import static com.adminremit.common.models.Constants.FILE_TYPE_INVALID_FOR_IMPORTING_COLLEGES;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adminremit.common.models.Constants;
import com.adminremit.common.util.DateUtil;
import com.adminremit.common.util.ExcelUtils;
import com.adminremit.masters.dto.HolidayUploadDTO;
import com.adminremit.masters.dto.HolidaysRequest;
import com.adminremit.masters.dto.HolidaysResponse;
import com.adminremit.masters.enums.Days;
import com.adminremit.masters.enums.ExcelHeader;
import com.adminremit.masters.enums.HolidayType;
import com.adminremit.masters.exception.ValidationException;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.CountryHolidays;
import com.adminremit.masters.models.Holiday;
import com.adminremit.masters.models.HolidayCalendarDetailsExcelHeader;
import com.adminremit.masters.repository.CountryHolidaysRepository;
import com.adminremit.masters.repository.HolidaysRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HolidaysServiceImpl implements HolidaysService {

    @Autowired
    HolidaysRepository holidaysRepository;

    @Autowired
    CountriesService countriesService;

    @Autowired
    CountryHolidaysRepository countryHolidaysRepository;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static String SHEET = "Sheet1";
    static String[] holidayHeaders ={"COUNTRY","TYPE","DATE","DAY","DESCRIPTION"};



    @Override
    public void save(Holiday holiday) {
        if(holiday.getId()==null) {
            holiday.setPublish(false);
            Holiday holidayNew = holidaysRepository.save(holiday);
            saveCountryHolidays(holiday.getCountry().getCountryName(), holidayNew);
        } else {
            Optional<Holiday> optional = holidaysRepository.findById(holiday.getId());
            if (optional.isPresent()) {
                Holiday holidayNew = holidaysRepository.save(holiday);
            }
        }
    }

    @Override
    public void editHoliday(HolidaysRequest holidaysRequest, Long holidayId) {
        Optional<Holiday> holidays = holidaysRepository.findById(holidayId);
        if(holidays.isPresent()) {
            Holiday holidayNew = mapToHolidays(holidaysRequest);
            holidayNew.setId(holidayId);
            holidaysRepository.save(holidayNew);
        }
    }

    @Override
    public List<HolidaysResponse> getHolidays() {
        return getHolidayResponses();
    }

    @Override
    public void deleteHoliday(Long holidayId) {
        Optional<Holiday> optional = holidaysRepository.findById(holidayId);
        if(optional.isPresent()) {
            Holiday holiday = optional.get();
            List<CountryHolidays> list = countryHolidaysRepository.findAll();
            for(CountryHolidays countryHolidays: list) {
                if(countryHolidays.getHoliday_id().equals(holiday.getId())) {
                    countryHolidays.setIsDeleted(true);
                    countryHolidaysRepository.save(countryHolidays);
                }
            }
            holiday.setIsDeleted(true);
            holidaysRepository.save(holiday);
        }

    }

    public  void importHolidays(final HolidayUploadDTO holidaysFile) {

        validateFile(holidaysFile.getFile());
        List<HolidaysRequest> holidaysRequests = getCreateHolidaysRequests(holidaysFile.getFile());
        holidaysRequests.forEach(holidaysRequest -> {
            Holiday hd = holidaysRepository.save(mapToHolidays(holidaysRequest));
            saveCountryHolidays(holidaysRequest.getCountryName(), hd);
        });
    }
    public ByteArrayInputStream exportHolidays() {

        ByteArrayInputStream in = exportHolidaysToExcel(getHolidayResponses());
        return in;
    }

    private List<HolidaysResponse> getHolidayResponses() {
        List<Holiday> holidays = holidaysRepository.findAll();
        List<Countries> countries = countriesService.listOfCountries();
        List<CountryHolidays> countryHolidays = countryHolidaysRepository.findAll();
        List<HolidaysResponse> responses=new ArrayList<>();
        countryHolidays.forEach(ch->{
            List<Holiday> holidayList = holidays.stream().filter(ele->ele.getId().equals(ch.getHoliday_id())).collect(Collectors.toList());
            List<Countries> countriesList = countries.stream().filter(c->c.getId().equals(ch.getCountry_id())).collect(Collectors.toList());
            if(holidays.size()>0 || countries.size()>0) {
                responses.add(mapToHolidayResponse(holidayList.get(0),countriesList.get(0)));
            }
        });
        return responses;
    }

    public Optional<Holiday> getExistingHolidayByConditions(Long holidayId) {
        return holidaysRepository.findById(holidayId);
    }

    @Override
    public List<HolidaysResponse> getCheckerHolidays() {
        List<Holiday> holidays = holidaysRepository.findAll();
        List<Countries> countries = countriesService.listOfCountries();
        List<CountryHolidays> countryHolidays = countryHolidaysRepository.findAll();
        List<HolidaysResponse> responses=new ArrayList<>();
        countryHolidays.forEach(ch->{
            List<Holiday> holidayList = holidays.stream().filter(ele->ele.getId().equals(ch.getHoliday_id())).collect(Collectors.toList());
            List<Countries> countriesList = countries.stream().filter(c->c.getId().equals(ch.getCountry_id())).collect(Collectors.toList());
            if((holidays.size()>0 || countries.size()>0)&& !holidayList.get(0).getPublish()) {
                responses.add(mapToHolidayResponse(holidayList.get(0),countriesList.get(0)));
            }
        });
        return responses;
    }
    private HolidaysResponse mapToHolidayResponse(Holiday holiday, Countries countries) {
        HolidaysResponse holidaysResponse = new HolidaysResponse();
        holidaysResponse.setId(holiday.getId());
        holidaysResponse.setCountryName(countries.getCountryName());
        holidaysResponse.setType(holiday.getType().name());
        if(holiday.getType().equals(HolidayType.HOLIDAY)) {
            holidaysResponse.setDate(holiday.getDate().toString());
        } else if(holiday.getType().equals(HolidayType.WEEKEND)) {
            holidaysResponse.setDay(holiday.getDay().name());
        }
        holidaysResponse.setPublish(holiday.getPublish());
       if(holiday.getDescription()!=null) {
           holidaysResponse.setDescription(holiday.getDescription());
       }
        return holidaysResponse;
    }

    private Holiday mapToHolidays(HolidaysRequest holidaysRequest)  {
        Holiday holiday = new Holiday();
        try {
            if(holidaysRequest.getType().equals(HolidayType.HOLIDAY.name())) {
                //holiday.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(holidaysRequest.getDate()));
                holiday.setDate(holiday.getDate());
                holiday.setType(HolidayType.HOLIDAY);
            } else if(holidaysRequest.getType().equals(HolidayType.WEEKEND.name())) {
                holiday.setDay(Days.valueOf(holidaysRequest.getDay()));
                holiday.setType(HolidayType.WEEKEND);
            } else {
                throw new ValidationException("Invalid Holiday Type");
            }
            if(holidaysRequest.getDescription()!=null) {
                holiday.setDescription(holidaysRequest.getDescription());
            }
        } catch (Exception e) {
            log.error("Please provide dates in proper format");
            throw new RuntimeException("Please provide dates in proper format");
        }

        return holiday;
    }

    private CountryHolidays mapToCountryHolidays(String countryName, Holiday holiday) {
        Countries countries = countriesService.findByCountryName(countryName);
        //countries.addHoliday(holiday);
        CountryHolidays countryHolidays = new CountryHolidays();
        countryHolidays.setHoliday_id(holiday.getId());
        countryHolidays.setCountry_id(countries.getId());
        return countryHolidays;
    }

    private void saveCountryHolidays(String countryName, Holiday holiday) {
        countryHolidaysRepository.save( mapToCountryHolidays(countryName,holiday));
    }

    //Import related Functionality
    private void validateFile(final MultipartFile file) {
        final String fileExtension = extractFileExtension(file.getOriginalFilename());
        if (!fileExtension.equalsIgnoreCase("xlsx") && !fileExtension.equalsIgnoreCase("xls")) {
            log.error("File extension:'{}' is not supported. Currently supported file types for importing colleges are:{}", fileExtension,
                    Arrays.asList("xlsx", "xls"));
            throw new ValidationException(FILE_TYPE_INVALID_FOR_IMPORTING_COLLEGES);
        }
    }

    public List<HolidaysRequest> getCreateHolidaysRequests(final MultipartFile file) {
        try (final Workbook workbook = ExcelUtils.createWorkbook(file)) {
            final Sheet holidayCalendarsDetailsSheet = workbook.getSheetAt(0);
            final List<HolidaysRequest> HolidayCalendarDetails = readHolidayCalendarDetailsFromExcelSheet(holidayCalendarsDetailsSheet);
            return HolidayCalendarDetails;
        } catch (final IOException e) {
            log.error("Unexpected error occurred while closing the file");
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String extractFileExtension(final String filename) {
        final int extIndex = filename.lastIndexOf('.');
        return extIndex == -1 ? "" : filename.substring(extIndex + 1);
    }

    private List<HolidaysRequest> readHolidayCalendarDetailsFromExcelSheet(final Sheet holidayCalendarsDetailsSheet) {
        log.info("Reading Holiday Calendar details from excel sheet");
        final Iterator<Row> holidayCalendarDetailsIterator = holidayCalendarsDetailsSheet.iterator();
        final Row holidayCalendarDetailsHeader = holidayCalendarDetailsIterator.next();

        validateHeaders(HolidayCalendarDetailsExcelHeader.class,holidayCalendarDetailsHeader);
        final List<HolidaysRequest> excelRows = new ArrayList<>();
        while (holidayCalendarDetailsIterator.hasNext()) {
            final Row currentRow = holidayCalendarDetailsIterator.next();
            final HolidaysRequest excelRow = parseHolidayCalendarDetails(currentRow);
            if (excelRow != null) {
                excelRows.add(excelRow);
            }
        }
        return excelRows;
    }

    private HolidaysRequest parseHolidayCalendarDetails(final Row currentRow) {
        final HolidaysRequest holidaysRequest = new HolidaysRequest();
        holidaysRequest.setCountryName(ExcelUtils.getStringCellValue(currentRow, HolidayCalendarDetailsExcelHeader.COUNTRYNAME.getIndex()));
        holidaysRequest.setType(ExcelUtils.getStringCellValue(currentRow,HolidayCalendarDetailsExcelHeader.TYPE.getIndex()));
        holidaysRequest.setDate(ExcelUtils.getStringCellValue(currentRow,HolidayCalendarDetailsExcelHeader.DATE.getIndex()));
        holidaysRequest.setDay(ExcelUtils.getStringCellValue(currentRow,HolidayCalendarDetailsExcelHeader.DAY.getIndex()));
        holidaysRequest.setDescription(ExcelUtils.getStringCellValue(currentRow,HolidayCalendarDetailsExcelHeader.DESCRIPTION.getIndex()));
        holidaysRequest.setPublish(false);
        return holidaysRequest;
    }

    private static void validateHeaders(final Class<? extends ExcelHeader> enumType, final Row headerRow) {
        log.debug("Validating excel sheet headers");
        final ExcelHeader[] headers = getValues(enumType);
        final Iterator<Cell> cellIterator = headerRow.cellIterator();
        for (final ExcelHeader header : headers) {
            if (!cellIterator.hasNext()) {
                log.error("{} headerRow not present in sheet", header.getHeaderText());
                throw new ValidationException("Sheet: headerRow not present");
            }
            final Cell nextHeader = cellIterator.next();
            if (!nextHeader.getStringCellValue().equals(header.getHeaderText())) {
                log.error("Sheet: Invalid headerRow present. Expecting headerRow text:{}, Actual headerRow text:{}", header.getHeaderText(),
                        nextHeader.getStringCellValue());
                throw new ValidationException("Sheet: headerRow not present");
            }
        }
    }

    private static ExcelHeader[] getValues(final Class<? extends ExcelHeader> enumType) {
        return enumType.getEnumConstants();
    }

    private static ByteArrayInputStream exportHolidaysToExcel(final List<HolidaysResponse> holidaysResponses) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final Sheet sheet = workbook.createSheet(SHEET);

            // Header
            final Row headerRow = sheet.createRow(0);
            for (int col = 0; col < holidayHeaders.length; col++) {
                final Cell cell = headerRow.createCell(col);
                cell.setCellValue(holidayHeaders[col]);
            }
            int rowIdx = 1;
            for(HolidaysResponse hr: holidaysResponses) {
                final Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(hr.getCountryName()); //Country
                row.createCell(1).setCellValue(hr.getType()); //Type
                row.createCell(2).setCellValue(hr.getDate()); //Date
                row.createCell(3).setCellValue(hr.getDay()); //Day
                row.createCell(4).setCellValue(hr.getDescription()); //Description
                rowIdx++;
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Data to Excel file. Caused by: " + e.getCause()+ "Description:"+ e.getMessage());
        }
    }

    private String getProcessingEndDate(String sendingCountry, String receivingCountry, LocalDate bookingDate, int paymentHrs, int receivingHrs) {

        List<Holiday> holidayList = holidaysRepository.getHolidaysByCountryNames(sendingCountry,receivingCountry);
        if(bookingDate==null) {
            bookingDate= LocalDate.now();
        }
        int expectedTimeInHrs =paymentHrs;
        for(Holiday holiday: holidayList) {
            if(holiday.getType().equals(HolidayType.WEEKEND)&& holiday.getDay().equals(bookingDate.getDayOfWeek())) {
                expectedTimeInHrs+=24;
                bookingDate.plusDays(1);
            }
            if(holiday.getType().equals(HolidayType.HOLIDAY)) {
                int val = bookingDate.compareTo(LocalDate.parse(holiday.getDate()));
                if(val==0) {
                    expectedTimeInHrs+=24;
                    bookingDate.plusDays(1);
                }
            }
        }
        return "";
    }
    
    private List<Order> createOrders() {
    	List<Order> listOrders = new ArrayList<>();
    	listOrders.add(new Order(Direction.ASC, "date"));
    	    	
    	return listOrders;
    }

	@Override
	public List<String> getLimitedHolidaysBySendingCountry(long sendingCountryCode) {
		List<Order> orders = createOrders();
		LocalDate today = DateUtil.getCurrentLocalDate();
		
		Pageable page = PageRequest.of(0, Constants.HOLIDAY_QUERY_RESULT_PAGE_SIZE, Sort.by(orders));		
		return holidaysRepository.getLimitedHolidaysBySendingCountry(sendingCountryCode, page, DateUtil.convertLocalDateToString(today));
	}
}
