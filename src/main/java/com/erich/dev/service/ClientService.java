package com.erich.dev.service;

import com.erich.dev.dto.UsuarioDto;
import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.LoginRequest;
import com.erich.dev.dto.proyection.SignupRequest;
import com.erich.dev.dto.proyection.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClientService extends CrudBankService<UsuarioDto, Long> {

    Integer countRoleUserIsActive();

    Integer countRoleUserInactive();

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse register(SignupRequest signupRequest);

    UploadResponse savePhotoByUserId(Long userId, MultipartFile file) throws IOException;

    byte[] getPhotoByUserId(Long userId, String fileName) throws IOException;
}
