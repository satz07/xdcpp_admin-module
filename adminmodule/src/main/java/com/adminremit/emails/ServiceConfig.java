package com.adminremit.emails;


import lombok.Builder;
import lombok.Value;

import javax.sql.DataSource;

@Value
@Builder
public final class ServiceConfig {
    final private String smtpHost;
    final private String smtpPort;
    final private String username;
    final private String password;
    final private boolean mailSmtpAuth;
    final private boolean mailSmtpStarttlsEnable;
    final private DataSource dataSource;
    final private String messageTemplateLocation;
    final private String pdfAttachmentTemplateLocation;
    final private String pdfAttachmentLocation;
}