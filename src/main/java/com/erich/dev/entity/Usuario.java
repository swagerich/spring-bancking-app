package com.erich.dev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "users")
public class Usuario extends AbstractEntity implements UserDetails {


    private String firstName;

    private String lastName;

    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private Integer age;

    private boolean active;

    private String repeatPassword;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_usuario_address"))
    @JsonIgnore
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<Contact> contacts = new ArrayList<>();

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_usuario_account"))
    @JsonIgnore
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
            , foreignKey = @ForeignKey(name = "fk_usuario"), inverseForeignKey = @ForeignKey(name = "fk_role"))
    @JsonIgnore
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getAuthority())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
