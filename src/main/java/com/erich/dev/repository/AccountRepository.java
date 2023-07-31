package com.erich.dev.repository;

import com.erich.dev.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account,Long> {

    boolean existsByNumber(String number);

    Optional<Account> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM Account a where a.user.id =?1")
    void deleteAccountByUser(Long id);
}
