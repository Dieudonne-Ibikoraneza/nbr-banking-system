package com.bnr.banking.repository;

import com.bnr.banking.model.Banking;
import com.bnr.banking.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankingRepository extends JpaRepository<Banking, Long> {
    List<Banking> findByCustomerIdOrderByBankingDateTimeDesc(Long customerId);
    List<Banking> findByAccountOrderByBankingDateTime(String account);
    List<Banking> findByTypeOrderByBankingDateTime(TransactionType type);

    @Query("SELECT b from Banking b WHERE b.customer.id = :customerId AND b.type = :type ORDER BY b.bankingDateTime DESC")
    List<Banking> findByCustomerIdAndType(@Param("customerId") Long customerId, @Param("type") TransactionType type);
}
