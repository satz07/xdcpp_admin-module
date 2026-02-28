package com.adminremit.report;

import com.adminremit.report.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reports/reconciliation-report")
public class ReconciliationReportController {

    @Autowired
    private ReconciliationService reconciliationService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showReportPage(ModelAndView modelAndView) {
        modelAndView.setViewName("report/reconciliation-report");
        modelAndView.addObject("reconciliation",reconciliationService.getReconciliationReport());
        return modelAndView;
    }
}
