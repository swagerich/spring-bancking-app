package com.erich.dev.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {

//    private String number;

//    @Enumerated(EnumType.STRING)
//    private Currency currency;

//    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "id_user",foreignKey = @ForeignKey(name = "fk_account_user"))
    private Usuario user;

    private String number;

}
