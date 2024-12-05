package com.delivery.api.domain.token.Interface;

import com.delivery.api.domain.token.model.TokenDto;

import java.util.Map;

public interface TokenHelperInterface {

    TokenDto issueAccessToken(Map<String, Object> data);

    TokenDto issueRefreshToken(Map<String, Object> data);

    Map<String, Object> validationTokenWithThrow(String token);
}