package com.woori.codeshare.snapshot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentCreateResponse {
        private Long commentId;
        private Long snapshotId;
        private String content;
        private boolean isChecked;
        private LocalDateTime createdAt;
    }
}
