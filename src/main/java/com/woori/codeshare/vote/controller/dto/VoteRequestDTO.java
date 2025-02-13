package com.woori.codeshare.vote.controller.dto;

import com.woori.codeshare.vote.domain.VoteType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCreateRequest {
        @NotBlank(message = "투표 제목은 필수입니다.")
        private String title;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCastRequest {
        @NotNull(message = "투표 타입은 필수입니다.")
        private VoteType voteType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteTitleUpdateRequest {
        @NotBlank(message = "투표 제목은 필수입니다.")
        private String title;
    }
}
