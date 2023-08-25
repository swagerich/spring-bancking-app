package com.erich.dev.controller.api;

import com.erich.dev.dto.UsuarioDto;

import static com.erich.dev.util.BankPath.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface ClientApi {

    @PostMapping(value = Path + "/client", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el cliente", description = "Crea  el cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto usuario crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto usuario no es valido")
    })
    ResponseEntity<UsuarioDto> saveClient(@Valid @RequestBody UsuarioDto client);

    @PutMapping(value = Path + "/client/{id}")
    ResponseEntity<UsuarioDto> updateClient(@Valid @RequestBody UsuarioDto client, @PathVariable Long id);

    @GetMapping(value = Path + "/client")
    ResponseEntity<List<UsuarioDto>> clientAll();

    @GetMapping(value = Path + "/client/{id}")
    ResponseEntity<UsuarioDto> findId(@PathVariable Long id);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = Path + "/client/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = Path + "/client/validate/{userId}")
    ResponseEntity<Long> validateAccount(@PathVariable Long userId);

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = Path + "/client/invalidate/{userId}")
    ResponseEntity<Long> invalidateAccount(@PathVariable Long userId);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = Path + "/client/users")
    ResponseEntity<List<UsuarioDto>> getAllRoleUser();

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = Path + "/client/users/active")
    ResponseEntity<Integer> countRoleUserIsActive();

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = Path + "/client/users/inactive")
    ResponseEntity<Integer> countRoleUserIsInactive();

}
