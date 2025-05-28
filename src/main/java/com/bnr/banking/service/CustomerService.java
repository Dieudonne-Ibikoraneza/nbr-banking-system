package com.bnr.banking.service;

import com.bnr.banking.dto.CustomerRegistrationDto;
import com.bnr.banking.model.Customer;
import com.bnr.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerRegistrationDto dto){
        //check if email already exists
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new Customer("Customer already exists");
        }
    }
}
