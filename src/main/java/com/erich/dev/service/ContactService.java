package com.erich.dev.service;

import com.erich.dev.dto.ContactDto;

import java.util.List;

public interface ContactService extends CrudBankService<ContactDto,Long> {

    List<ContactDto> findAllContactByUserId(Long userId);
}
