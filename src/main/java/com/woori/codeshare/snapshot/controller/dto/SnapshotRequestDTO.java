package com.woori.codeshare.snapshot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SnapshotRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SnapshotCreateRequest {
        private String title;
        private String description;
        private String code;
    }
}
