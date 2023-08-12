package com.erich.dev.repository;

import com.erich.dev.entity.Role;
import com.erich.dev.entity.Usuario;
import com.erich.dev.repository.custom.ClientRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Usuario,Long>, ClientRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);
    Optional<Usuario> findByUserName(String username);
    List<Usuario> findByActiveAndRolesContaining(boolean active, Role role);
    List<Usuario> findByRolesContaining(Role role);

//    @Query("SELECT u.usuariosDate as usuariosDate, COUNT(u) as total  FROM Usuario u WHERE u.creationDate BETWEEN :start AND :end GROUP BY u.usuariosDate")
//    List<UsuariosDetails> countUsersByDate(LocalDateTime start , LocalDateTime end);
}
