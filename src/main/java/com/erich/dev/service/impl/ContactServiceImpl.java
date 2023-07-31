package com.erich.dev.service.impl;

import com.erich.dev.dto.ContactDto;
import com.erich.dev.entity.Contact;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.repository.ContactRepository;
import com.erich.dev.service.ContactService;
import com.erich.dev.util.validation.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepo;
    private final ObjectsValidator<ContactDto> validator;

    @Override
    @Transactional
    public ContactDto save(ContactDto contactDto) {
        Contact contact = ContactDto.toEntity(contactDto);
        return ContactDto.fromEntity(contactRepo.save(contact));
    }

    @Override
    public ContactDto update(ContactDto contactDto, Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto findById(Long id) {
        return contactRepo.findById(id).map(ContactDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("id contact no encontrado!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> findAll() {
        return Streamable.of(contactRepo.findAll())
                .stream().map(ContactDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        contactRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> findAllContactByUserId(Long userId) {
        return contactRepo.findAllContactUserById(userId).stream()
                .map(ContactDto::fromEntity).toList();
    }

//    public  void searchByContactfirstNameOrFirstNameWithPage(String lastName, String firstName ,int page,int size){
//        contactRepo.findByFirstNameOrLastName(firstName,lastName)
//    }
}
