package com.woori.codeshare.global.exception;

import com.woori.codeshare.global.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {

    // 가장 일반적인 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 테스트 관련 에러
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트"),

    // 페이징 관련 에러
    PAGE_NEGATIVE_INPUT(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 번호는 1이상의 숫자여야 합니다."),

    // JSON 관련 에러 추가
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "JSON1002", "JSON 형식이 올바르지 않습니다. \" 기호가 포함된 경우 \\\" 로 이스케이프 처리하세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
