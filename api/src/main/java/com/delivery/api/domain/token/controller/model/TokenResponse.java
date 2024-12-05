package com.delivery.api.domain.token.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String accessToken;
    private LocalDateTime accessTokenExpiresAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiresAt;
}