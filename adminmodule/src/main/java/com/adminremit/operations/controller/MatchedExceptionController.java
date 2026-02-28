package com.adminremit.operations.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.backoffice.dto.StagingSearchDTO;
import com.adminremit.operations.dto.MatchExceptionDTO;
import com.adminremit.operations.dto.ViewMatchExceptionDTO;
import com.adminremit.operations.model.ReconStatus;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.operations.repository.MatchedExceptionCustomSearch;
import com.adminremit.operations.repository.UserCalculatorRepository;
import com.adminremit.operations.service.TransactionWorkflowService;

@RestController
public class MatchedExceptionController {

    private static final Logger LOG = LoggerFactory.getLogger(MatchedExceptionController.class);

    @Autowired
    MatchedExceptionCustomSearch matchedExceptionCustomSearch;

    @Autowired
    UserCalculatorRepository userCalculatorRepository;

    @Autowired
    TransactionWorkflowService transactionWorkflowService;


    @RequestMapping(value = "/reconciliation/matchedentry/matchedentrymaker",method = RequestMethod.GET)
    public ModelAndView maker(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();

         List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionMakerList(WorkflowStatus.MATCHED_EXCEPTION.toString());

        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrymaker");
        return modelAndView;
    }

    @RequestMapping(value = "/reconciliation/matchedentry/matchedentrychecker",method = RequestMethod.GET)
    public ModelAndView checker(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionCheckerList(WorkflowStatus.MATCHED_EXCEPTION_CHECKER.toString());

        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrychecker");
        return modelAndView;
    }


    @RequestMapping(value = "/reconciliation/matchedentry/useraction/{id}",method = RequestMethod.GET)
    public ModelAndView useraction(@PathVariable("id") String id, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(id).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER,ReconStatus.USER_ACTION);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionMakerList(WorkflowStatus.MATCHED_EXCEPTION.toString());
        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrymaker");

        return modelAndView;

    }

    @RequestMapping(value = "/reconciliation/matchedentry/balanceamt/{id}",method = RequestMethod.GET)
    public ModelAndView balanceamt(@PathVariable("id") String id, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(id).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER,ReconStatus.BALANCE_AMT);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionMakerList(WorkflowStatus.MATCHED_EXCEPTION.toString());
        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrymaker");
        return modelAndView;

    }
    @RequestMapping(value = "/reconciliation/matchedentry/process/{id}",method = RequestMethod.GET)
    public ModelAndView process(@PathVariable("id") String id, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(id).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER,ReconStatus.PROCESS);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionMakerList(WorkflowStatus.MATCHED_EXCEPTION.toString());
        modelAndView.addObject("usertransactions",matchWithExceptionList);

        modelAndView.setViewName("reconciliation/matchedentry/matchedentrymaker");
        return modelAndView;

    }

    @RequestMapping(value = "/reconciliation/matchedentry/refund/{id}",method = RequestMethod.GET)
    public ModelAndView refund(@PathVariable("id") String id, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(id).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER,ReconStatus.REFUND);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionMakerList(WorkflowStatus.MATCHED_EXCEPTION.toString());
        modelAndView.addObject("usertransactions",matchWithExceptionList);

        modelAndView.setViewName("reconciliation/matchedentry/matchedentrymaker");
        return modelAndView;

    }

    @RequestMapping(value = "/reconciliation/matchedentry/viewtransaction/{refid}",method = RequestMethod.GET)
    public ViewMatchExceptionDTO viewTransaction(@PathVariable("refid") String refid, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();
        ViewMatchExceptionDTO viewMatchExceptionDTO = matchedExceptionCustomSearch.getMatchedExceptionMakerTransactionView(WorkflowStatus.MATCHED_EXCEPTION.toString(),refid,false);
        return viewMatchExceptionDTO;
    }

    @RequestMapping(value = "/reconciliation/matchedentry/viewCheckertransaction/{refid}",method = RequestMethod.GET)
    public ViewMatchExceptionDTO viewCheckTransaction(@PathVariable("refid") String refid, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
        List<UserCalculatorMapping> userCalculatorMappings = null;
        StagingSearchDTO stagingSearchDTO = new StagingSearchDTO();
        ViewMatchExceptionDTO viewMatchExceptionDTO = matchedExceptionCustomSearch.getViewMatchedExceptionView(WorkflowStatus.MATCHED_EXCEPTION_CHECKER.toString(),refid,true);
        return viewMatchExceptionDTO;
    }

    @RequestMapping(value = "/reconciliation/matchedentry/checkerapproved/{refid}",method = RequestMethod.GET)
    public ModelAndView checkApproved(@PathVariable("refid") String refid, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(refid).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.COMPLIANCE_START);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionCheckerList(WorkflowStatus.MATCHED_EXCEPTION_CHECKER.toString());
        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrychecker");
        return modelAndView;
    }

    @RequestMapping(value = "/reconciliation/matchedentry/checkerrejected/{refid}",method = RequestMethod.GET)
    public ModelAndView checkerRejected(@PathVariable("refid") String refid, ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {
        UserCalculatorMapping userCalculatorMapping = userCalculatorRepository.findAllByRefId(refid).get(0);
        transactionWorkflowService.updateTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION_CHECKER);
        transactionWorkflowService.createTransactionWorkFlow(userCalculatorMapping,WorkflowStatus.MATCHED_EXCEPTION);
        List<MatchExceptionDTO> matchWithExceptionList = matchedExceptionCustomSearch.getAllMatchedExceptionCheckerList(WorkflowStatus.MATCHED_EXCEPTION_CHECKER.toString());

        modelAndView.addObject("usertransactions",matchWithExceptionList);
        modelAndView.setViewName("reconciliation/matchedentry/matchedentrychecker");
        return modelAndView;
    }



}
