package com.erich.dev.dto.proyection;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
