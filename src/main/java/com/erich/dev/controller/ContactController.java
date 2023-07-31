package com.erich.dev.controller;

import com.erich.dev.controller.api.ContactApi;
import com.erich.dev.dto.ContactDto;
import com.erich.dev.service.impl.ContactServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController implements ContactApi {

    private final ContactServiceImpl contactService;

    public ContactController(ContactServiceImpl contactService) {
        this.contactService = contactService;
    }

    @Override
    public ResponseEntity<ContactDto> saveContact(ContactDto contactDto) {
        return new ResponseEntity<>(contactService.save(contactDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ContactDto>> accountAll() {
        return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContactDto> findId(Long id) {
        return new ResponseEntity<>(contactService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        contactService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<ContactDto>> findAllContactByUserId(Long userId) {
        return new ResponseEntity<>(contactService.findAllContactByUserId(userId), HttpStatus.OK);
    }
}
