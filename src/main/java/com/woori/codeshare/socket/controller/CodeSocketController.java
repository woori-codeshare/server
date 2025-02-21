package com.woori.codeshare.socket.controller;

import com.woori.codeshare.socket.controller.dto.CodeSocketDTO;
import com.woori.codeshare.socket.service.CodeSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeSocketController {

    private final CodeSocketService codeSocketService;

    /**
     * 코드 변경 WebSocket 이벤트
     */
    @MessageMapping("/update.code")
    public void updateCode(@Payload CodeSocketDTO.CodeUpdateRequest request) {
        log.info("[WebSocket] 코드 변경 요청: roomId={}", request.getRoomId());
        codeSocketService.updateCode(request);
    }
}

