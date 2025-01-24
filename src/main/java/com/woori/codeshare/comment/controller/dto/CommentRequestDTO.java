package com.woori.codeshare.comment.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentCreateRequest {
        @NotNull(message = "스냅샷 ID는 필수입니다.")
        private Long snapshotId;

        @NotBlank(message = "댓글 내용은 필수입니다.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResolveRequest {
        @JsonProperty("isChecked")
        private boolean isChecked;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentUpdateRequest {
        private String content;
    }
}
