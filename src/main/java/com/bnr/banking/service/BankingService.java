package com.bnr.banking.service;

import com.bnr.banking.dto.TransactionDto;
import com.bnr.banking.exception.CustomerNotFoundException;
import com.bnr.banking.exception.InsufficientFundsException;
import com.bnr.banking.model.Banking;
import com.bnr.banking.model.Customer;
import com.bnr.banking.model.TransactionType;
import com.bnr.banking.repository.BankingRepository;
import com.bnr.banking.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;

@Service
@Transactional
public class BankingService {

    private BankingRepository bankingRepository;
    private CustomerRepository customerRepository;

    public Banking saveMoney(TransactionDto dto) {
        Customer customer = customerRepository.findByAccount(dto.getAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Account " + dto.getAccount() + " not found"));

        // Update customer balance
        customer.setBalance(customer.getBalance().add(dto.getAmount()));
        customerRepository.save(customer);

        // Create banking record
        Banking banking = new Banking(customer, dto.getAccount(), dto.getAmount(), TransactionType.SAVING);
        banking.setDescription(dto.getDescription());

        // Send notification message
        String message = String.format("Dear %s, Your SAVING of %s on your account %s has been completed successfully.", customer.getFullName(), dto.getAccount(), customer.getAccount());

        messageService.createAndSendMessage(customer, message);

        return banking;
    }

    public Baking withdrawMoney(TransactionDto dto) {
        Customer customer = customerRepository.findByAccount(dto.getAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Account " + dto.getAccount() + " not found"));

        // Check if sufficient funds
        if (customer.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: " + customer.getBalance());
        }

        // Update customer balance
        customer.setBalance(customer.getBalance().subtract(dto.getAmount()));
    }
}
