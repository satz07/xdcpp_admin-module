package com.adminremit.operations.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.operations.service.IFSCService;

@Controller
@RequestMapping(value = "/reports/ifsc")
public class IFSCController {

	private static final Logger LOG = LoggerFactory.getLogger(IFSCController.class);

	@Autowired
	IFSCService ifscSrevice;

	@RequestMapping(method = RequestMethod.GET)
	public String getRemitterSummary(Model model) {

		return "report/ifsc";

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView uploadFile(ModelAndView modelAndView, @RequestParam(value = "interimFile") MultipartFile file,
			BindingResult bindingResult) throws IOException {
		LOG.info("uploadFile    " + file.getSize());
		String isFileUploadSuccess = ifscSrevice.saveUploadedIFSCFiles(file);
		LOG.info("isFileUploadSuccess" + isFileUploadSuccess);

		modelAndView.setViewName("/report/ifsc");

		return modelAndView;
	}
}
