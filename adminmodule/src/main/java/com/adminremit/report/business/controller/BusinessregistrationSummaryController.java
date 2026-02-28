package com.adminremit.report.business.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.report.service.BusinessRegistrationSummaryService;
import com.adminremit.report.vo.business.BusinessRegistrationSummary;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/")
public class BusinessregistrationSummaryController {
	
	@Autowired
	private BusinessRegistrationSummaryService businessRegistrationSummaryService;
	
	@RequestMapping(value = "reports/business-registration-summary", method = RequestMethod.GET)
	public String getRemitterSummary(Model model) {
		return "report/business-registration-summary";
	}
	
	@RequestMapping(value = "businessRegistrationSummary/list", method = RequestMethod.POST)
	public ModelAndView generateBusinessRegistrationReport(@RequestParam(value = "email") String email,
			ModelAndView modelAndView) throws ParseException {
		
		BusinessRegistrationSummary businessRegistrationSummary = businessRegistrationSummaryService.generateReport(email);	
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		
		try {
            jsonStr = Obj.writeValueAsString(businessRegistrationSummary);
		}catch (IOException e) {
            e.printStackTrace();
        }
		
		modelAndView.addObject("businessregistrationsummary", businessRegistrationSummary);
		modelAndView.addObject("businessregistrationsummaryjson", jsonStr);
		modelAndView.setViewName("report/business-registration-summary");
		
		return modelAndView;
	}

}
