package com.erich.dev.controller.api;

import com.erich.dev.dto.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.dev.util.BankPath.Path;

public interface AccountApi {

    @PostMapping(value = Path + "/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el Account", description = "Crea  el Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto Account crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto Account no es valido")
    })
    ResponseEntity<AccountDto> saveAccount(@Valid @RequestBody AccountDto client);

    @GetMapping(value = Path + "/account")
    ResponseEntity<List<AccountDto>> accountAll();

    @GetMapping(value = Path + "/account/{id}")
    ResponseEntity<AccountDto> findId(@PathVariable Long id);

    @DeleteMapping(value = Path + "/account/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

}
