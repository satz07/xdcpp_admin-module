package com.adminremit.emails;


import static com.adminremit.emails.ServiceHelper.createFileTemplateResolver;
import static com.adminremit.emails.ServiceHelper.createMailSession;
import static com.adminremit.emails.ServiceHelper.createMimeMessage;
import static com.adminremit.emails.ServiceHelper.createTemplateEngine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

final public class MailService {
	final private static  Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    final private ServiceConfig serviceConfig;
    final private Session mailSession;
    final private TemplateEngine emailTemplateEngine;

    @Value("${spring.mail.fromEmail}")
    private String fromEmail;
    
    private MailService(
            ServiceConfig serviceConfig,
            Session mailSession,
            TemplateEngine emailTemplateEngine) {
        this.serviceConfig = serviceConfig;
        this.mailSession = mailSession;
        this.emailTemplateEngine = emailTemplateEngine;
    }

    public static MailService getInstance(final ServiceConfig serviceConfig){
        final Session mailSession = createMailSession(serviceConfig);
        final FileTemplateResolver emailTemplateResolver = createFileTemplateResolver(serviceConfig.getMessageTemplateLocation());
        final TemplateEngine emailTemplateEngine = createTemplateEngine(emailTemplateResolver);
        final FileTemplateResolver attachmentTemplateResolver = createFileTemplateResolver(serviceConfig.getPdfAttachmentTemplateLocation());
        return new MailService(serviceConfig, mailSession, emailTemplateEngine);
    }

    public void sendEmailText(final String to, final String subject, final String text) throws MessagingException {
        MimeMessage message = createMimeMessage(mailSession, serviceConfig.getUsername(), to, subject);
        message.setText(text);
        Transport.send(message);
    }

    public void sendEmailHtml(final String to, final String subject, final String htmlString) throws MessagingException {
        MimeMessage message = createMimeMessage(mailSession, serviceConfig.getUsername(), to, subject);
        message.setDataHandler(new DataHandler(new HTMLDataSource(htmlString)));
        
        if(!fromEmail.isEmpty()) {
        	message.setFrom(new InternetAddress(fromEmail));	
        }
        
        Transport.send(message);
    }

    public void sendEmailHtml(
            final String to,
            final String subject,
            final String templateName,
            final Map<String, Object> valueMap) throws MessagingException {
        final Context context = new Context(Locale.ENGLISH, valueMap);
        final String htmlString = emailTemplateEngine.process(templateName, context);
        sendEmailHtml(to, subject, htmlString);
    }

    public void sendEmailHtml(
            final String to,
            final String subject,
            final String messageTemplateName,
            final Map<String, Object> messageValueMap,
            final String attachmentTemplateName,
            final Map<String, Object> attachmentValueMap
    ) throws MessagingException, IOException, DocumentException {

        sendEmailHtml(
                to,
                subject,
                messageTemplateName,
                messageValueMap,
                attachmentTemplateName,
                attachmentValueMap,
                UUID.randomUUID().toString());
    }

    public void sendEmailHtml(
            final String to,
            final String subject,
            final String messageTemplateName,
            final Map<String, Object> messageValueMap,
            final String attachmentTemplateName,
            final Map<String, Object> attachmentValueMap,
            final String attachmentName
    ) throws MessagingException, IOException, DocumentException {

        final Context messageContext = new Context(Locale.ENGLISH, messageValueMap);
        final String htmlMessageString = emailTemplateEngine.process(messageTemplateName, messageContext);
        MimeMessage message = createMimeMessage(mailSession, serviceConfig.getUsername(), to, subject);
        //MimeMessage message1 = createMimeMessage(mailSession, serviceConfig.getUsername(), to, "Receipt Log");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(new HTMLDataSource(htmlMessageString)));

        final String absoluteTemplatePath = serviceConfig.getPdfAttachmentTemplateLocation() + attachmentTemplateName + ".html";
        final String attachmentFileName = attachmentName + ".pdf";
        final String absoluteFilePath = serviceConfig.getPdfAttachmentLocation()+ attachmentFileName;
        LOGGER.debug("TemplateFileName", absoluteTemplatePath);
        final String htmlString = new String(Files.readAllBytes(Paths.get((absoluteTemplatePath))));
        final String updatedHtmlString = new StringSubstitutor(attachmentValueMap).replace(htmlString);
        
        LOGGER.debug("HTML", updatedHtmlString);
        OutputStream outputStream = new FileOutputStream(absoluteFilePath);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(updatedHtmlString);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(absoluteFilePath);
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(attachmentFileName);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }
    
    public void sendEmail(
            final String to,
            final String subject,
            final String message
    ) throws MessagingException, IOException, DocumentException {

        MimeMessage mes = createMimeMessage(mailSession, serviceConfig.getUsername(), to, subject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/html; charset=utf-8");
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mes.setContent(multipart);
        Transport.send(mes);
    }
    
    public String downloadPdfReceipt(
            final String attachmentTemplateName,
            final Map<String, Object> attachmentValueMap,
            final String attachmentName
    		)  throws MessagingException, IOException, DocumentException {
    	
        final String absoluteTemplatePath = serviceConfig.getPdfAttachmentTemplateLocation() + attachmentTemplateName + ".html";
        final String attachmentFileName = attachmentName + ".pdf";
        final String absoluteFilePath = serviceConfig.getPdfAttachmentLocation()+ attachmentFileName;
        LOGGER.debug("TemplateFileName", absoluteTemplatePath);
        final String htmlString = new String(Files.readAllBytes(Paths.get((absoluteTemplatePath))));
        final String updatedHtmlString = new StringSubstitutor(attachmentValueMap).replace(htmlString);
        
        LOGGER.debug("HTML", updatedHtmlString);
        OutputStream outputStream = new FileOutputStream(absoluteFilePath);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(updatedHtmlString);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        return attachmentFileName;
        
    }
}
