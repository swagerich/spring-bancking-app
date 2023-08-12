package com.erich.dev.service;

import com.erich.dev.dto.UsuarioDto;
import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.LoginRequest;
import com.erich.dev.dto.proyection.SignupRequest;

public interface ClientService extends CrudBankService<UsuarioDto,Long> {

    Integer countRoleUserIsActive();

    Integer countRoleUserInactive();

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse register(SignupRequest signupRequest);
}
