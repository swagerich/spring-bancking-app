package com.erich.dev.repository.custom;

import com.erich.dev.dto.proyection.impl.UsuariosDetailsImpl;
import com.erich.dev.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepositoryCustom  {

    List<UsuariosDetailsImpl> countUsersByDateAndRole(LocalDateTime start, LocalDateTime end);

    Optional<Usuario> findByName(String name);
}
