package com.erich.dev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact extends AbstractEntity {

    private String firstName;

    private String lastName;

    private String email;

    private String number;

    @ManyToOne
    @JoinColumn(name = "id_user",foreignKey = @ForeignKey(name = "fk_contact_user"))
    private Usuario user;
}
