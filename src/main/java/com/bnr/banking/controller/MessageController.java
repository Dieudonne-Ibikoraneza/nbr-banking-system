package com.bnr.banking.controller;

import com.bnr.banking.model.Message;
import com.bnr.banking.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Message>> getMessagesByCustomerId(@PathVariable Long customerId) {
        List<Message> messages = messageService.getMessagesByCustomerId(customerId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unsent")
    public ResponseEntity<List<Message>> getUnsentMessages() {
        List<Message> messages = messageService.getUnsentMessages();
        return ResponseEntity.ok(messages);
    }
}