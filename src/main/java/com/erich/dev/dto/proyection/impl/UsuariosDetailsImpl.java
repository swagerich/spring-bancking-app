package com.erich.dev.dto.proyection.impl;

import com.erich.dev.dto.proyection.UsuariosDetails;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Implementamos para que JPA CRITERIA SCANNEE LA CLASE  (porque NO scanea una interfaz)
 */
@Getter
@Setter
public class UsuariosDetailsImpl implements UsuariosDetails {
    private LocalDate usuariosDate;
    private Long total;

    @Override
    public LocalDate getUsuariosDate() {
        return usuariosDate;
    }

    @Override
    public Long getTotal() {
        return total;
    }
}
