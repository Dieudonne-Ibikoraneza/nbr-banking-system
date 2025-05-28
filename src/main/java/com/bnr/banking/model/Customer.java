package com.bnr.banking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Column(nullable = false)
    private String mobile;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message = "Account number is required")
    @Column(name = "account", unique = true, nullable = false)
    private String account;

    @Positive(message = "Balance must be positive")
    @Column(precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "last_update_date_time")
    private LocalDateTime lastUpdateDateTime;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Banking> transactions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    // Constructors
    public Customer() {}

    public Customer(String firstName, String lastName, String email, String mobile,
                    LocalDate dob, String account, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.account = account;
        this.balance = balance;
        this.lastUpdateDateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        this.lastUpdateDateTime = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdateDateTime() { return lastUpdateDateTime; }
    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public List<Banking> getTransactions() { return transactions; }
    public void setTransactions(List<Banking> transactions) { this.transactions = transactions; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}