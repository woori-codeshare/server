package com.woori.codeshare.snapshot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class SnapshotResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SnapshotCreateResponse {

        private Long roomId;
        private Long snapshotId;
        private Long voteId;
        private String title;
        private String description;
        private String code;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SnapshotDetailResponse {
        private Long snapshotId;
        private String title;
        private String description;
        private String code;
        private LocalDateTime createdAt;
        private List<CommentDetailResponse> comments;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentDetailResponse {
        private Long commentId;
        private Long parentCommentId;
        private String content;
        private boolean solved;
        private LocalDateTime createdAt;
    }
}
