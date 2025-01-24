package com.woori.codeshare.comment.controller.dto;

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
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResolveResponse {
        private Long commentId;
        private boolean isChecked;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentUpdateResponse {
        private Long commentId;
        private String content;
        private LocalDateTime updatedAt;
    }
}
