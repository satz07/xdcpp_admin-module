package com.adminremit.config;
import com.adminremit.emails.MailService;
import com.adminremit.emails.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MailReceiptConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailReceiptConfig.class);

    @Value("${app.mail.host}")
    private String smtpHost;

    @Value("${app.mail.port}")
    private String smtpPort;

    @Value("${app.mail.username}")
    private String username;

    @Value("${app.mail.password}")
    private String password;

    @Value("${app.mail.template.location}")
    private String messageTemplateLocation;

    @Value("${app.mail.pdf.template.location}")
    private String pdfAttachmentTemplateLocation;

    @Value("${app.mail.pdf.location}")
    private String pdfAttachmentLocation;

    @Autowired
    private DataSource dataSource;


    @Bean
    public MailService mailService(){
        ServiceConfig serviceConfig = ServiceConfig.builder()
                .smtpHost(smtpHost)
                .smtpPort(smtpPort)
                .username(username)
                .password(password)
                .mailSmtpAuth(Boolean.TRUE)
                .mailSmtpStarttlsEnable(Boolean.TRUE)
                .dataSource(dataSource)
                .messageTemplateLocation(messageTemplateLocation)
                .pdfAttachmentTemplateLocation(pdfAttachmentTemplateLocation)
                .pdfAttachmentLocation(pdfAttachmentLocation)
                .build();
        LOGGER.info("Service Config: {}",serviceConfig);
        return MailService.getInstance(serviceConfig);
    }
}
