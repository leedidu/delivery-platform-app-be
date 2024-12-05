package com.delivery.api.common.exception;

import com.delivery.api.common.error.ErrorCodeInterface;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface{

    private final ErrorCodeInterface errorCodeInterface;

    private final String errorDescription;

    public ApiException(ErrorCodeInterface errorCodeInterface) {
        super(errorCodeInterface.getDescription());
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription) {
        super(errorDescription);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx) {
        super(tx);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx, String errorDescription) {
        super(tx);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }
}