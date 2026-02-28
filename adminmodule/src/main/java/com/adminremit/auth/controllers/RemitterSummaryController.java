package com.adminremit.auth.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.dto.KYCDTO;
import com.adminremit.auth.dto.TransactionDTO;
import com.adminremit.auth.dto.UsersDTO;
import com.adminremit.auth.service.RegistrationService;
import com.adminremit.auth.service.TransactionService;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.user.model.Users;
import com.adminremit.user.service.UserCalculatorMappingService;
import com.adminremit.user.service.UsersService;

@Controller
@RequestMapping(value = "/")
public class RemitterSummaryController {

	@Autowired
	private UsersService userService;

	@Autowired
	private RegistrationService registerService;

	@Autowired
	private UserCalculatorMappingService calculatorMappingService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "reports/remitter-summary", method = RequestMethod.GET)
	public String getRemitterSummary(Model model) {

		return "report/remitter-summary";

	}

	@RequestMapping(value = "remitterSummary/list", method = RequestMethod.POST)
	public ModelAndView getRemitter(@RequestParam(value = "email", required = false) String email,
			ModelAndView modelAndView) throws ParseException {

		List<KYCDTO> list = new ArrayList<KYCDTO>();

		List<KYCDTO> kyc1 = transactionService.getKYC1ByEmail(email);
		List<KYCDTO> kyc2 = transactionService.getKYC2ByEmail(email);
	//	List<KYCDTO> aml = transactionService.getKYCAMLByEmail(email);

		for (int i = 0; i < kyc1.size(); i++) {
			list.add(kyc1.get(i));
		}
		for (int i = 0; i < kyc2.size(); i++) {
			list.add(kyc2.get(i));
		}
		/*
		 * for (int i = 0; i < aml.size(); i++) { list.add(aml.get(i)); }
		 */
		
		modelAndView.addObject("kyc1", list);
		modelAndView.addObject("recipients", transactionService.getRecipientByEmail(email));
		modelAndView.addObject("userLists", transactionService.getRemitterByEmail(email));
		modelAndView.addObject("eddDatas", transactionService.getEddDataByEmailId(email));
		modelAndView.addObject("transactionLists", transactionService.getAllTransactionByEmailId(email));
		modelAndView.setViewName("report/remitter-summary");

		return modelAndView;

	}

}
