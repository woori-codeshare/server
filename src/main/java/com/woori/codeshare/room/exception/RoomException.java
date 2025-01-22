package com.woori.codeshare.room.exception;

import com.woori.codeshare.global.exception.CustomException;

public class RoomException extends CustomException {

    public RoomException(RoomErrorCode errorCode) {
        super(errorCode);
    }
}
