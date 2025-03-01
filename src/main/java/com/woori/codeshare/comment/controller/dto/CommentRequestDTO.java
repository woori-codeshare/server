package com.woori.codeshare.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentCreateRequest {
        @NotBlank(message = "댓글 내용은 필수입니다.")
        private String content;

        private Long parentCommentId; // 대댓글일 때만 전달
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResolveRequest {
        private boolean solved;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentUpdateRequest {
        private String content;
    }
}
