package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RoomResponseDTO {

    @Data
    @AllArgsConstructor
    public static class RoomCreateResponse {
        private Long roomId;
        private String uuid;
        private String title;
    }
}
