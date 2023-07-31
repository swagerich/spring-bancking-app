package com.erich.dev.service.impl;

import com.erich.dev.dto.AccountDto;
import com.erich.dev.entity.Account;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.exception.OperationNotAllowedException;
import com.erich.dev.repository.AccountRepository;
import com.erich.dev.service.AccountService;
import com.erich.dev.util.validation.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;

    private final ObjectsValidator<AccountDto> validator;

    @Override
    @Transactional
    public AccountDto save(AccountDto dto) {
        //blok update
//        if(dto.getId() != null){
//            throw new OperationNotAllowedException("Operation no permitida, no se puede actualizar!");
//        }
        Account account = AccountDto.toEntity(dto);
        boolean isUser = accountRepo.findByUserId(account.getUser().getId()).isPresent();
        if (isUser && account.getUser().isActive()) {
            throw new OperationNotAllowedException("El cliente ya existe en la cuenta!");
        }
        if (account.getId() == null) {
            account.setNumber(generateNumRandom());
        }
        return AccountDto.fromEntity(accountRepo.save(account));
    }

    @Override
    public AccountDto update(AccountDto dto, Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findById(Long id) {
        return accountRepo.findById(id).map(AccountDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("ID no encontrado!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> findAll() {
        return Streamable.of(accountRepo.findAll())
                .stream().map(AccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        //ELIMINACION DE CUENTA SIEMPRE Y CUANDO SE ELIMINA EL CLIENTE!
        //OJO AQUI
        accountRepo.deleteById(id);
    }

    private String generateNumRandom() {
        String number = Iban.random(CountryCode.AT).toFormattedString();
        if (accountRepo.existsByNumber(number)) {
            generateNumRandom();
        }
        return number;
        // UUID uuid = UUID.randomUUID();
        // return uuid.toString().replace("-","").toUpperCase().substring(12);
    }
}
