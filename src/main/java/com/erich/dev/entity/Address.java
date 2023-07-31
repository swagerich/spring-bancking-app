package com.erich.dev.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public  class Address extends  AbstractEntity{

    private String street;

    private String direction;

    private Integer codePostal;

    private String city;

    private String country;

    @OneToOne
    @JoinColumn(name = "id_user",foreignKey = @ForeignKey(name = "fk_address_user"))
    private Usuario user;
}
