package com.bnr.banking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerRegistrationDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance = BigDecimal.ZERO;

    // Constructors
    public CustomerRegistrationDto() {}

    // Getters and Setters
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

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}