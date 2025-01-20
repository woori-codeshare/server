package com.woori.codeshare.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage()); // 예외 메시지를 부모 클래스에 전달
        this.errorCode = errorCode;
    }
}
