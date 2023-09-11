package com.erich.dev.controller.api;

import static com.erich.dev.util.BankPath.*;

import com.erich.dev.dto.proyection.TransactionSumDetails;
import com.erich.dev.dto.proyection.impl.UsuariosDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StatisticsApi {

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = Path + "/statistics/sum-date/{userId}")
    @Operation(summary = "Consulta transaccion entre fechas", description = "Consulta la suma del monto de la trasaccion entre fecha inicial y la ultima fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto consulta las trassanciones"),
            @ApiResponse(responseCode = "400", description = "El objeto trassanciones no es valido")
    })
    ResponseEntity<List<TransactionSumDetails>> findSumTransactionByDate(
            @PathVariable Long userId,
            @RequestParam("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("last-date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate lastDate);

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = Path + "/statistics/amount-max/{userId}")
    ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long userId);

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = Path + "/statistics/high-tranfer/{userId}")
    ResponseEntity<BigDecimal> highTranfer(@PathVariable Long userId);

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = Path + "/statistics/high-deposit/{userId}")
    ResponseEntity<BigDecimal> highDeposit(@PathVariable Long userId);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = Path + "/statistics/users")
    ResponseEntity<List<UsuariosDetailsImpl>> countUsersByDate(
            @RequestParam("start-date")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("last-date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate lastDate);
}
