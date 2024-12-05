package com.delivery.api.domain.token.helper;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.TokenErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.Interface.TokenHelperInterface;
import com.delivery.api.domain.token.model.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperInterface {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiresAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .claims(data)
                .expiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiresAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parser()
                .verifyWith(key)
                .build();

        try {
            var result = parser.parseSignedClaims(token);
            return new HashMap<String, Object>(result.getPayload());
        } catch (Exception e){
            if(e instanceof SignatureException){
                // 토큰이 유효하지 않을 때
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            } else if (e instanceof ExpiredJwtException) {
                // 만료된 토큰일 때
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            } else{
                // 그 외 에러
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}