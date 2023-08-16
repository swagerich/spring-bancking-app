package com.erich.dev.entity;

import com.erich.dev.util.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "transactions")
public class Transaction extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private String destinationBank;

    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "fk_transaction_user"))
    private Usuario user;

}
