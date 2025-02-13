package com.woori.codeshare.vote.exception;

import com.woori.codeshare.global.exception.CustomException;

public class VoteException extends CustomException {

    public VoteException(VoteErrorCode errorCode) {
        super(errorCode);
    }
}
