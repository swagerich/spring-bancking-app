package com.erich.dev.service.impl;

import com.erich.dev.dto.proyection.TransactionSumDetails;
import com.erich.dev.dto.proyection.impl.UsuariosDetailsImpl;
import com.erich.dev.repository.ClientRepository;
import com.erich.dev.repository.TransactionRepository;
import com.erich.dev.service.StatisticsService;
import com.erich.dev.util.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionRepository transactionRepo;

    private final ClientRepository clientRepo;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionSumDetails> findSumTransactionByDate(LocalDate startDate, LocalDate lastDate, Long userId) {
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime last = LocalDateTime.of(lastDate, LocalTime.of(23, 59, 59));
        return transactionRepo.findAllSumTransactionByDate(start, last, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAccountBalance(Long userId) {
        return transactionRepo.findAccountAmount(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal highTranfer(Long userId) {
        return transactionRepo.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERENCIA);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal highDeposit(Long userId) {
        return transactionRepo.findHighestAmountByTransactionType(userId, TransactionType.DEPOSITO);
    }

    @Override
    public List<UsuariosDetailsImpl> countUsersByDate(LocalDate start, LocalDate last) {
        LocalDateTime startDate = LocalDateTime.of(start, LocalTime.of(0, 0, 0));
        LocalDateTime lastDate = LocalDateTime.of(last, LocalTime.of(23, 59, 59));
        return clientRepo.countUsersByDateAndRole(startDate,lastDate);
    }
}
