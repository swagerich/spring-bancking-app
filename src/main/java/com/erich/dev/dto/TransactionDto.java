package com.erich.dev.dto;

import com.erich.dev.entity.Transaction;
import com.erich.dev.entity.Usuario;
import com.erich.dev.util.TransactionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    private Long id;

    private TransactionType type;

    @Positive
    @Max(value = 10000000)
    @Min(value = 20)
    private BigDecimal amount;

    private String destinationBank;

    private LocalDate transactionDate;

    private Long userId;

    public static TransactionDto fromEntity(Transaction transaction){
        if(transaction == null){
            return null;
        }
        return TransactionDto.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .destinationBank(transaction.getDestinationBank())
                .transactionDate(transaction.getTransactionDate())
                .userId(transaction.getUser().getId())
                .build();
    }

    public static Transaction toEntity(TransactionDto transactionDto){
        if(transactionDto == null){
            return null;
        }
        return Transaction.builder()
                .id(transactionDto.getId())
                .type(transactionDto.getType())
                .amount(transactionDto.getAmount())
                .destinationBank(transactionDto.getDestinationBank())
                .transactionDate(LocalDate.now())
                .user(Usuario.builder()
                        .id(transactionDto.getUserId())
                        .build())
                .build();
    }
}
