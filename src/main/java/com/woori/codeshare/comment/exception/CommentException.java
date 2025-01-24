package com.woori.codeshare.comment.exception;

import com.woori.codeshare.global.exception.CustomException;

public class CommentException extends CustomException {

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode);
    }
}
