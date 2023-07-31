package com.erich.dev.handlers;

import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.exception.ObjectValidationException;
import com.erich.dev.exception.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.*;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFoundException(EntityNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setTitle("NOT FOUND");
        detail.setDetail(e.getMessage());
        detail.setProperty("HORA: ", LocalDate.now());
        return detail;
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ProblemDetail handleOperationException(OperationNotAllowedException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        detail.setTitle("Operacion no permitida!");
        detail.setDetail(e.getMessage());
        detail.setProperty("HORA: ", LocalDate.now());
        return detail;
    }

    /**
     * Validation optionals
     * @param e
     * @return
     */
    @ExceptionHandler(ObjectValidationException.class)
    public ProblemDetail handleObjectValid(ObjectValidationException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("BAD-REQUEST");
        detail.setDetail(e.getViolationSource());
        detail.setProperty("Hora:", LocalDate.now());
        detail.setProperty("Violations : ", Set.of(e.getViolations()));
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodValid(MethodArgumentNotValidException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> maps = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> {
            String field = f.getField();
            String message = f.getDefaultMessage();
            maps.put("El campo " + field, " " + message);
        });
        detail.setTitle("BAD-REQUEST");
        detail.setDetail("");
        detail.setProperty("Violations", maps);
        return detail;
    }

    @ExceptionHandler(DisabledException.class)
    public ProblemDetail handleDisableException(DisabledException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("BAD-REQUEST");
        detail.setDetail(e.getMessage());
        detail.setProperty("Hora:", LocalDate.now());
        return detail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        detail.setTitle("FORBIDDEN");
        detail.setDetail(e.getMessage());
        detail.setProperty("Hora:", LocalDate.now());
        return detail;
    }


}
