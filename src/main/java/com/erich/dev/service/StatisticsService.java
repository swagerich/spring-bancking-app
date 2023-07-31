package com.erich.dev.service;

import com.erich.dev.dto.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {

    List<TransactionSumDetails> findSumTransactionByDate(LocalDate startDate, LocalDate lastDate, Long userId);

    BigDecimal getAccountBalance(Long userId);

    BigDecimal highTranfer(Long userId);

    BigDecimal highDeposit(Long userId);
}
