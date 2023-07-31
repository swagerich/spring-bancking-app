package com.erich.dev.dto;

import com.erich.dev.entity.Contact;
import com.erich.dev.entity.Usuario;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String number;

    private Long userId;

    public static ContactDto fromEntity(Contact contact){
        if(contact == null){
            return null;
        }
         return ContactDto.builder()
                 .id(contact.getId())
                 .firstName(contact.getFirstName())
                 .lastName(contact.getLastName())
                 .email(contact.getEmail())
                 .number(contact.getNumber())
                 .userId(contact.getUser().getId())
                 .build();
    }

    public static Contact toEntity(ContactDto contactDto){
        if(contactDto == null){
            return null;
        }
        return Contact.builder()
                .id(contactDto.getId())
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .email(contactDto.getEmail())
                .number(contactDto.getNumber())
                .user(Usuario.builder()
                        .id(contactDto.getUserId())
                        .build())
                .build();
    }

}
