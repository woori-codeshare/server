package com.woori.codeshare.socket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RoomSocketDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomJoinRequest {
        private Long roomId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomLeaveRequest {
        private Long roomId;
        private String nickname;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomUserListResponse {
        private Long roomId;
        private String nickname;
        private List<String> users;
        private int userCount;
        private String eventType; // JOIN or LEAVE
    }
}

