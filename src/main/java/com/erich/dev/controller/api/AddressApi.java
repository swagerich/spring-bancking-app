package com.erich.dev.controller.api;

import com.erich.dev.dto.AddressDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.dev.util.BankPath.Path;

public interface AddressApi {

    @PostMapping(value = Path + "/address", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el Address", description = "Crea  el Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto Address crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto Address no es valido")
    })
    ResponseEntity<AddressDto> saveAddress(@RequestBody AddressDto client);

    @GetMapping(value = Path + "/address")
    ResponseEntity<List<AddressDto>> addressAll();

    @GetMapping(value = Path + "/address/{id}")
    ResponseEntity<AddressDto> findId(@PathVariable Long id);


    @PutMapping(value = Path + "/address/{id}")
    ResponseEntity<AddressDto> update(@RequestBody AddressDto dto, @PathVariable Long id);

    @DeleteMapping(value = Path + "/address/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

}
