package com.inventory.Report;
 
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
 
@Service
public class EmailService {
 
    private final org.springframework.mail.javamail.JavaMailSender mailSender;
    
    @org.springframework.beans.factory.annotation.Value("${spring.mail.username:}")
    private String fromEmail;
 
    public EmailService(org.springframework.mail.javamail.JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @jakarta.annotation.PostConstruct
    public void init() {
        if (fromEmail == null || fromEmail.isBlank()) {
            System.err.println("EmailService WARNING: spring.mail.username is not set!");
        } else {
            System.out.println("EmailService initialized with sender: " + fromEmail);
        }
    }
 
    public void sendEmail(String toAddress, String subject, String body) {
        if (toAddress == null || toAddress.isBlank()) {
            System.out.println("Skipping email send: No recipient address provided.");
            return;
        }
 
        try {
            org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toAddress);
            message.setSubject(subject);
            message.setText(body);
            
            // It's good practice to set the 'From' address explicitly
            // message.setFrom("u.vishnu3568@gmail.com"); 

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toAddress);
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to send email to " + toAddress + " | Error: " + e.getMessage());
            throw new RuntimeException("Email delivery failed: " + e.getMessage());
        }
    }
}
