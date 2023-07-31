package com.erich.dev.controller.api;

import com.erich.dev.dto.TransactionDto;
import com.erich.dev.dto.proyection.TransactionPageByUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.dev.util.BankPath.Path;

public interface TransactionApi {

    @PostMapping(value = Path + "/transaction", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el transaction", description = "Crea  el transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto transaction crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto transaction no es valido")
    })
    ResponseEntity<TransactionDto> saveTransaction(@Valid @RequestBody TransactionDto dto);

    @GetMapping(value = Path + "/transaction")
    ResponseEntity<List<TransactionDto>> transactionAll();

    @GetMapping(value = Path + "/transaction/{id}")
    ResponseEntity<TransactionDto> findId(@PathVariable Long id);

    @DeleteMapping(value = Path + "/transaction/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping(value = Path + "/transaction/all/{userId}")
    ResponseEntity<List<TransactionDto>> findAllTransactionByUserId(@PathVariable Long userId);

    @GetMapping(value = Path + "/transaction/page")
    ResponseEntity<TransactionPageByUser> findAllTransactionPageByUserId(@RequestParam(required = false) Long userId,
                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                         @RequestParam(defaultValue = "5") Integer size);
}
