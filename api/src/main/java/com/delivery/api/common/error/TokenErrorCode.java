package com.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.lang.model.type.ErrorType;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeInterface {
    // HttpStatusCode, 서비스에러코드, 코드설명
    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰"),
    EXPIRED_TOKEN(400, 2001, "만료된 토큰"),
    TOKEN_EXCEPTION(400, 2002, "토큰 : 알 수 없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "인증 헤더 토큰 없음");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
