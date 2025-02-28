package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CurrentSessionRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentSessionRequest {
        private String code;
    }
}
