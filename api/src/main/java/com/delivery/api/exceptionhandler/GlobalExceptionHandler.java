package com.delivery.api.exceptionhandler;

import com.delivery.api.common.api.Api;
import com.delivery.api.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE) // 우선순위 지정하는 어노테이션 >> value가 낮을 수록 높은 우선순위
// MAX >> 가장 마지막에 실행 적용 -> 커스텀예외처리를 우선처리하도록
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) // 일어나는 모든 예외 처리
    public ResponseEntity<Api<Object>> exception(Exception e){
        log.error("", e);

        return ResponseEntity
                .status(500)
                .body(Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}
