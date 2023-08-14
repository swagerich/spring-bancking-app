package com.erich.dev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "refreshToken")
public class RefreshToken extends AbstractEntity {

    @Column(nullable = false,unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expireDate;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_usuario"))
    public Usuario usuario;
}
