package com.woori.codeshare.vote.controller.dto;

import com.woori.codeshare.vote.domain.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

public class VoteResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCreateResponse {
        private Long voteId;
        private Long snapshotId;
        private String title;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCastResponse {
        private Long voteId;
        private VoteType voteType;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteTitleUpdateResponse {
        private Long voteId;
        private String title;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteResultResponse {
        private Long voteId;
        private Map<String, Integer> voteCounts;
    }
}
