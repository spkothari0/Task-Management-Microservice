package com.shreyas.service.implementation;


import com.shreyas.AppConstant;
import com.shreyas.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppConstant appConstant;

    public EmailServiceImpl(JavaMailSender mailSender, AppConstant appConstant) {
        this.mailSender = mailSender;
        this.appConstant = appConstant;
    }

    @Override
    @Async
    public CompletableFuture<Boolean> sendEmail(String emailId, String Subject, String Message) {
        try{
            if(!appConstant.IsMailServiceEnabled()){
                logger.info("Mail service is disabled by developers.");
                return CompletableFuture.completedFuture(false);
            }
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setTo(emailId);
            helper.setSubject(Subject);
            helper.setText(Message, true);
            helper.setFrom(appConstant.Application_Email_Address());
            mailSender.send(mimeMessage);
            logger.info("Email for {} sent to {}",Subject, emailId);
            return CompletableFuture.completedFuture(true);
        }catch (MessagingException ex){
            logger.error(ex.getMessage(), ex);
            return CompletableFuture.completedFuture(false);
        }

    }
}

