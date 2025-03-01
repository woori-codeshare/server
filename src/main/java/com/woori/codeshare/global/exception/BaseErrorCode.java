package com.woori.codeshare.global.exception;

import com.woori.codeshare.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    ErrorResponse getErrorResponse();

    String getMessage();

    HttpStatus getStatus();
}
