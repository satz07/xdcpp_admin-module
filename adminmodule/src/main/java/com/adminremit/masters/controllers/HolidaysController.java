package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.dto.HolidayUploadDTO;
import com.adminremit.masters.dto.HolidaysRequest;
import com.adminremit.masters.dto.HolidaysResponse;
import com.adminremit.masters.enums.Days;
import com.adminremit.masters.enums.HolidayType;
import com.adminremit.masters.models.Holiday;
import com.adminremit.masters.service.*;
import com.github.javaparser.utils.Log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
@RestController
@RequestMapping("/holiday")
public class HolidaysController {

    @Autowired
    HolidaysService holidaysService;

    @Autowired
    private CountriesService countriesService;

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, Holiday holiday) {
        modelAndView.addObject("holiday", holiday);
        modelAndView.addObject("countries",countriesService.listOfCountries());
        modelAndView.addObject("types",Arrays.asList(HolidayType.values()));
        modelAndView.addObject("days",Arrays.asList(Days.values()));
        modelAndView.setViewName("masters/holiday/create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ModelAndView createHoliday(@Valid Holiday holiday, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException, NoSuchFieldException {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("holiday", holiday);
            modelAndView.addObject("countries", countriesService.listOfCountries());
            modelAndView.addObject("types", Arrays.asList(HolidayType.values()));
            modelAndView.addObject("days", Arrays.asList(Days.values()));
            modelAndView.addObject("errorMessage", "Please fill mandatory fields!!!");
            modelAndView.setViewName("masters/holiday/create");
            return modelAndView;
        } else {
            holidaysService.save(holiday);
            modelAndView.addObject("confirmationMessage", "Holiday has been added");
            List<HolidaysResponse> holidaysList = holidaysService.getHolidays();
            modelAndView.addObject("holiday", holidaysList);
        }
        return new ModelAndView("redirect:/holiday/list");
    }

    @RequestMapping(value = "/{holidayId}/patch",method = RequestMethod.GET)
    public ModelAndView editHoliday(ModelAndView model, BindingResult bindingResult, @PathVariable("holidayId") Long holidayId) {

        if(bindingResult.hasErrors()) {
            model.setViewName("masters/holiday/create");
        }
        Optional<Holiday> optional = holidaysService.getExistingHolidayByConditions(holidayId);
        if(optional.isPresent()) {
            Holiday holiday1 = optional.get();
            model.addObject("holiday", holiday1);
            model.addObject("countries", countriesService.listOfCountries());
            model.addObject("types", Arrays.asList(HolidayType.values()));
            model.addObject("days", Arrays.asList(Days.values()));
            model.setViewName("masters/holiday/create");
        } else  {
            model.addObject("holiday", new Holiday());
            model.setViewName("masters/holiday/create");
        }
        return model;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView getHolidays(ModelAndView modelAndView,@PageableDefault(size = 10) Pageable pageable) {
    	getWeekEnds();
        List<HolidaysResponse>  holidaysResponses = holidaysService.getHolidays();
        modelAndView.addObject("holiday",holidaysResponses);
        modelAndView.setViewName("masters/holiday/list");
       return modelAndView;
    }

    @RequestMapping(value = "/delete/{holidayId}",method = RequestMethod.GET)
    public ModelAndView deleteHoliday(ModelAndView modelAndView, @PathVariable("holidayId") Optional<Long> holidayId) {
        holidaysService.deleteHoliday(holidayId.get());
        modelAndView.addObject("confirmationMessage", "Holiday has been deleted!!");
        
        return new ModelAndView("redirect:/holiday/list");
    }

    @RequestMapping(value = "/uploadfile",method = RequestMethod.GET)
    public ModelAndView uploadFile(ModelAndView modelAndView, @ModelAttribute HolidayUploadDTO holidayUploadDTO ,BindingResult bindingResult) {
       modelAndView.addObject("holidayUploadDTO",holidayUploadDTO);
        modelAndView.setViewName("masters/holiday/uploadfile");
        return modelAndView;
    }

    @PostMapping("/import")
    public ModelAndView importHolidays(ModelAndView modelAndView,@ModelAttribute HolidayUploadDTO holidayUploadDTO) {
        holidaysService.importHolidays(holidayUploadDTO);
        List<HolidaysResponse>  holidaysResponses = holidaysService.getHolidays();
        modelAndView.addObject("holiday",holidaysResponses);
        modelAndView.setViewName("masters/holiday/list");
        return modelAndView;

    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportHolidays(HttpServletResponse response) {
        String filename = "AndRemitHolidayCalendar.xlsx";
        InputStreamResource file = new InputStreamResource(holidaysService.exportHolidays());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @RequestMapping(value = "/checkerlist",method = RequestMethod.GET)
    public ModelAndView getCheckerHolidays(ModelAndView modelAndView,@PageableDefault(size = 10) Pageable pageable) {
        List<HolidaysResponse>  holidaysResponses = holidaysService.getCheckerHolidays();
        modelAndView.addObject("holiday",holidaysResponses);
        modelAndView.setViewName("masters/holiday/checkerlist");
        return modelAndView;
    }

    @RequestMapping(value = "/{holidayId}/approve",method = RequestMethod.GET)
    public ModelAndView approveHolidays(ModelAndView modelAndView,BindingResult bindingResult, @PathVariable("holidayId") Long holidayId) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("masters/holiday/create");
        }
        
        Optional<Holiday> optional = holidaysService.getExistingHolidayByConditions(holidayId);
        
        if(optional.isPresent()){
        	
            Holiday holiday = optional.get();
           log.info(holiday.getType().name());
            log.info(holiday.getCountry().getCountryName());
            
            holiday.setPublish(true);
            holidaysService.save(holiday);

        }
        List<HolidaysResponse>  holidaysResponses = holidaysService.getHolidays();
        modelAndView.addObject("holiday",holidaysResponses);
        modelAndView.setViewName("masters/holiday/checkerlist");
        return modelAndView;
    }

    @RequestMapping(value = "/reject/{holidayId}",method = RequestMethod.GET)
    public ModelAndView rejectHoliday(ModelAndView modelAndView, @PathVariable("holidayId")Long holidayId) {
        Optional<Holiday> optional = holidaysService.getExistingHolidayByConditions(holidayId);
        if(optional.isPresent()) {
            Holiday holiday = optional.get();
            holiday.setPublish(false);
            holidaysService.save(holiday);
        }
        modelAndView.addObject("confirmationMessage", "Holiday has been Rejected!!");
        List<HolidaysResponse>  holidaysResponses = holidaysService.getHolidays();
        modelAndView.addObject("holiday",holidaysResponses);
        modelAndView.setViewName("masters/holiday/checkerlist");
        return modelAndView;
    }
    public static void getWeekEnds(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int count = 0;
        try {
            Date d1 = formatter.parse("2021-12-06 00:00:01");
            Date d2 = formatter.parse("2021-12-31 00:00:01");
            count = saturdaysundaycount(d1,d2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Count of Sats & Sundays = "+count);
    }

    public static int saturdaysundaycount(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        int sundays = 0;
        int saturday = 0;
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        while (! c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
            	c1.get(Calendar.DATE);
            	c1.get(Calendar.MONTH);
            	
            	System.out.println(c1.getTime());
            	// Output "Wed Sep 26 14:23:28 EST 2012"

            	String formatted = format1.format(c1.getTime());
            	System.out.println(formatted);
                saturday++; 
            }
            if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                sundays++;
                String formatted = format1.format(c1.getTime());
            	System.out.println(formatted);
            }

            c1.add(Calendar.DATE, 1);
        }

        System.out.println("Saturday Count = "+saturday);
        System.out.println("Sunday Count = "+sundays);
        return saturday + sundays;
    }
}
