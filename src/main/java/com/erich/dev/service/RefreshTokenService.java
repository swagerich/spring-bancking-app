package com.erich.dev.service;

import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.RefreshTokenRequest;
import com.erich.dev.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    JwtResponse refreshToken(RefreshTokenRequest request);
}
