package com.erich.dev.controller;

import com.erich.dev.controller.api.TransactionApi;
import com.erich.dev.dto.TransactionDto;
import com.erich.dev.dto.proyection.TransactionPageByUser;
import com.erich.dev.service.impl.TransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController implements TransactionApi {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<TransactionDto> saveTransaction(TransactionDto dto) {
        return new ResponseEntity<>(transactionService.save(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TransactionDto>> transactionAll() {
        return new ResponseEntity<>(transactionService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> findId(Long id) {
        return new ResponseEntity<>(transactionService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        transactionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TransactionDto>> findAllTransactionByUserId(Long userId) {
        return new ResponseEntity<>(transactionService.findAllTransactionByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionPageByUser> findAllTransactionPageByUserId(Long userId, Integer page, Integer size) {
        return new ResponseEntity<>(transactionService.findAllTransactionPageByUserId(userId, page, size), HttpStatus.OK);
    }
}
