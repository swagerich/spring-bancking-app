package com.erich.dev.repository;

import com.erich.dev.dto.proyection.TransactionSumDetails;
import com.erich.dev.entity.Transaction;
import com.erich.dev.util.enumeration.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=:userId")
    Page<Transaction> findUserById(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT sum(t.amount)  FROM Transaction t WHERE t.user.id =:userId")
    BigDecimal findAccountAmount(Long userId);

    @Query("SELECT max(abs(t.amount))  FROM Transaction  t WHERE t.user.id =:userId AND t.type =:transaction")
    BigDecimal findHighestAmountByTransactionType(Long userId, TransactionType transaction);

    @Query("SELECT t.transactionDate as transactionDate, sum(t.amount) as amount FROM Transaction t WHERE t.user.id=:userId AND t.creationDate between :start AND :end GROUP BY t.transactionDate")
    List<TransactionSumDetails> findAllSumTransactionByDate(LocalDateTime start, LocalDateTime end, Long userId);
}
