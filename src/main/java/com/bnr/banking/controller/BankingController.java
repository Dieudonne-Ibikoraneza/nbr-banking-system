package com.bnr.banking.controller;

import com.bnr.banking.dto.TransactionDto;
import com.bnr.banking.dto.TransferDto;
import com.bnr.banking.model.Banking;
import com.bnr.banking.service.BankingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banking")
@Validated
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/save")
    public ResponseEntity<Banking> saveMoney(@Valid @RequestBody TransactionDto dto) {
        Banking banking = bankingService.saveMoney(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(banking);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Banking> withdrawMoney(@Valid @RequestBody TransactionDto dto) {
        Banking banking = bankingService.withdrawMoney(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(banking);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Banking> transferMoney(@Valid @RequestBody TransferDto dto) {
        Banking banking = bankingService.transferMoney(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(banking);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Banking>> getAllTransactions() {
        List<Banking> transactions = bankingService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Banking> getTransactionById(@PathVariable Long id) {
        Banking transaction = bankingService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions/customer/{customerId}")
    public ResponseEntity<List<Banking>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        List<Banking> transactions = bankingService.getTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/account/{account}")
    public ResponseEntity<List<Banking>> getTransactionsByAccount(@PathVariable String account) {
        List<Banking> transactions = bankingService.getTransactionsByAccount(account);
        return ResponseEntity.ok(transactions);
    }
}
