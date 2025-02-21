package com.woori.codeshare.socket.service;

import com.woori.codeshare.socket.controller.dto.CodeSocketDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    // 방별 코드 상태 (roomId -> 현재 코드)
    private final Map<Long, String> roomCodeState = new ConcurrentHashMap<>();

    /**
     * 코드 변경 로직
     */
    @Transactional
    public void updateCode(CodeSocketDTO.CodeUpdateRequest request) {
        Long roomId = request.getRoomId();
        String newCode = request.getCode();

        // 코드 상태 업데이트
        roomCodeState.put(roomId, newCode);

        log.info("[WebSocket] 코드 변경: roomId={}, code={}", roomId, newCode);

        // 변경된 코드 내용을 모든 사용자에게 브로드캐스팅
        sendUpdatedCode(roomId, newCode);
    }

    /**
     * 변경된 코드 내용 브로드캐스트
     */
    private void sendUpdatedCode(Long roomId, String code) {
        CodeSocketDTO.CodeUpdateResponse response =
                CodeSocketDTO.CodeUpdateResponse.builder()
                        .roomId(roomId)
                        .code(code)
                        .eventType("UPDATE")
                        .build();

        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/code", response);
    }
}
