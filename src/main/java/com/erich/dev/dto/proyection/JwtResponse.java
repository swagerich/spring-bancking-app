package com.erich.dev.dto.proyection;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
  //  private String refresToken;
    private String TokenType;
}
