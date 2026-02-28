package com.adminremit.report.transactionreport.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.adminremit.auth.models.DataTableResults;
import com.adminremit.report.transactionreport.model.TransactionReportDetails;

public interface TransactionReportService {
	public DataTableResults<TransactionReportDetails> getReportByPage(final Map<String, String> dataMap);
	public void generateExcelReport(List<TransactionReportDetails> transactionReportDetails, HttpServletResponse response);
	public List<TransactionReportDetails> getAllTransactionReportDetails(final Map<String, String> dataMap);
}
