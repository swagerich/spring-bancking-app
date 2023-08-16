package com.erich.dev.service.impl;

import com.erich.dev.dto.TransactionDto;
import com.erich.dev.util.printer.ExcelMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl  {

    private final TransactionServiceImpl transactionService;

    public byte[] saveDataExcel(Long userId){
        List<TransactionDto> transactions = transactionService.findAllTransactionByUserId(userId);
        return  ExcelMethods.toExcel(transactions);
    }

}
