package com.erich.dev.controller.api;

import com.erich.dev.dto.ContactDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.dev.util.BankPath.Path;

public interface ContactApi {

    @PostMapping(value = Path + "/contact", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el contact", description = "Crea  el contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto contact crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto contact no es valido")
    })
    ResponseEntity<ContactDto> saveContact(@Valid  @RequestBody ContactDto client);

    @GetMapping(value = Path + "/contact")
    ResponseEntity<List<ContactDto>> accountAll();

    @GetMapping(value = Path + "/contact/{id}")
    ResponseEntity<ContactDto> findId(@PathVariable Long id);

    @DeleteMapping(value = Path + "/contact/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping(value = Path + "/contact/all/{userId}")
    ResponseEntity<List<ContactDto>> findAllContactByUserId(@PathVariable Long userId);
}
