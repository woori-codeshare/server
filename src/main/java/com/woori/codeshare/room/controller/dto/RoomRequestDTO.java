package com.woori.codeshare.room.controller.dto;

import lombok.Data;

public class RoomRequestDTO {

    @Data
    public static class RoomCreateRequest {
        private String title;
        private String password;
    }
}
