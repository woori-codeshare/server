package com.woori.codeshare.socket.service;

import com.woori.codeshare.socket.controller.dto.RoomSocketDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    // 방별 익명 사용자 목록 (roomId -> 사용자 리스트)
    private final Map<Long, List<String>> roomUsers = new ConcurrentHashMap<>();

    /**
     * 방 입장 로직
     */
    @Transactional
    public void joinRoom(RoomSocketDTO.RoomJoinRequest request) {
        Long roomId = request.getRoomId();

        // 방에 존재하는 사용자 목록 가져오기
        roomUsers.putIfAbsent(roomId, new ArrayList<>());
        List<String> users = roomUsers.get(roomId);

        // 새로운 익명 사용자 추가
        String newNickname = generateUniqueNickname(users);
        users.add(newNickname);

        log.info("[WebSocket] 방 입장: roomId={}, nickname={}", roomId, newNickname);

        sendUpdatedUserList(roomId, newNickname, users, "JOIN");
    }

    /**
     * 방 나가기 로직
     */
    @Transactional
    public void leaveRoom(RoomSocketDTO.RoomLeaveRequest request) {
        Long roomId = request.getRoomId();
        String nickname = request.getNickname();

        if (!roomUsers.containsKey(roomId)) {
            log.warn("[WebSocket] 방이 존재하지 않음: roomId={}", roomId);
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/errors",
                    "방이 존재하지 않습니다: roomId=" + roomId);
            return;
        }

        List<String> users = roomUsers.get(roomId);
        log.info("[WebSocket] 기존 방 유저 목록: {}", users);

        if (users.remove(nickname)) {
            log.info("[WebSocket] 방 나감: roomId={}, nickname={}", roomId, nickname);
            log.info("[WebSocket] 나간 후 남은 유저 목록: {}", users);

            // 사용자가 아무도 없으면 방 목록에서 삭제
            if (users.isEmpty()) {
                roomUsers.remove(roomId);
                log.info("[WebSocket] 방 삭제됨: roomId={}", roomId);
            }

            sendUpdatedUserList(roomId, nickname, users, "LEAVE");
        } else {
            log.warn("[WebSocket] 방 나가기 요청했지만 해당 닉네임이 존재하지 않음: roomId={}, nickname={}", roomId, nickname);
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/errors",
                    "닉네임이 존재하지 않습니다: " + nickname);
        }
    }

    /**
     * 익명 닉네임 생성 (중복 방지)
     */
    private String generateUniqueNickname(List<String> existingUsers) {
        int index = 1;
        String nickname;

        do {
            nickname = "익명" + index;
            index++;
        } while (existingUsers.contains(nickname));

        return nickname;
    }

    /**
     * 방 사용자 목록 업데이트 및 전송
     */
    private void sendUpdatedUserList(Long roomId, String newnickname, List<String> users, String eventType) {
        RoomSocketDTO.RoomUserListResponse response =
                RoomSocketDTO.RoomUserListResponse.builder()
                        .roomId(roomId)
                        .nickname(newnickname)
                        .users(users)
                        .userCount(users.size())
                        .eventType(eventType)
                        .build();

        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/users", response);
    }
}

