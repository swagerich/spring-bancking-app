package com.erich.dev.controller;

import com.erich.dev.controller.api.AddressApi;
import com.erich.dev.dto.AddressDto;
import com.erich.dev.service.impl.AddressServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController implements AddressApi {

    private final AddressServiceImpl addressService;

    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @Override
    public ResponseEntity<AddressDto> saveAddress(AddressDto addressDto) {
        return new ResponseEntity<>(addressService.save(addressDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AddressDto>> addressAll() {
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AddressDto> findId(Long id) {
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AddressDto> update(AddressDto dto, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
