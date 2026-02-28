package com.adminremit.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("sathishauit@gmail.com");
        javaMailSender.send(mailMessage);
    }

    public void send(SimpleMailMessage mailMessage) {
        mailMessage.setFrom(fromEmail);
        LOG.info("From Email!!!"+fromEmail);
        javaMailSender.send(mailMessage);
    }

}
