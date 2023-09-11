package com.erich.dev.service;

import com.erich.dev.dto.AddressDto;

public interface AddressService extends CrudBankService<AddressDto,Long> {

    AddressDto findByUserId(Long userId);

    boolean existByUserId(Long userId);
}
