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
        private Long roomId;    // 방 ID
        private String uuid;    // 고유 식별자
        private String title;   // 방 제목
        private LocalDateTime createdAt; // 방 생성 시각
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomEnterResponse {
        private Long roomId;    // 방 ID
        private String uuid;    // 방 고유 식별자
        private String title;   // 방 제목
        private LocalDateTime createdAt; // 방 생성 시각
    }
}
