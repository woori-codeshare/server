package com.woori.codeshare.socket.controller;

import com.woori.codeshare.socket.controller.dto.RoomSocketDTO;
import com.woori.codeshare.socket.service.RoomSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomSocketController {

    private final RoomSocketService roomSocketService;

    /**
     * 방 입장 WebSocket 이벤트
     */
    @MessageMapping("/join.room")
    public void joinRoom(@Payload RoomSocketDTO.RoomJoinRequest request) {
        log.info("[WebSocket] 방 입장: roomId={}", request.getRoomId());
        roomSocketService.joinRoom(request);
    }

    /**
     * 방 나가기 WebSocket 이벤트
     */
    @MessageMapping("/leave.room")
    public void leaveRoom(@Payload RoomSocketDTO.RoomLeaveRequest request) {
        log.info("[WebSocket] 방 나가기 요청: roomId={}, nickname={}", request.getRoomId(), request.getNickname());
        roomSocketService.leaveRoom(request);
    }

    @MessageMapping("/connect")
    public void handleConnect() {
        log.info("✅ STOMP CONNECT 요청 수신됨");
    }
}



