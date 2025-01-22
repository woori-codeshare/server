package com.woori.codeshare.room.controller.dto;

import lombok.*;

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
    }
}
