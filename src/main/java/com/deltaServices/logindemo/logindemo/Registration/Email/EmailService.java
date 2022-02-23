package com.deltaServices.logindemo.logindemo.Registration.Email;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(String to, String emailBody) {
        try{
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");
            helper.setText(emailBody, true);
            helper.setTo(to);
            helper.setSubject("Confirm you email");
            helper.setFrom("confirmation@loginDemo.com");
            mailSender.send(msg);
        }catch(MessagingException ex){
            logger.error("Failed to send email", ex);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
