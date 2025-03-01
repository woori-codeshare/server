package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LiveSessionResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LiveSessionResponse {
        private Long roomId;
        private String code;
        private LocalDateTime updatedAt;
    }
}
