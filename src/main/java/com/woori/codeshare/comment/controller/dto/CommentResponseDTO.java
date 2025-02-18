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
        private Long parentCommentId;
        private Long snapshotId;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResolveResponse {
        private Long commentId;
        private boolean solved;
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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentListResponse {
        private Long commentId;
        private Long parentCommentId;
        private String content;
        private boolean solved;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
