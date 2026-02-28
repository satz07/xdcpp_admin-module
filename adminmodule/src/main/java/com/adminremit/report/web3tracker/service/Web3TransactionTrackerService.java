package com.adminremit.report.web3tracker.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adminremit.report.web3tracker.model.Web3TransactionEvent;
import com.adminremit.report.web3tracker.model.WebhookEventLog;
import com.adminremit.report.web3tracker.repository.WebhookEventLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Web3TransactionTrackerService {

    private final WebhookEventLogRepository webhookEventLogRepository;

    /**
     * Fetch latest Web3 onramp/offramp events from the local webhook_event_log table.
     */
    public List<Web3TransactionEvent> getLatestEvents() {
        List<WebhookEventLog> entities = webhookEventLogRepository.findTop100ByOrderByEventCreatedAtDesc();
        if (entities == null || entities.isEmpty()) {
            log.debug("Web3 Transaction Tracker: no events found in webhook_event_log");
            return Collections.emptyList();
        }

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Web3TransactionEvent toDto(WebhookEventLog entity) {
        Web3TransactionEvent dto = new Web3TransactionEvent();
        dto.setEventId(entity.getEventId());
        dto.setEventType(entity.getEventType());
        dto.setReferenceId(entity.getReferenceId());
        dto.setStatus(entity.getStatus());
        dto.setEventCreatedAt(entity.getEventCreatedAt() != null ? entity.getEventCreatedAt().toString() : null);
        dto.setCreateAt(entity.getCreateAt() != null ? entity.getCreateAt().toString() : null);
        return dto;
    }
}

