package com.erich.dev.dto;

import com.erich.dev.entity.Address;
import com.erich.dev.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String direction;

    @NotBlank
    private Integer codePostal;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    private Long userId;

    public static AddressDto fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .direction(address.getDirection())
                .codePostal(address.getCodePostal())
                .city(address.getCity())
                .country(address.getCountry())
                .userId(address.getUser().getId())
                .build();
    }

    public static Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        return Address.builder()
                .id(addressDto.getId())
                .street(addressDto.getStreet())
                .direction(addressDto.getDirection())
                .codePostal(addressDto.getCodePostal())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .user(Usuario.builder().id(addressDto.getUserId()).build())
                .build();
    }
}
