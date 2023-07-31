package com.erich.dev.controller;

import com.erich.dev.controller.api.StatisticsApi;
import com.erich.dev.dto.TransactionSumDetails;
import com.erich.dev.service.impl.StatisticsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class StatisticsController implements StatisticsApi {

    private final StatisticsServiceImpl statisticsService;

    public StatisticsController(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public ResponseEntity<List<TransactionSumDetails>> findSumTransactionByDate(Long userId,
                                                                                LocalDate startDate,
                                                                                LocalDate lastDate) {
        return new ResponseEntity<>(statisticsService.findSumTransactionByDate(startDate,lastDate,userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> getAccountBalance(Long userId) {
        return new ResponseEntity<>(statisticsService.getAccountBalance(userId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> highTranfer(Long userId) {
        return new ResponseEntity<>(statisticsService.highTranfer(userId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> highDeposit(Long userId) {
        return new ResponseEntity<>(statisticsService.highDeposit(userId),HttpStatus.OK);
    }
}
