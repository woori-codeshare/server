package com.woori.codeshare.global.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS("SUCCESS_200", "정상 처리되었습니다."),
    CONFIRM("CONFIRM_202", "검증 및 확인 되었습니다.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
