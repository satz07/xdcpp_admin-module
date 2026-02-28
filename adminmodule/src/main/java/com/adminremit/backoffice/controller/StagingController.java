package com.adminremit.backoffice.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.backoffice.dto.StagingSearchDTO;
import com.adminremit.backoffice.service.StagingCustomSearch;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentCodeMaster;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.PaymentCodeMasterService;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.TransactionWorkflowRepository;
import com.adminremit.operations.repository.UserCalculatorRepository;
import com.adminremit.operations.service.RemitTransactionService;
import com.adminremit.operations.service.TransactionWorkflowService;
import com.adminremit.user.*;
import com.adminremit.user.repository.*;
import com.adminremit.user.service.UserCalculatorMappingService;

@RestController
public class StagingController {

    private static final Logger LOG = LoggerFactory.getLogger(StagingController.class);

    @Autowired
    RemitTransactionService remitTransactionService;

    @Autowired
    StagingCustomSearch stagingCustomSearch;

    @Autowired
    PaymentCodeMasterService paymentCodeMasterService;

    @Autowired
    CurrenciesService currenciesService;


    @Autowired
    UserCalculatorRepository userCalculatorRepository;
    
    @Autowired
	UserCalculatorMappingService userCalculatorMappingService;

    @Autowired
    TransactionWorkflowService transactionWorkflowService;
    
    @Autowired
	TransactionWorkflowRepository transactionWorkflowRepository;




    @RequestMapping(value = "/backoffice/staging/stagingmaker",method = RequestMethod.GET)
    public ModelAndView opsListView(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();

        userCalculatorMappings = stagingCustomSearch.getAllStagingMakerList();


        if(userCalculatorMappings!=null)
        {
            LOG.info("Size of the Mapping "+userCalculatorMappings.size());
        }

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);
        modelAndView.addObject("stagingsearch",stagingSearchDTO);
          modelAndView.addObject("usertransactions",userCalculatorMappings);

        modelAndView.setViewName("backoffice/staging/stagingmaker");
        return modelAndView;
    }

    @RequestMapping(value = "/backoffice/staging/stagingmakersearch/",method = RequestMethod.POST)
    public ModelAndView searchOpListView(ModelAndView modelAndView, @ModelAttribute StagingSearchDTO stagingSearchDTO , BindingResult bindingResult) throws ParseException {
        Date uploadDate= null;

        List<UserCalculatorMapping> stagingCustomSearchList = stagingCustomSearch.getStagingMakerSearch(stagingSearchDTO);


        //LOG.info(stagingCustomSearchList.get(0).getBeneficiaryEmail());

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);


        modelAndView.addObject("stagingsearch",stagingSearchDTO);

        modelAndView.addObject("usertransactions",stagingCustomSearchList);

        modelAndView.setViewName("backoffice/staging/stagingmaker");
        return modelAndView;
    }

    @RequestMapping(value = "/backoffice/staging/stagingchecker",method = RequestMethod.GET)
    public ModelAndView stagingCheckerListView(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        List<UserCalculatorMapping> userCalculatorMappings = stagingCustomSearch.getAllStagingCheckerList();
        if(userCalculatorMappings!=null)
        {
            LOG.info("Size of the Mapping StagingCheckerListView"+userCalculatorMappings.size());
        }
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);

        modelAndView.addObject("stagingsearch",stagingSearchDTO);
        modelAndView.addObject("usertransactions",userCalculatorMappings);

        modelAndView.setViewName("backoffice/staging/stagingchecker");
        return modelAndView;
    }
    
    @RequestMapping(value = "/backoffice/staging/stagingCheckerCancelled/approve/{refId}",method = RequestMethod.GET) 
	public ModelAndView approveStagingCancellation(@PathVariable("refId") String refId, ModelAndView modelAndView, BindingResult bindingResult) throws Exception 
    {
    	UserCalculatorMapping userCalculatorMapping = userCalculatorMappingService.findByRefId(refId);
    	List<TransactionWorkflow> transactions = transactionWorkflowRepository.findAllByReferenceNo(userCalculatorMapping.getRefId());
		
		for(int i=0;i<transactions.size();i++) {
			if(transactions.get(i).isCompleted() == false && transactions.get(i).getWorkflowStatus() == WorkflowStatus.BACKOFFICE_CANCEL) {
				transactions.get(i).setCompleted(true);
				transactionWorkflowRepository.save(transactions.get(i));
				break;
			}
		}
		
		//userCalculatorRepository.cancel(userCalculatorMapping.getId(), "BACKOFFICE_CANCEL", "Cancelled in Staging Maker Queue");

		/*TransactionWorkflow transactionWorflow = new TransactionWorkflow();
		transactionWorflow.setUserCalculatorMapping(userCalculatorMapping);
		transactionWorflow.setWorkflowStatus(WorkflowStatus.BACKOFFICE_CANCEL);
		transactionWorflow.setCompleted(false);
		transactionWorflow.setReferenceNo(userCalculatorMapping.getRefId());
		transactionWorkflowRepository.save(transactionWorflow);*/
    	
		LOG.info("Reference Id of the approved staging:::"+refId);
		
		
		return new ModelAndView("redirect:/backoffice/staging/stagingchecker");
	  }

    @RequestMapping(value = "/backoffice/staging/stagingMakerCancelled/{id}",method = RequestMethod.GET)
        public ModelAndView staging(@PathVariable("id") String id,ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(id).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.STAGING_START);

        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.BACKOFFICE_CANCEL);
        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();

        userCalculatorMappings = stagingCustomSearch.getAllStagingMakerList();

        if(userCalculatorMappings!=null)
        {
            LOG.info("Size of the Mapping "+userCalculatorMappings.size());
        }

        List<PaymentCodeMaster> paymentCodeMasters = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.PAYMENT);
        List<PaymentCodeMaster> receiveCodeMaster = paymentCodeMasterService.listOfAllPaymentCodeByType(PaymentModeType.RECEIVE);
        List<Currencies> currencies = currenciesService.listOfCurrencies();

        modelAndView.addObject("paymentCodeList",paymentCodeMasters);
        modelAndView.addObject("receiveCodeList",receiveCodeMaster);
        modelAndView.addObject("currencies",currencies);

        modelAndView.addObject("stagingsearch",stagingSearchDTO);
        modelAndView.addObject("usertransactions",userCalculatorMappings);

        modelAndView.setViewName("backoffice/staging/stagingmaker");
        return modelAndView;

    }

}
