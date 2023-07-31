package com.erich.dev.controller.api;


import com.erich.dev.dto.UsuarioDto;
import com.erich.dev.dto.proyection.LoginRequest;
import com.erich.dev.dto.proyection.SignupRequest;
import com.erich.dev.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

public interface AuthenticationApi {

    @PostMapping(value = {"/register","/signup"})
    ResponseEntity<?> register(@RequestBody SignupRequest customDto);

    @PostMapping(value = {"/login","/signin"})
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;

    @GetMapping(value = "/current-user")
    Usuario currentUser(Principal principal);
}
