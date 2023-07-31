package com.erich.dev.service;

import com.erich.dev.dto.TransactionDto;

import java.util.List;

public interface TransactionService extends CrudBankService<TransactionDto,Long> {

    List<TransactionDto> findAllTransactionByUserId(Long userId);


}
