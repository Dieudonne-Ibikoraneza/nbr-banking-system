package com.bnr.banking.service;

import com.bnr.banking.dto.TransactionDto;
import com.bnr.banking.dto.TransferDto;
import com.bnr.banking.exception.CustomerNotFoundException;
import com.bnr.banking.exception.InsufficientFundsException;
import com.bnr.banking.model.Banking;
import com.bnr.banking.model.Customer;
import com.bnr.banking.model.TransactionType;
import com.bnr.banking.repository.BankingRepository;
import com.bnr.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@Transactional
public class BankingService {

    @Autowired
    private BankingRepository bankingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MessageService messageService;

    public Banking saveMoney(TransactionDto dto) {
        Customer customer = customerRepository.findByAccount(dto.getAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Account " + dto.getAccount() + " not found"));

        // Update customer balance
        customer.setBalance(customer.getBalance().add(dto.getAmount()));
        customerRepository.save(customer);

        // Create banking record
        Banking banking = new Banking(customer, dto.getAccount(), dto.getAmount(), TransactionType.SAVING);
        banking.setDescription(dto.getDescription());
        banking = bankingRepository.save(banking);

        // Send notification message
        String message = String.format("Dear %s, Your SAVING of %s on your account %s has been completed successfully.",
                customer.getFullName(), dto.getAmount(), dto.getAccount());
        messageService.createAndSendMessage(customer, message);

        return banking;
    }

    public Banking withdrawMoney(TransactionDto dto) {
        Customer customer = customerRepository.findByAccount(dto.getAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Account " + dto.getAccount() + " not found"));

        // Check if sufficient funds
        if (customer.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: " + customer.getBalance());
        }

        // Update customer balance
        customer.setBalance(customer.getBalance().subtract(dto.getAmount()));
        customerRepository.save(customer);

        // Create banking record
        Banking banking = new Banking(customer, dto.getAccount(), dto.getAmount(), TransactionType.WITHDRAW);
        banking.setDescription(dto.getDescription());
        banking = bankingRepository.save(banking);

        // Send notification message
        String message = String.format("Dear %s, Your WITHDRAW of %s on your account %s has been completed successfully.",
                customer.getFullName(), dto.getAmount(), dto.getAccount());
        messageService.createAndSendMessage(customer, message);

        return banking;
    }

    public Banking transferMoney(TransferDto dto) {
        // Get source customer
        Customer sourceCustomer = customerRepository.findByAccount(dto.getSourceAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Source account " + dto.getSourceAccount() + " not found"));

        // Get recipient customer
        Customer recipientCustomer = customerRepository.findByAccount(dto.getRecipientAccount())
                .orElseThrow(() -> new CustomerNotFoundException("Recipient account " + dto.getRecipientAccount() + " not found"));

        // Check if sufficient funds
        if (sourceCustomer.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: " + sourceCustomer.getBalance());
        }

        // Update balances
        sourceCustomer.setBalance(sourceCustomer.getBalance().subtract(dto.getAmount()));
        recipientCustomer.setBalance(recipientCustomer.getBalance().add(dto.getAmount()));

        customerRepository.save(sourceCustomer);
        customerRepository.save(recipientCustomer);

        // Create banking record for sender
        Banking senderTransaction = new Banking(sourceCustomer, dto.getSourceAccount(), dto.getAmount(), TransactionType.TRANSFER);
        senderTransaction.setRecipientAccount(dto.getRecipientAccount());
        senderTransaction.setDescription(dto.getDescription());
        senderTransaction = bankingRepository.save(senderTransaction);

        // Create banking record for recipient
        Banking recipientTransaction = new Banking(recipientCustomer, dto.getRecipientAccount(), dto.getAmount(), TransactionType.SAVING);
        recipientTransaction.setDescription("Transfer from " + dto.getSourceAccount());
        bankingRepository.save(recipientTransaction);

        // Send notification messages
        String senderMessage = String.format("Dear %s, Your TRANSFER of %s from your account %s to account %s has been completed successfully.",
                sourceCustomer.getFullName(), dto.getAmount(), dto.getSourceAccount(), dto.getRecipientAccount());
        messageService.createAndSendMessage(sourceCustomer, senderMessage);

        String recipientMessage = String.format("Dear %s, Your account %s has received a TRANSFER of %s from account %s.",
                recipientCustomer.getFullName(), dto.getRecipientAccount(), dto.getAmount(), dto.getSourceAccount());
        messageService.createAndSendMessage(recipientCustomer, recipientMessage);

        return senderTransaction;
    }

    public List<Banking> getTransactionsByCustomerId(Long customerId) {
        return bankingRepository.findByCustomerIdOrderByBankingDateTimeDesc(customerId);
    }

    public List<Banking> getTransactionsByAccount(String account) {
        return bankingRepository.findByAccountOrderByBankingDateTimeDesc(account);
    }

    public List<Banking> getAllTransactions() {
        return bankingRepository.findAll();
    }

    public Banking getTransactionById(Long id) {
        return bankingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction with ID " + id + " not found"));
    }
}
