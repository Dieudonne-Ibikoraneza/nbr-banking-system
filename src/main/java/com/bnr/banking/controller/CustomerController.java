package com.bnr.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bnr.banking.dto.CustomerRegistrationDto;
import com.bnr.banking.model.Customer;
import com.bnr.banking.service.CustomerService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody CustomerRegistrationDto dto) {
        Customer customer = customerService.registerCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/account/{account}")
    public ResponseEntity<Customer> getCustomerByAccount(@PathVariable String account) {
        Customer customer = customerService.getCustomerByAccount(account);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
                                                   @Valid @RequestBody CustomerRegistrationDto dto) {
        Customer customer = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}