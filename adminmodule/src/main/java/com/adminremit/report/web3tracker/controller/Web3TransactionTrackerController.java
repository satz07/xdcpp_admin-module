package com.adminremit.report.web3tracker.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.adminremit.report.web3tracker.model.Web3TransactionEvent;
import com.adminremit.report.web3tracker.service.Web3TransactionTrackerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Web3TransactionTrackerController {

    private final Web3TransactionTrackerService web3TransactionTrackerService;

    @GetMapping("reports/web3-transaction-tracker")
    public String getWeb3TransactionTracker(Model model) {
        List<Web3TransactionEvent> events = web3TransactionTrackerService.getLatestEvents();
        log.debug("Web3 Transaction Tracker: loaded {} events", events != null ? events.size() : 0);
        model.addAttribute("events", events);
        return "report/web3-transaction-tracker";
    }
}

