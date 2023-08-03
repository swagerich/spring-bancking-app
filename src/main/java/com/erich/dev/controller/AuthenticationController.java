package com.erich.dev.controller;

import com.erich.dev.controller.api.AuthenticationApi;
import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.LoginRequest;
import com.erich.dev.dto.proyection.SignupRequest;
import com.erich.dev.entity.Usuario;
import com.erich.dev.security.CustomUserServiceImpl;
import com.erich.dev.service.impl.ClientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements AuthenticationApi {

    private final ClientServiceImpl clientService;

    private final CustomUserServiceImpl customUserService;

    public AuthenticationController(ClientServiceImpl clientService, CustomUserServiceImpl customUserService) {
        this.clientService = clientService;
        this.customUserService = customUserService;
    }

    @Override
    public ResponseEntity<?> register(SignupRequest customDto) {
        return new ResponseEntity<>(clientService.register(customDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception {
        JwtResponse login = clientService.login(loginRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Usuario> currentUser(Principal principal) {
        UserDetails userDetails = customUserService.loadUserByUsername(principal.getName());
        Usuario usuario = (Usuario) userDetails;
        if(usuario.isEnabled()){
            return new ResponseEntity<>(usuario,HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
