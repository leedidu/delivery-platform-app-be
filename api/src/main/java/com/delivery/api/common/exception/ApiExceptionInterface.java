package com.delivery.api.common.exception;

import com.delivery.api.common.error.ErrorCodeInterface;

public interface ApiExceptionInterface {

    ErrorCodeInterface getErrorCodeInterface();

    String getErrorDescription();
}