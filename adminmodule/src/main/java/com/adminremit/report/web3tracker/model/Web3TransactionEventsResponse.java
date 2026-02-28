package com.adminremit.report.web3tracker.model;

import java.util.List;

import lombok.Data;

@Data
public class Web3TransactionEventsResponse {

    private Integer count;

    private List<Web3TransactionEvent> events;
}

