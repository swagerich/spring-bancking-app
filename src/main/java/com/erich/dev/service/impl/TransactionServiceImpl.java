package com.erich.dev.service.impl;

import com.erich.dev.dto.TransactionDto;
import com.erich.dev.dto.proyection.TransactionPageByUser;
import com.erich.dev.entity.Transaction;
import com.erich.dev.entity.Usuario;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.repository.ClientRepository;
import com.erich.dev.repository.TransactionRepository;
import com.erich.dev.service.TransactionService;
import com.erich.dev.util.enumeration.TransactionType;
import com.erich.dev.util.validation.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepo;

    private final ClientRepository clientRepo;

    private final ObjectsValidator<TransactionDto> validator;

    @Override
    @Transactional
    public TransactionDto save(TransactionDto transactionDto) {
        Transaction transaction = TransactionDto.toEntity(transactionDto);
        boolean usuario = clientRepo.findById(transaction.getUser().getId()).isPresent();
        if (!usuario) {
            throw new EntityNotFoundException("User does not exist for the transaction!");
        }
        BigDecimal amount = transaction.getAmount()
                .multiply(BigDecimal.valueOf(transactionType(transaction.getType())));
        transaction.setAmount(amount);
        return TransactionDto.fromEntity(transactionRepo.save(transaction));
    }

    @Override
    public TransactionDto update(TransactionDto transactionDto, Long aLong) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto findById(Long id) {
        return transactionRepo.findById(id).map(TransactionDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("TransactionId not found"));
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findAll() {
        return Streamable.of(transactionRepo.findAll())
                .stream().map(TransactionDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        transactionRepo.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findAllTransactionByUserId(Long userId) {
        return transactionRepo.findAllByUserId(userId)
                .stream().map(TransactionDto::fromEntity).toList();
    }

    /**
     * @param userId page - size
     * @return TransactionPageByUser
     */
    @Transactional(readOnly = true)
    public TransactionPageByUser findAllTransactionPageByUserId(Long userId, Integer page, Integer size) {
        Usuario usuario = clientRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> pageUser = transactionRepo.findUserById(usuario.getId(), pageable);
        List<TransactionDto> transactionDtos = pageUser.getContent().stream().map(TransactionDto::fromEntity).toList();
        Map<String, Object> mapper = Map.of("totalPages", pageUser.getTotalPages(), "totalTransactions", pageUser.getTotalElements(), "pageNumber", pageUser.getNumber());
        return new TransactionPageByUser(transactionDtos, mapper);
    }

    private int transactionType(TransactionType type) {
        return TransactionType.TRANSFERENCIA == type ? -1 : 1;
    }
}
