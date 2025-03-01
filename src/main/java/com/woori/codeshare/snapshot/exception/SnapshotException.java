package com.woori.codeshare.snapshot.exception;

import com.woori.codeshare.global.exception.CustomException;

public class SnapshotException extends CustomException {

    public SnapshotException(SnapshotErrorCode errorCode) {
        super(errorCode);
    }
}
