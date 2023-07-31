package com.erich.dev.dto;

import com.erich.dev.entity.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    @NotBlank
    private String number;

    private UsuarioDto user;

    public static AccountDto fromEntity(Account account){
        if(account == null){
            return null;
        }
        return AccountDto.builder()
                .id(account.getId())
                .number(account.getNumber())
                .user(UsuarioDto.fromEntity(account.getUser()))
                .build();
    }

    public static Account toEntity(AccountDto accountDto){
        if(accountDto == null){
            return null;
        }
        return Account.builder()
                .id(accountDto.getId())
                .number(accountDto.getNumber())
                .user(UsuarioDto.toEntity(accountDto.getUser()))
                .build();
    }
}
