package com.bnr.banking.repository;

import com.bnr.banking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccount(String account);
    Optional<Customer> findByEmail(String email);

    boolean existsByAccount(String account);
    boolean existsByEmail(String email);
}
