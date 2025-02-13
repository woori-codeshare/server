package com.woori.codeshare.snapshot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCreateRequest {
        private String title;
    }
}
