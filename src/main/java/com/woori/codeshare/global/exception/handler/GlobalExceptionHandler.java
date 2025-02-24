package com.woori.codeshare.global.exception.handler;

import com.woori.codeshare.global.exception.BaseErrorCode;
import com.woori.codeshare.global.exception.CommonErrorCode;
import com.woori.codeshare.global.exception.CustomException;
import com.woori.codeshare.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. CustomException 처리
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.warn(">>>>> Custom Exception: ", e);
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(errorCode.getErrorResponse());
    }

    // 2. Validation Exception 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(">>>>> Validation Failed: ", e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ErrorResponse errorResponse = ErrorResponse.of("400", "입력값에 대한 검증에 실패했습니다.");
        fieldErrors.forEach(error -> errorResponse.addValidation(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    // 3. JSON 파싱 오류 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParsingError(HttpMessageNotReadableException e) {
        log.warn(">>>>> JSON Parsing Error: ", e);
        return ResponseEntity.badRequest()
                .body(CommonErrorCode.INVALID_JSON_FORMAT.getErrorResponse());
    }

    // 3. 기타 Global Exception 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        log.error(">>>>> Internal Server Error: ", e);
        ErrorResponse errorResponse = ErrorResponse.of("500", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
