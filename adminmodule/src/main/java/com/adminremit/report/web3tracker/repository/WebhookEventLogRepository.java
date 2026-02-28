package com.adminremit.report.web3tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminremit.report.web3tracker.model.WebhookEventLog;

public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLog, Long> {

    /**
     * Get latest events ordered by eventCreatedAt descending.
     */
    List<WebhookEventLog> findTop100ByOrderByEventCreatedAtDesc();
}

