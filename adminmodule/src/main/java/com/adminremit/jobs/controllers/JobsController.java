package com.adminremit.jobs.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.adminremit.operations.service.TransactionWorkflowService;

@RestController
public class JobsController {
	
	@Autowired
	private TransactionWorkflowService transactionWorkflowService;
	
	private static final Logger LOG = LoggerFactory.getLogger(JobsController.class);
	
	  @Scheduled(cron = "${guaranted.rate.applicable.cron.expression}")
	  public void checkForGuarantedRateApplicable() throws Exception {
		LOG.info("Check For Guaranted Rate Applicable Cron Job started at::"+new Date());
		transactionWorkflowService.checkAndSetGuarantedRateApplicableFlag();
	  }
	  
	  @Scheduled(cron = "${transaction.staging.state.longer.duration.cron.expression}")
	  public void checkForAutoCancellationOfTxns() throws Exception {
		LOG.info("Check For Auto cancel of transactions Cron Job started at::"+new Date());
		transactionWorkflowService.eligibleForAutoCancelOfTxns();
	  }
}
