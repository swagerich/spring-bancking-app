package com.erich.dev.service.impl;

import com.erich.dev.dto.AddressDto;
import com.erich.dev.entity.Address;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.exception.OperationNotAllowedException;
import com.erich.dev.repository.AddressRepository;
import com.erich.dev.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepo;

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        Address address = AddressDto.toEntity(addressDto);
        return AddressDto.fromEntity(addressRepo.save(address));
    }

    @Override
    public AddressDto update(AddressDto addressDto, Long id) {
        if(!addressRepo.existsById(id)){
            throw new EntityNotFoundException("Addres" + id + "No encontrado");
        }
      return  addressRepo.findById(id).map(a -> {
           a.setStreet(addressDto.getStreet());
           a.setDirection(addressDto.getDirection());
           a.setCodePostal(addressDto.getCodePostal());
           a.setCity(addressDto.getCity());
           return AddressDto.fromEntity(addressRepo.save(a));
        }).orElseThrow(() -> new OperationNotAllowedException("No se pudo actualizar!"));
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto findByUserId(Long userId) {
        return addressRepo.findByUserId(userId).map(AddressDto::fromEntity).orElseThrow((()  -> new EntityNotFoundException("User ID NOT FOUND")));
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto findById(Long id) {
        return addressRepo.findById(id).map(AddressDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("Addres Id NO FOUND"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> findAll() {
        return Streamable.of(addressRepo.findAll()).stream()
                .map(AddressDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        if(aLong == null){
            return;
        }
        addressRepo.deleteById(aLong);
    }
}
