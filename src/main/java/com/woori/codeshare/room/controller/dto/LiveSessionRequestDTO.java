package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LiveSessionRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LiveSessionRequest {
        private String code;
    }
}
