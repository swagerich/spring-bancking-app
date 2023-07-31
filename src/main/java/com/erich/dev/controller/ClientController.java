package com.erich.dev.controller;

import com.erich.dev.controller.api.ClientApi;
import com.erich.dev.dto.UsuarioDto;
import com.erich.dev.entity.Usuario;
import com.erich.dev.security.CustomUserServiceImpl;
import com.erich.dev.service.impl.ClientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ClientController implements ClientApi {


    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<UsuarioDto> saveClient(UsuarioDto client) {
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UsuarioDto> updateClient(UsuarioDto client, @PathVariable Long id) {
        return new ResponseEntity<>(clientService.update(client, id), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UsuarioDto>> clientAll() {
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDto> findId(Long id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Long> validateAccount(Long userId) {
        return new ResponseEntity<>(clientService.validateAccount(userId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> invalidateAccount(Long userId) {
        return new ResponseEntity<>(clientService.invalidateAccount(userId),HttpStatus.OK);
    }


    @GetMapping("/usuarios")
    Iterable<Usuario> clientAllUsuarios(){
        return clientService.findAllUsuario();
    }

}
