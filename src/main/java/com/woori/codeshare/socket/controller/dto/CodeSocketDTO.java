package com.woori.codeshare.socket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CodeSocketDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeUpdateRequest {
        private Long roomId;
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeUpdateResponse {
        private Long roomId;
        private String code;
        private String eventType; // UPDATE
    }
}
