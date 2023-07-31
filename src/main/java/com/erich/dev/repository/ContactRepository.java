package com.erich.dev.repository;

import com.erich.dev.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.user.id=?1")
    List<Contact> findAllContactUserById(Long userId);

    Page<Contact> findByFirstNameOrLastName(String first,String last,Pageable pageable);
}
