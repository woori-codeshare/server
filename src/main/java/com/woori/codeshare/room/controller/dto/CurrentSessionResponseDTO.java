package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CurrentSessionResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentSessionResponse {
        private Long roomId;
        private String code;
        private LocalDateTime updatedAt;
    }
}
