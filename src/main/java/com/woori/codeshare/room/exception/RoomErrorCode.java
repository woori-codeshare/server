package com.woori.codeshare.room.exception;

import com.woori.codeshare.global.exception.BaseErrorCode;
import com.woori.codeshare.global.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomErrorCode implements BaseErrorCode {

    DUPLICATE_ROOM_TITLE(HttpStatus.BAD_REQUEST, "ROOM400", "이미 동일한 이름의 방이 존재합니다."),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "ROOM403", "방 비밀번호가 잘못 되었습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "ROOM404", "존재하지 않는 방입니다.");

    private final HttpStatus status;  // HTTP 상태 코드
    private final String code;        // 에러 코드
    private final String message;     // 에러 메시지

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
