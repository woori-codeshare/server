package com.woori.codeshare.room.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 인스턴스화 방지
public class RoomResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomCreateResponse {
        private Long roomId;
        private String uuid;
        private String title;
        private String sharedUrl;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomEnterResponse {
        private Long roomId;
        private String uuid;
        private String title;
        private LocalDateTime createdAt;
    }
}
