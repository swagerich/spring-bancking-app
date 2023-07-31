package com.erich.dev.service;

import java.util.List;

public interface CrudBankService<T, ID> {

    T save(T t);

    T update(T t, ID id);

    T findById(ID id);

    List<T> findAll();

    void deleteById(ID id);
}
