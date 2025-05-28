package com.bnr.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bnr.banking.dto.CustomerRegistrationDto;
import com.bnr.banking.model.Customer;
import com.bnr.banking.exception.CustomerAlreadyExistsException;
import com.bnr.banking.exception.CustomerNotFoundException;
import com.bnr.banking.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerRegistrationDto dto) {
        // Check if email already exists
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with email " + dto.getEmail() + " already exists");
        }

        // Generate unique account number
        String accountNumber = generateAccountNumber();
        while (customerRepository.existsByAccount(accountNumber)) {
            accountNumber = generateAccountNumber();
        }

        Customer customer = new Customer(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getMobile(),
                dto.getDob(),
                accountNumber,
                dto.getInitialBalance()
        );

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }

    public Customer getCustomerByAccount(String account) {
        return customerRepository.findByAccount(account)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with account " + account + " not found"));
    }

    public Customer updateCustomer(Long id, CustomerRegistrationDto dto) {
        Customer customer = getCustomerById(id);

        // Check if email is being changed and if new email already exists
        if (!customer.getEmail().equals(dto.getEmail()) &&
                customerRepository.existsByEmail(dto.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with email " + dto.getEmail() + " already exists");
        }

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setMobile(dto.getMobile());
        customer.setDob(dto.getDob());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    private String generateAccountNumber() {
        return "NBR" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
}