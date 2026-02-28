package com.adminremit.report.web3tracker.model;

import lombok.Data;

@Data
public class Web3TransactionEvent {

    private Long eventId;

    private String eventCreatedAt;

    private String eventType;

    private String referenceId;

    private String createAt;

    private String status;
}

