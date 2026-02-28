package com.adminremit.emails;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

final class ServiceHelper {

    static Session createMailSession(final ServiceConfig serviceConfig){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", serviceConfig.isMailSmtpAuth());
        properties.put("mail.smtp.starttls.enable", serviceConfig.isMailSmtpStarttlsEnable());
        properties.put("mail.smtp.host", serviceConfig.getSmtpHost());
        properties.put("mail.smtp.port", serviceConfig.getSmtpPort());
        return Session.getInstance(
                properties,
                MailAuthenticator.getInstance(
                        serviceConfig.getUsername(),
                        serviceConfig.getPassword()));
    }

    static FileTemplateResolver createFileTemplateResolver(final String templateLocation){
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(templateLocation);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheTTLMs(3600000L);
        return templateResolver;
    }

    static TemplateEngine createTemplateEngine(final FileTemplateResolver templateResolver){
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    static MimeMessage createMimeMessage(
            final Session session, final String from, final String to, final String subject
    ) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        final String bcc = "object.ref.13@gmail.com";
        message.setRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
        return message;
    }

}
