package com.woori.codeshare.snapshot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SnapshotResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SnapshotCreateResponse {

        private Long snapshotId;
        private Long roomId;
        private String title;
        private String description;
        private String code;
        private LocalDateTime createdAt;
    }
}
