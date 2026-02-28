package com.adminremit.auth.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.dto.PersonalDetailsDTO;
import com.adminremit.auth.service.RegistrationService;
import com.adminremit.personaldetails.model.PersonalDetails;

@Controller
public class RegistrationReportController {

	@Autowired
	private RegistrationService service;

	@RequestMapping(value = "/reports/registration-report", method = RequestMethod.GET)
	public ModelAndView getRegistration(ModelAndView modelAndView) throws ParseException {

		List<PersonalDetails> paginatedRegistration = service.findAll();

		List<PersonalDetailsDTO> detailsDTOs = new ArrayList<PersonalDetailsDTO>();

		PersonalDetailsDTO dto = null;

		for (PersonalDetails pd : paginatedRegistration) {

			dto = new PersonalDetailsDTO(pd);
			dto.setAddress1(dto.getFlatNumber() + "," + dto.getStreetName() + "," + dto.getStreetNumber());
			dto.setAddress2(dto.getStreetType() + "," + dto.getSuburb());
			
			detailsDTOs.add(dto);

		}
		modelAndView.addObject("Registrations", detailsDTOs);
		modelAndView.setViewName("report/registration-report");
		return modelAndView;
	}

	@RequestMapping(value = "/reports/registration-report/{id}", method = RequestMethod.GET)
	public ModelAndView getRegistrationBySort(ModelAndView modelAndView, @PathVariable("id") String id) throws ParseException {

		List<PersonalDetails> paginatedRegistration = service.findAllBySort(id);

		List<PersonalDetailsDTO> detailsDTOs = new ArrayList<PersonalDetailsDTO>();

		PersonalDetailsDTO dto = null;

		for (PersonalDetails pd : paginatedRegistration) {

			dto = new PersonalDetailsDTO(pd);
			dto.setAddress1(dto.getFlatNumber() + "," + dto.getStreetName() + "," + dto.getStreetNumber());
			dto.setAddress2(dto.getStreetType() + "," + dto.getSuburb());

			detailsDTOs.add(dto);

		}
		modelAndView.addObject("Registrations", detailsDTOs);
		modelAndView.setViewName("report/registration-report");
		return modelAndView;
	}
}
