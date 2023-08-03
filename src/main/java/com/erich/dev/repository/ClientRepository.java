package com.erich.dev.repository;

import com.erich.dev.entity.Role;
import com.erich.dev.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Usuario,Long> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);
    Optional<Usuario> findByUserName(String username);

    List<Usuario> findByRolesContaining(Role role);
}
