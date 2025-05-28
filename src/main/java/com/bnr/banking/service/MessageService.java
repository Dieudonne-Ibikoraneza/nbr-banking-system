package com.bnr.banking.service;

import com.bnr.banking.model.Customer;
import com.bnr.banking.model.Message;
import com.bnr.banking.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EmailService emailService;

    public Message createAndSendMessage(Customer customer, String messageText) {
        Message message = new Message(customer, messageText);
        message = messageRepository.save(message);

        sendEmailNotification(customer, messageText);

        return message;
    }

    @Async
    public void sendEmailNotification(Customer customer, String messageText) {
        try {
            emailService.sendTransactionNotification(customer.getEmail(), customer.getFullName(), messageText);

            // Update message as sent
            List<Message> unsentMessages = messageRepository.findByEmailSentFalse();
            for(Message msg: unsentMessages) {
                if (msg.getCustomer().getId().equals(customer.getId()) &&
                msg.getMessage().equals(messageText)
                ) {
                    msg.setEmailSent(true);
                    messageRepository.save(msg);
                    break;
                }
            }
        } catch (Exception e) {
            // Log error but don't fail the transaction
            System.err.println("Failed to send email to " + customer.getEmail() + ": " + e.getMessage());
        }
    }

    public List<Message> getMessagesByCustomerId(Long customerId) {
        return messageRepository.findByCustomerIdOrderByDateTimeDesc(customerId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getUnsentMessages() {
        return messageRepository.findByEmailSentFalse();
    }
}
