package com.adminremit.report.transactionreport.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.adminremit.auth.models.DataTableResults;
import com.adminremit.common.models.FilterAndSort;
import com.adminremit.common.models.SearchCriteria;
import com.adminremit.common.service.PredicateService;
import com.adminremit.common.util.DateUtil;
import com.adminremit.common.util.NumberUtil;
import com.adminremit.report.constants.ReportsConstants;
import com.adminremit.report.transactionreport.model.TransactionReportDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionReportServiceImpl implements TransactionReportService {
	
	@Autowired
    private PredicateService<TransactionReportDetails> predicateService;

    @Autowired
    private EntityManager entityManager;
	
	@Override
	public DataTableResults<TransactionReportDetails> getReportByPage(Map<String, String> dataMap) {
		DataTableResults<TransactionReportDetails> dataTableResults = new DataTableResults<TransactionReportDetails>();
		
		List<Order> orders = createOrders(dataMap.get("sortOrder"));
		Pageable page = PageRequest.of(Integer.parseInt(dataMap.get("pageNum")), Integer.parseInt(dataMap.get("pageSize")), Sort.by(orders));
		Set<SearchCriteria> criterias = createCriteria(dataMap);
		FilterAndSort filterAndSort = new FilterAndSort(null, criterias);
		
		String selectQuery = buildSelectQuery();		
		String fromWhereQuery = buildFromAndWhereClauseQuery();
		
		Long totalRecordsWithoutFilter = predicateService.getTotalRecords(fromWhereQuery, null);
		dataTableResults.setRecordsTotal(totalRecordsWithoutFilter!=null ? totalRecordsWithoutFilter : 0);

        Query query1 = entityManager.createQuery(selectQuery.concat("FROM TransactionWorkflow transactionWorkflow left outer join TransactionWorkflow transactionWorkflow2 on (transactionWorkflow2.referenceNo = transactionWorkflow.referenceNo and transactionWorkflow2.workflowStatus='STAGING_START') left outer join TransactionWorkflow transactionWorkflow3 on (transactionWorkflow3.referenceNo = transactionWorkflow.referenceNo and transactionWorkflow3.workflowStatus='DISBURSEMENT_CHECK' and transactionWorkflow3.actionStatus = 'Disburse') left outer join UserCalculatorMapping usercalc on(usercalc.id = transactionWorkflow.userCalculatorMapping.id) left outer join Users users on(users.id = usercalc.user.id) left outer join PersonalDetails personaldetails on(users.id = personaldetails.userId) left outer join BeneficiaryUser beneUser on(beneUser.id = usercalc.beneficiaryId) left outer join ReceiverTransactionModel receivertxn on(receivertxn.transactionCalId = usercalc.id) left outer join PurposeListMaster purposemaster on(receivertxn.purposeList = purposemaster.id) left outer join BeneficiaryAccount beneaccount on(beneaccount.beneficiaryUser.id = beneUser.id and usercalc.beneficiaryAccountNumber = beneaccount.accountNumber) left outer join BeneficiaryNameCheck beneNameCheck on(beneNameCheck.uniqueRequestNo = transactionWorkflow.referenceNo) where transactionWorkflow.isCompleted = false ORDER BY email DESC"));
        List<TransactionReportDetails> list = query1.getResultList();

		List<TransactionReportDetails> orderList = new ArrayList<>();
        List<TransactionReportDetails> orderList1 = new ArrayList<>();

		String tem = list.get(0).getEmail();
        for(int i=0;i<list.size();i++){
			if(tem.equals(list.get(i).getEmail())){
				if(list.get(i).getBookingDateTime()!=null){
					orderList.add(list.get(i));
				}
				if(i==(list.size()-1)){
				Collections.sort(orderList, Comparator.comparing(obj -> obj.getBookingDateTime()));
				for(int j=0;j<orderList.size();j++){
					orderList.get(j).setOrderSeq((long) (j+1));
				}
				orderList1.addAll(orderList);
				}
			}else{
				Collections.sort(orderList, Comparator.comparing(obj -> obj.getBookingDateTime()));
				for(int j=0;j<orderList.size();j++){
					orderList.get(j).setOrderSeq((long) (j+1));
				}
				orderList1.addAll(orderList);
				orderList.clear();
				if(list.get(i).getBookingDateTime()!=null){
					orderList.add(list.get(i));
				}
				tem = list.get(i).getEmail();
			}

		}

        for(int i=0;i<orderList1.size();i++){
			formatBookingDateAndTime(orderList1.get(i));
		}

		Page<TransactionReportDetails> reportByPage = predicateService.loadDataByPage(page, fromWhereQuery, selectQuery, filterAndSort, null, true);
		dataTableResults.setRecordsFiltered(reportByPage!=null ? reportByPage.getTotalElements(): 0);
		
		List<TransactionReportDetails> transactionReportDetailsList = reportByPage.getContent();
		
		if(transactionReportDetailsList!=null && !transactionReportDetailsList.isEmpty()) {			
			transactionReportDetailsList.stream()
			                            .forEach(transactionReportDetail->{
			                            	formatBookingDateAndTime(transactionReportDetail);			                				
			                				formatDisbursementDateAndTime(transactionReportDetail);			                				
			                				formatUtrTime(transactionReportDetail);			                				
			                				getRelationshipDescription(transactionReportDetail);			                				
			                				formatFxBaseRate(transactionReportDetail);
			                				getTransactionStatusDescription(transactionReportDetail);
											getDisbursementBank(transactionReportDetail);
			                				getDisbursementStatusDescription(transactionReportDetail);
			                				getReportSeqNo(transactionReportDetail,orderList1);
			                            });
			
		}
		dataTableResults.setData(transactionReportDetailsList);
		return dataTableResults;
	}

	private void getDisbursementStatusDescription(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getDisbursementBankStatus()!=null) {
			transactionReportDetail.setDisbursementStatusToDisplay(transactionReportDetail.getDisbursementBankStatus().getTransactionStatus());
		}
		
	}

	private void formatFxBaseRate(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getFxBaseRate()!=null) {
			transactionReportDetail.setFxBaseRateToDisplay(NumberUtil.formatValueToGivenDecimalPlaceFormatWithoutRounding("0.0000", transactionReportDetail.getFxBaseRate()));
		}
	}

	private void getRelationshipDescription(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getRelationshipWithRemitter()!=null) {
			transactionReportDetail.setRelationshipWithRemitterToDisplay(ReportsConstants.relationShipMap.get(transactionReportDetail.getRelationshipWithRemitter().intValue()));
		}
	}

	private void formatUtrTime(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getUtrTime()!=null) {
			transactionReportDetail.setUtrTimeToDisplay((DateUtil.formatDateAndTimeInGivenFormat("dd/MM/YYYY hh:mm:ss", transactionReportDetail.getUtrTime())));
		}
	}

	private void formatDisbursementDateAndTime(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getDisbursementDateAndTime()!=null) {
			transactionReportDetail.setDisbursementBookingDateAndTimeToDisplay(DateUtil.formatDateAndTimeInGivenFormat("dd/MM/YYYY hh:mm:ss", transactionReportDetail.getDisbursementDateAndTime()));
		}
	}

	private void formatBookingDateAndTime(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getBookingDateTime()!=null) {
			transactionReportDetail.setBookingDate(DateUtil.formatDateAndTimeInGivenFormat("dd/MM/yyyy", transactionReportDetail.getBookingDateTime()));
			transactionReportDetail.setBookingTime(DateUtil.formatDateAndTimeInGivenFormat("HH:mm:ss", transactionReportDetail.getBookingDateTime()));
		}
	}
	
	private void getTransactionStatusDescription(TransactionReportDetails transactionReportDetail) {
		if(transactionReportDetail.getStatus()!=null) {
			transactionReportDetail.setStatusToDisplay(transactionReportDetail.getStatus().name());
		}
	}

	private String buildFromAndWhereClauseQuery() {
		return "FROM TransactionWorkflow transactionWorkflow " +
		   		"left outer join TransactionWorkflow transactionWorkflow2 on (transactionWorkflow2.referenceNo = transactionWorkflow.referenceNo and transactionWorkflow2.workflowStatus='STAGING_START') " +
		   		"left outer join TransactionWorkflow transactionWorkflow3 on (transactionWorkflow3.referenceNo = transactionWorkflow.referenceNo and transactionWorkflow3.workflowStatus='DISBURSEMENT_CHECK' and transactionWorkflow3.actionStatus = 'Disburse') " +
		   		"left outer join UserCalculatorMapping usercalc on(usercalc.id = transactionWorkflow.userCalculatorMapping.id) " +
		   		"left outer join Users users on(users.id = usercalc.user.id) " +
		   		"left outer join PersonalDetails personaldetails on(users.id = personaldetails.userId) " +
		   		"left outer join BeneficiaryUser beneUser on(beneUser.id = usercalc.beneficiaryId) " +
		   		"left outer join ReceiverTransactionModel receivertxn on(receivertxn.transactionCalId = usercalc.id) " +
		   		"left outer join PurposeListMaster purposemaster on(receivertxn.purposeList = purposemaster.id) " +
		   		"left outer join BeneficiaryAccount beneaccount on(beneaccount.beneficiaryUser.id = beneUser.id and usercalc.beneficiaryAccountNumber = beneaccount.accountNumber) " +
		   		"left outer join BeneficiaryNameCheck beneNameCheck on(beneNameCheck.uniqueRequestNo = transactionWorkflow.referenceNo) " +
		   		"where transactionWorkflow.isCompleted = false ";
	}

	private String buildSelectQuery() {
		return "select new com.adminremit.report.transactionreport.model.TransactionReportDetails('Zyuper' as partnerId, "+
				 "transactionWorkflow2.createAt as bookingDateTime, "+
				 "users.email as email, "+
				 "transactionWorkflow.referenceNo as transactionNumber, "+
				 "usercalc.fromCurrencyCode	as originatingCurrency, "+
				 "usercalc.transferAmount as originatingAmount, "+
				 "usercalc.toCurrencyCode as receivingCurrency, "+
				 "usercalc.transactionFee as totalFees, "+
				 "usercalc.fxBaseRate as fxBaseRate, "+
				 "usercalc.marginSpread as marginSpread, "+
				 "usercalc.toCurrencyValue as exchangeRate, "+
				 "usercalc.totalConvertedValue as disbursementAmount, "+
				 "usercalc.paymentMode as paymentMode, "+
				 "usercalc.receiveMode as receiveMode, "+
				 "personaldetails.fullName|| ' ' ||personaldetails.middleName || ' ' || personaldetails.lastName as remitterName, "+
				 "beneUser.fullName as receipientName, "+
				 "beneUser.beneficiaryRelationship as relationshipWithRemitter, "+
				 "purposemaster.purpose as purpose, "+
				 "beneaccount.branchName as bankName, "+
				 "beneaccount.accountNumber as accountNumber, "+
				 "CASE WHEN beneaccount.accountType = 'UPI' THEN beneaccount.accountNumber ELSE '' END as vpaHandle, "+
				 "transactionWorkflow3.disbursementDateTime as disbursementDateAndTime, "+
				 //"'YES BANK' as disbursementBank, "+
				 "beneNameCheck.yesBankAPITransactionStatus as disbursementBankStatus, "+
				 "CASE WHEN (transactionWorkflow.workflowStatus = 'SENT_TO_BENEFICIARY' or transactionWorkflow.workflowStatus = 'COMPLETED') THEN transactionWorkflow.utrNumber  ELSE '' END as utrNumber, "+
				 "CASE WHEN (transactionWorkflow.workflowStatus = 'SENT_TO_BENEFICIARY' or transactionWorkflow.workflowStatus = 'COMPLETED') THEN transactionWorkflow.utrTime ELSE null END as utrTime , "+
				 "transactionWorkflow.workflowStatus as status )";
	}
	
	private List<Order> createOrders(String dataMap) {
    	List<Order> listOrders = new ArrayList<>();
    	listOrders.add(new Order((dataMap.equals("asc")? Direction.ASC : Direction.DESC), "bookingDateTime"));
    	    	
    	return listOrders;
    }

	private Set<SearchCriteria> createCriteria(Map<String, String> dataMap) {
    	Set<SearchCriteria> criterias = new HashSet<>();
    	
    	if(dataMap.get("fromBookingDate")!=null && !dataMap.get("fromBookingDate").isEmpty() && dataMap.get("toBookingDate")!=null && !dataMap.get("toBookingDate").isEmpty()) {
    		String toBookingDateStr = null;
    		Date formattedDate = DateUtil.convertStringToDate(dataMap.get("toBookingDate"),"yyyy-MM-dd");
    		if(formattedDate!=null) {
    			LocalDate localDate = DateUtil.convertDateToLocalDate(formattedDate);
    			if(localDate!=null) {
    				toBookingDateStr = DateUtil.addDaysToLocalDateAndConvertToString(localDate, 1);
        		}
    		}    		
    		
    		SearchCriteria c1 = new SearchCriteria("transactionWorkflow2.createAt", "between", dataMap.get("fromBookingDate"), toBookingDateStr);
    		criterias.add(c1);
    	}
    	
    	if(dataMap.get("transactionNumber")!=null && !dataMap.get("transactionNumber").isEmpty()) {
    		SearchCriteria c3 = new SearchCriteria("transactionWorkflow.referenceNo", "equals", dataMap.get("transactionNumber"), null);
    		criterias.add(c3);
    	}
    	
    	if(dataMap.get("email")!=null && !dataMap.get("email").isEmpty()) {
    		SearchCriteria c4 = new SearchCriteria("users.email", "equals", dataMap.get("email"), null);
    		criterias.add(c4);
    	}
    	
    	if(dataMap.get("status")!=null && !dataMap.get("status").isEmpty()) {
    		SearchCriteria c4 = new SearchCriteria("transactionWorkflow.workflowStatus", "equals", dataMap.get("status"), null);
    		criterias.add(c4);
    	}

		return criterias;
	}	

	@Override
	public List<TransactionReportDetails> getAllTransactionReportDetails(Map<String, String> dataMap) {
		List<Order> orders = createOrders(dataMap.get("sortOrder")== null ? "desc" : "asc");
		
		// page=0 & size=1 is just a dummy value. Only sorting is important here.
		Pageable page = PageRequest.of(0, 1, Sort.by(orders)); 
		Set<SearchCriteria> criterias = createCriteria(dataMap);
		FilterAndSort filterAndSort = new FilterAndSort(null, criterias);
		
		String selectQuery = buildSelectQuery();		
		String fromWhereQuery = buildFromAndWhereClauseQuery();
		
		List<TransactionReportDetails> transactionReportDetailsList = predicateService.loadAllData(page, fromWhereQuery, selectQuery, filterAndSort, null, true);
		
		if(transactionReportDetailsList!=null && !transactionReportDetailsList.isEmpty()) {			
			transactionReportDetailsList.stream()
			                            .forEach(transactionReportDetail->{
			                            	formatBookingDateAndTime(transactionReportDetail);			                				
			                				formatDisbursementDateAndTime(transactionReportDetail);			                				
			                				formatUtrTime(transactionReportDetail);			                				
			                				getRelationshipDescription(transactionReportDetail);			                				
			                				formatFxBaseRate(transactionReportDetail);
			                				getTransactionStatusDescription(transactionReportDetail);
			                				getDisbursementBank(transactionReportDetail);
			                				getDisbursementStatusDescription(transactionReportDetail);
			                            });
			
		}
		
		return transactionReportDetailsList;
	}

	private void getDisbursementBank(TransactionReportDetails transactionReportDetail) {
		// TODO Auto-generated method stub
		log.debug("Inside getDisbursementBank"+transactionReportDetail.getTransactionNumber());
		if(transactionReportDetail!=null && transactionReportDetail.getTransactionNumber()!=null) {
			
			if(transactionReportDetail.getTransactionNumber().endsWith("IN")) {
				transactionReportDetail.setDisbursementBank("Yes Bank");
			}else if(transactionReportDetail.getTransactionNumber().endsWith("NP")) {
				transactionReportDetail.setDisbursementBank("City Express");
			}
		}
	}

	@Override
	public void generateExcelReport(List<TransactionReportDetails> transactionReportDetails, HttpServletResponse response) {
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		SXSSFSheet sheet = workbook.createSheet("Transaction_Report");
				
		writeHeaderRow(workbook, sheet);
		writeDataRows(transactionReportDetails, sheet);
		
		ServletOutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			workbook.write(outputStream);
	        workbook.close();
	        outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeDataRows(List<TransactionReportDetails> transactionReportDetailList, SXSSFSheet sheet) {
		int rowCount = 1;
		
		for (TransactionReportDetails transactionReportDetails : transactionReportDetailList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(sheet, row, columnCount++, transactionReportDetails.getPartnerId());
            createCell(sheet, row, columnCount++, getBookingDateAndTime(transactionReportDetails.getBookingDate(), transactionReportDetails.getBookingTime()));
            createCell(sheet, row, columnCount++, transactionReportDetails.getEmail());
            createCell(sheet, row, columnCount++, transactionReportDetails.getTransactionNumber());
            createCell(sheet, row, columnCount++, transactionReportDetails.getOriginatingCurrency());
            createCell(sheet, row, columnCount++, transactionReportDetails.getOriginatingAmount()!=null ? transactionReportDetails.getOriginatingAmount().toString() : "");
            createCell(sheet, row, columnCount++, transactionReportDetails.getTotalFees()!=null ? transactionReportDetails.getTotalFees().toString() : "");
            createCell(sheet, row, columnCount++, transactionReportDetails.getFxBaseRateToDisplay());
            createCell(sheet, row, columnCount++, transactionReportDetails.getMarginSpread());
            createCell(sheet, row, columnCount++, transactionReportDetails.getExchangeRate()!=null ? transactionReportDetails.getExchangeRate().toString() : "");
            createCell(sheet, row, columnCount++, transactionReportDetails.getReceivingCurrency());
            createCell(sheet, row, columnCount++, transactionReportDetails.getDisbursementAmount()!=null ? transactionReportDetails.getDisbursementAmount().toString() : "");
            createCell(sheet, row, columnCount++, transactionReportDetails.getPaymentMode());
            createCell(sheet, row, columnCount++, transactionReportDetails.getReceiveMode());
            createCell(sheet, row, columnCount++, transactionReportDetails.getRemitterName());
            createCell(sheet, row, columnCount++, transactionReportDetails.getReceipientName());
            createCell(sheet, row, columnCount++, transactionReportDetails.getRelationshipWithRemitterToDisplay());
            createCell(sheet, row, columnCount++, transactionReportDetails.getPurpose());
            createCell(sheet, row, columnCount++, transactionReportDetails.getBankName());
            createCell(sheet, row, columnCount++, transactionReportDetails.getAccountNumber());
            createCell(sheet, row, columnCount++, transactionReportDetails.getDisbursementBookingDateAndTimeToDisplay());
            createCell(sheet, row, columnCount++, transactionReportDetails.getDisbursementBank());
            createCell(sheet, row, columnCount++, transactionReportDetails.getDisbursementStatusToDisplay());
            createCell(sheet, row, columnCount++, transactionReportDetails.getUtrNumber());
            createCell(sheet, row, columnCount++, transactionReportDetails.getUtrTimeToDisplay());
            createCell(sheet, row, columnCount++, transactionReportDetails.getStatusToDisplay());          
        }
		
	}

	private String getBookingDateAndTime(String bookingDate, String bookingTime) {
		String bookingDateToDisplay = bookingDate!=null ? bookingDate : "";
		String bookingTimeToDisplay = bookingTime!=null ? bookingTime : "";
		
		return (bookingDateToDisplay!="" && bookingTimeToDisplay!="") ? (bookingDateToDisplay +", "+bookingTimeToDisplay) : "";
	}

	private void writeHeaderRow(SXSSFWorkbook workbook, SXSSFSheet sheet) {
		List<String> headerList = Arrays.asList("Partner ID",
				"Booking Date and Time",
				"Email Address",
				"Reference Number",
				"Originating Currency",
				"Originating Amount",
				"Total Fees",
				"FX Base Rate",
				"Margin",
				"Exchange Rate",
				"Receiving Currency",
				"Disbursement Amount",
				"Payment mode",
				"Receive mode",
				"Remitter Name",
				"Recipient Name",
				"Relationship with Remitter",
				"Purpose",
				"Recipient Bank Name",
				"Recipient Bank Details",
				"Disbursement Date and Time",
				"Disbursement Partner Bank",
				"Disbursement Bank Status",
				"UTR Number",
				"UTR time stamp",
				"Status");
		

		Row row = sheet.createRow(0);
		
		for(int i=0; i<headerList.size(); i++) {
			createCell(sheet, row, i, headerList.get(i));   
		}
		
	}

	private void createCell(SXSSFSheet sheet, Row row, int columnCount, Object value) {
        //sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
    }

    private void getReportSeqNo(TransactionReportDetails transactionReportDetail, List<TransactionReportDetails> orderList1){
        for(int i=0;i<orderList1.size();i++){
        	if((transactionReportDetail.getBookingDate().equals(orderList1.get(i).getBookingDate())) &&
					(transactionReportDetail.getBookingTime().equals(orderList1.get(i).getBookingTime()))){
        		    transactionReportDetail.setOrderSeq(orderList1.get(i).getOrderSeq());
			}
		}

    }

}

