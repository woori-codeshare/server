package com.woori.codeshare.vote.exception;

import com.woori.codeshare.global.exception.BaseErrorCode;
import com.woori.codeshare.global.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VoteErrorCode implements BaseErrorCode {
    VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "VOTE404", "존재하지 않는 투표입니다."),
    VOTE_ALREADY_CASTED(HttpStatus.BAD_REQUEST, "VOTE404", "이미 투표한 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
