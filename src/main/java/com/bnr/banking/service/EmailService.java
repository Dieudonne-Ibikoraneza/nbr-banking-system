package com.bnr.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendTransactionNotification(String to, String customerName, String message) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject("BNR Banking Notification");
            mailMessage.setText(message);
            mailMessage.setFrom("noreply@bnr.com");

            mailSender.send(mailMessage);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
