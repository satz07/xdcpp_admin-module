package com.adminremit.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.models.Partner;
import com.adminremit.auth.service.PartnerService;

@Controller
public class RegistrationReport {

	 @Autowired
	 private PartnerService partnerService;
	
	 @RequestMapping(value="/reports/list",method = RequestMethod.GET)
	 public ModelAndView listPartners(ModelAndView modelAndView) {
		 List<Partner> partners = partnerService.listOfPartners();
	        modelAndView.addObject("partners", partners);
			modelAndView.setViewName("report/registration-report");
			return modelAndView;
		}
	 
}
