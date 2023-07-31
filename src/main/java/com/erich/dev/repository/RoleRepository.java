package com.erich.dev.repository;

import com.erich.dev.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByAuthority(String role);
}
