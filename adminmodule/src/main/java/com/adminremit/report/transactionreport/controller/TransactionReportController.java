package com.adminremit.report.transactionreport.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.models.DataTableResults;
import com.adminremit.common.models.Constants;
import com.adminremit.common.util.DateUtil;
import com.adminremit.report.transactionreport.model.TransactionReportDetails;
import com.adminremit.report.transactionreport.service.TransactionReportService;

@RestController
public class TransactionReportController {
	
	@Autowired
	private TransactionReportService transactionReportService;
	
	@RequestMapping(value = "reports/transaction-report", method = RequestMethod.GET)
	public ModelAndView getTransaction(ModelAndView modelAndView) throws ParseException {
		String fromToBookingDate = DateUtil.formatCurrentDateMinusDaysByGivenPattern(Constants.TRANSACTION_REPORT_BOOKING_DATE_FORMAT, 1);
		String toBookingDate = DateUtil.formatCurrentDateByGivenPattern(Constants.TRANSACTION_REPORT_BOOKING_DATE_FORMAT);
		
		modelAndView.addObject("fromToBookingDate", fromToBookingDate);
		modelAndView.addObject("toBookingDate", toBookingDate);
		modelAndView.setViewName("report/transaction-report");
		return modelAndView;
	}
	
	@RequestMapping(value = "reports/get-transaction-report", method = RequestMethod.GET)
	public DataTableResults<TransactionReportDetails> getTransactionReport(HttpServletRequest request) throws ParseException {
		String length = request.getParameter("length");//number of records per page
		String pageNumber = request.getParameter("pageNumber");
		String fromBookingDate = request.getParameter("fromBookingDate");
		String toBookingDate = request.getParameter("toBookingDate");
		String transactionNumber = request.getParameter("transactionNumber");
		String email = request.getParameter("email");
		String status = request.getParameter("status");
		String sortOrder = request.getParameter("order[0][dir]");
		
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("pageSize", length!=null ? length : String.valueOf(Constants.PAGE_SIZE));		
		dataMap.put("pageNum", pageNumber!=null ? pageNumber : "0");
		dataMap.put("sortOrder", sortOrder!=null ? sortOrder : "asc");
		
		if(fromBookingDate!=null && !fromBookingDate.isEmpty()) {
			dataMap.put("fromBookingDate", fromBookingDate);
		}
		
		if(toBookingDate!=null && !toBookingDate.isEmpty()) {
			dataMap.put("toBookingDate", toBookingDate);
		}
		
		if(transactionNumber!=null && !transactionNumber.isEmpty()) {
			dataMap.put("transactionNumber", transactionNumber);
		}
		
		if(email!=null && !email.isEmpty()) {
			dataMap.put("email", email);
		}
		
		if(status!=null && !status.isEmpty()) {
			dataMap.put("status", status);
		}
		
		
		return transactionReportService.getReportByPage(dataMap);
	  }
		
	@RequestMapping(value = "reports/download-transaction-report/{partnerId}/{fromBookingDate}/{toBookingDate}/{trnsno}/{email}/{status}", method = RequestMethod.GET)
	public void downloadTransactionReport(HttpServletRequest request, HttpServletResponse response, @PathVariable("partnerId") String partnerId,
				@PathVariable("fromBookingDate") String fromBookingDate, @PathVariable("toBookingDate") String toBookingDate, 
				@PathVariable("trnsno") String trnsno, @PathVariable("email") String email, @PathVariable("status") String status) throws IOException {
		
		Map<String, String> dataMap = new HashMap<>();
		
		if(fromBookingDate!=null && !fromBookingDate.isEmpty() && !fromBookingDate.equalsIgnoreCase("null")) {
			dataMap.put("fromBookingDate", fromBookingDate);
		}
		
		if(toBookingDate!=null && !toBookingDate.isEmpty() && !toBookingDate.equalsIgnoreCase("null")) {
			dataMap.put("toBookingDate", toBookingDate);
		}
		
		if(trnsno!=null && !trnsno.isEmpty() && !trnsno.equalsIgnoreCase("null")) {
			dataMap.put("transactionNumber", trnsno);
		}
		
		if(email!=null && !email.isEmpty() && !email.equalsIgnoreCase("null")) {
			dataMap.put("email", email);
		}
		
		if(status!=null && !status.isEmpty() && !status.equalsIgnoreCase("null")) {
			dataMap.put("status", status);
		}
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transaction_Reports.xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<TransactionReportDetails> transactionReportList = transactionReportService.getAllTransactionReportDetails(dataMap);
        if(transactionReportList!=null && !transactionReportList.isEmpty()) {
        	transactionReportService.generateExcelReport(transactionReportList, response);
        }
	}
	
}


