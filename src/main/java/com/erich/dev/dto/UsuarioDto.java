package com.erich.dev.dto;

import com.erich.dev.entity.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String userName;

    @NotBlank
    // @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;

    @NotBlank
    @Size(min = 3, max = 16)
    private String password;

    @NotBlank
    private Integer age;

    private String numberAccount;

    private boolean active;

    private LocalDate usuariosDate;

    private String namePhoto;

    public static UsuarioDto fromEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioDto.builder()
                .id(usuario.getId())
                .firstName(usuario.getFirstName())
                .lastName(usuario.getLastName())
                .userName(usuario.getUsername())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .age(usuario.getAge())
                .numberAccount(usuario.getAccount() != null ? usuario.getAccount().getNumber() : "")
                .active(usuario.isActive())
                .usuariosDate(usuario.getUsuariosDate())
                .namePhoto(usuario.getNamePhoto())
                .build();
    }

    public static Usuario toEntity(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        return Usuario.builder()
                .id(usuarioDto.getId())
                .firstName(usuarioDto.getFirstName())
                .lastName(usuarioDto.getLastName())
                .userName(usuarioDto.getUserName())
                .email(usuarioDto.getEmail())
                .password(usuarioDto.getPassword())
                .age(usuarioDto.getAge())
                .namePhoto(usuarioDto.getNamePhoto())
                .usuariosDate(LocalDate.now())
                .build();
    }

}
