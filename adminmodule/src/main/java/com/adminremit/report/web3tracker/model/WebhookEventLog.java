package com.adminremit.report.web3tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Read-only view of Web3 webhook events stored in the webhook_event_log table.
 */
@Entity
@Table(name = "webhook_event_log")
@Data
public class WebhookEventLog {

    @Id
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_created_at")
    private Date eventCreatedAt;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "status")
    private String status;
}

