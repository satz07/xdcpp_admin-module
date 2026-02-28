package com.adminremit.report;

import com.adminremit.report.service.ComplainceService;
import com.adminremit.report.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
public class ComplainceChecklistController {

    @Autowired
    private ComplainceService complainceService;

    @RequestMapping(value = "/reports/complaince-checklist", method = RequestMethod.GET)
    public ModelAndView showReportPage(ModelAndView modelAndView) {
        modelAndView.setViewName("report/complaince-checklist");
        return modelAndView;
    }

    /*@RequestMapping(value = "complaince/list", method = RequestMethod.POST)
    public ModelAndView getTransaction(@RequestParam(value = "transactionId", required = false) String transactionId,
                                       ModelAndView modelAndView) throws ParseException {
        modelAndView.setViewName("report/complaince-checklist");
        modelAndView.addObject("complaince", complainceService.getComplainceReport(transactionId));
        return modelAndView;

    }*/
    
    @RequestMapping(value = "complaince/list", method = RequestMethod.POST)
    public ModelAndView getTransaction(@RequestParam(value = "transactionId", required = false) String transactionId,
                                       ModelAndView modelAndView) throws ParseException {
        modelAndView.setViewName("report/complaince-checklist");
        modelAndView.addObject("complaince", complainceService.extractReport(transactionId));
        return modelAndView;
    }
  }
