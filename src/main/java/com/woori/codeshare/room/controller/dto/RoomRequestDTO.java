package com.woori.codeshare.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class RoomRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomCreateRequest {
        private String title;
        private String password;
    }
}
