package com.erich.dev.controller;

import com.erich.dev.controller.api.AccountApi;
import com.erich.dev.dto.AccountDto;
import com.erich.dev.service.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController implements AccountApi {
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<AccountDto> saveAccount(AccountDto account) {
        return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AccountDto>> accountAll() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDto> findId(Long id) {
        return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
