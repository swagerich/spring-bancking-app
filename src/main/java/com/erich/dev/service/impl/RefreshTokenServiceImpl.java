package com.erich.dev.service.impl;

import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.RefreshTokenRequest;
import com.erich.dev.entity.RefreshToken;
import com.erich.dev.entity.Usuario;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.repository.ClientRepository;
import com.erich.dev.repository.RefreshTokenRepository;
import com.erich.dev.security.CustomUserServiceImpl;
import com.erich.dev.security.jwt.JwtTokenProvider;
import com.erich.dev.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final ClientRepository clientRepo;

    private final RefreshTokenRepository refreshTokenRepo;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserServiceImpl customUserService;

    @Value("${app.jwt.refreshTokenDurationMs}")
    private Long expireToken;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(String username) {
        Usuario usuario = clientRepo.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("Usuario not found"));
        RefreshToken refresh = RefreshToken.builder()
                .usuario(usuario)
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(expireToken))
                .build();
        return refreshTokenRepo.save(refresh);
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        return this.findByToken(request.token())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUsuario)
                .map(user -> {
                    Map<String, Object> claims = this.showDetailClaims(user);
                    String accessToken = jwtTokenProvider.generateToken(customUserService.loadUserByUsername(user.getUsername()), claims);
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(request.token())
                            .tokenType("Bearer ")
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    private Map<String, Object> showDetailClaims(Usuario usuario) {
        return Map.of("userId", usuario.getId(),
                "fullName", usuario.getFirstName() + " " + usuario.getLastName(),
                "authorities", usuario.getAuthorities());
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " The update token has expired. Make a new login request");
        }
        return token;
    }
}
