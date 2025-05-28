// src/main/java/rw/nbr/banking/entity/Message.java
package com.bnr.banking.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "email_sent")
    private boolean emailSent = false;

    // Constructors
    public Message() {
        this.dateTime = LocalDateTime.now();
    }

    public Message(Customer customer, String message) {
        this.customer = customer;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public boolean isEmailSent() { return emailSent; }
    public void setEmailSent(boolean emailSent) { this.emailSent = emailSent; }
}