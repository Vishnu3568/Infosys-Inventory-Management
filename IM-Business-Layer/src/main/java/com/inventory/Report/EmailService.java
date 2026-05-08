package com.inventory.Report;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username:}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @PostConstruct
    public void init() {
        if (fromEmail == null || fromEmail.isBlank()) {
            System.err.println("WARNING: Email source not configured!");
        } else {
            System.out.println("EmailService ready using: " + fromEmail);
        }
    }

    public void sendEmail(String toAddress, String subject, String body) {
        if (toAddress == null || toAddress.isBlank()) {
            System.out.println("No recipient, skipping email.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toAddress);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            System.out.println("Email sent to: " + toAddress);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
