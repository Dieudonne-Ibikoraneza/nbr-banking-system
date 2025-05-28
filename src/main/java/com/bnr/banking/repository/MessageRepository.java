package com.bnr.banking.repository;

import com.bnr.banking.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCustomerIdOrderByDateTimeDesc(Long customerId);
    List<Message> findByEmailSentFalse();
}
