package com.erich.dev.repository;

import com.erich.dev.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,Long> {

    Optional<Address> findByUserId(Long userId);
}
