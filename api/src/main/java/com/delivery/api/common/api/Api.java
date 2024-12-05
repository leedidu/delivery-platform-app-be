package com.delivery.api.common.api;

import com.delivery.api.common.error.ErrorCodeInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    // result
    private Result result;

    // body
    @Valid
    private T body;

    public static <T> Api<T> OK(T data){
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, tx);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, String descriptions){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, descriptions);
        return api;
    }
}