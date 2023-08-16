package com.erich.dev.repository;

import com.erich.dev.entity.RefreshToken;
import com.erich.dev.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
    RefreshToken findByUsuario(Usuario usuario);
}
