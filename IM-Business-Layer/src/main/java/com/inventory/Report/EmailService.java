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
            SimpleMailMessage message = new SimpleMailMessage();
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
