package com.woori.codeshare.comment.exception;

import com.woori.codeshare.global.exception.BaseErrorCode;
import com.woori.codeshare.global.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "존재하지 않는 댓글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
