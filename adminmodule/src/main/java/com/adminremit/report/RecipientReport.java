package com.adminremit.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.report.vo.ReceipientVO;

@Controller
@RequestMapping("/reports/recipient-report")
public class RecipientReport {

    @Autowired
    private RecipientReportService recipientReportService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientReport.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showReportPage(){
        ModelAndView modelAndView = new ModelAndView();
        final LocalDate fromDate = LocalDate.now().minusMonths(1);
        final LocalDate toDate = LocalDate.now();
        String initialPageNumber = "0";
        
        
        Map<String, String> dataMap = new HashMap<>(4);
        dataMap.put("partnerId", StringUtils.EMPTY);
        dataMap.put("fromDate", null);
        dataMap.put("toDate", null);
        dataMap.put("email", StringUtils.EMPTY);
        dataMap.put("pageNum", initialPageNumber);
        
        Page<ReceipientVO> page = recipientReportService.findAllByPage(dataMap);
        if(page!=null) {
        	List<ReceipientVO> resultList = page.getContent();
        	
        	if(resultList!=null && !resultList.isEmpty()) {
        		modelAndView.addObject("result", resultList);
        		modelAndView.addObject("totalPage", page.getTotalPages());
        	}else {
        		modelAndView.addObject("result", Collections.emptyList());
        		modelAndView.addObject("totalPage", 0);
        	}
        }
        
        modelAndView.setViewName("report/recipient-report");
        modelAndView.addObject("criteria", dataMap);
        modelAndView.addObject("pageNum", Integer.parseInt(initialPageNumber)+1);
        
        return modelAndView;
    }
   
    @RequestMapping(value = "/filtered-data",method = RequestMethod.POST)
    public ModelMap getFilteredData(@RequestBody MultiValueMap<String, String> formData){

        formData.entrySet().stream().forEach( e-> LOGGER.info("Key: {} | Value: {}", e.getKey(), e.getValue()));
        final String partnerId = formData.getFirst("partner_id");
        final String fromDate = formData.getFirst("regdatefrom");
        final String toDate = formData.getFirst("regdateto");
        final String email = formData.getFirst("emailad");
        final String defaultPageNumber = "0";

        Map<String, String> dataMap = new HashMap<>(5);

        dataMap.put("partnerId", partnerId);
        dataMap.put("fromDate", fromDate);
        dataMap.put("toDate", toDate);
        dataMap.put("email", email);
        dataMap.put("pageNum", defaultPageNumber);
        
        ModelMap modelMap = new ModelMap();
        Page<ReceipientVO> page = recipientReportService.findAllByPage(dataMap);
        if(page!=null) {
        	List<ReceipientVO> resultList = page.getContent();
        	LOGGER.info("Result data size: {}", resultList.size());
        	
        	if(resultList!=null && !resultList.isEmpty()) {
        		modelMap.addAttribute("result", resultList);
        		modelMap.addAttribute("totalPage", page.getTotalPages());
        		modelMap.addAttribute("pageNum", Integer.parseInt(defaultPageNumber)+1);
        	}else {
        		modelMap.addAttribute("result", Collections.emptyList());
        		modelMap.addAttribute("totalPage", 0);
        		modelMap.addAttribute("pageNum", 0);
        	}
        }        
        
        modelMap.addAttribute("criteria", dataMap);
        modelMap.addAttribute("pageNum", Integer.parseInt(defaultPageNumber)+1);
        
        
        return modelMap;
    }
    
    @RequestMapping(value = "/load-data",method = RequestMethod.POST)
    public ModelAndView loadMoreData(@RequestBody MultiValueMap<String, String> formData){

        formData.entrySet().stream().forEach( e-> LOGGER.info("Key: {} | Value: {}", e.getKey(), e.getValue()));
        final String partnerId = formData.getFirst("partner_id");
        final String fromDate = formData.getFirst("regdatefrom");
        final String toDate = formData.getFirst("regdateto");
        final String email = formData.getFirst("emailad");
        final String pageNum = formData.getFirst("pageNum");

        Map<String, String> dataMap = new HashMap<>(5);

        dataMap.put("partnerId", partnerId);
        dataMap.put("fromDate", fromDate);
        dataMap.put("toDate", toDate);
        dataMap.put("email", email);
        dataMap.put("pageNum", pageNum);
        
        ModelAndView modelAndView = new ModelAndView();
        Page<ReceipientVO> page = recipientReportService.findAllByPage(dataMap);
        if(page!=null) {
        	List<ReceipientVO> resultList = page.getContent();
        	LOGGER.info("Result data size: {}", resultList.size());
        	
        	if(resultList!=null && !resultList.isEmpty()) {
        		modelAndView.addObject("result", resultList);
        		modelAndView.addObject("totalPage", page.getTotalPages());
        		modelAndView.addObject("pageNum", Integer.parseInt(pageNum)+1);
        	}else {
        		modelAndView.addObject("result", Collections.emptyList());
        		modelAndView.addObject("totalPage", 0);
        		modelAndView.addObject("pageNum", 0);
        	}
        } 
        
        modelAndView.setViewName("report/recipient-report");
        modelAndView.addObject("criteria", dataMap);
        
        
        return modelAndView;
    }
    
    /*@RequestMapping(method = RequestMethod.POST)
    public ModelMap getReceipientReportDetails(@RequestBody MultiValueMap<String, String> formData){
    	
    	formData.entrySet().stream().forEach( e-> LOGGER.info("Key: {} | Value: {}", e.getKey(), e.getValue()));
        final String partnerId = formData.getFirst("partner_id");
        final String fromDate = formData.getFirst("regdatefrom");
        final String toDate = formData.getFirst("regdateto");
        final String email = formData.getFirst("emailad");
        final String currentDataCount = formData.getFirst("current_data_count");
    	
    	return null;
    }*/
}
