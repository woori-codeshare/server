package com.woori.codeshare.room.service;


import com.woori.codeshare.room.controller.dto.CurrentSessionRequestDTO;
import com.woori.codeshare.room.controller.dto.CurrentSessionResponseDTO;
import com.woori.codeshare.room.controller.dto.RoomRequestDTO;
import com.woori.codeshare.room.controller.dto.RoomResponseDTO;
import com.woori.codeshare.room.domain.CurrentSession;
import com.woori.codeshare.room.domain.Room;
import com.woori.codeshare.room.exception.RoomErrorCode;
import com.woori.codeshare.room.exception.RoomException;
import com.woori.codeshare.room.repository.CurrentSessionRepository;
import com.woori.codeshare.room.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CurrentSessionRepository currentSessionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 방 생성 로직 (UUID 기반 URL 생성)
     *
     * @param request Room 생성 요청 DTO
     * @return Room 생성 응답 DTO
     */
    public RoomResponseDTO.RoomCreateResponse createRoom(RoomRequestDTO.RoomCreateRequest request) {
        boolean exists = roomRepository.checkDuplicateTitle(request.getTitle());
        if (exists) {
            throw new RoomException(RoomErrorCode.DUPLICATE_ROOM_TITLE);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        String uuid = UUID.randomUUID().toString();

        // Room 엔티티 생성 및 저장
        Room room = new Room();
        room.setUuid(uuid);
        room.setTitle(request.getTitle());
        room.setPassword(encryptedPassword);
        Room savedRoom = roomRepository.save(room);

        // 공유 URL 생성
        String sharedUrl = "https://codeshare.woori.com/rooms/enter/" + uuid;

        return RoomResponseDTO.RoomCreateResponse.builder()
                .roomId(savedRoom.getRoomId())
                .uuid(savedRoom.getUuid())
                .title(savedRoom.getTitle())
                .createdAt(savedRoom.getCreatedAt())
                .sharedUrl(sharedUrl)
                .build();
    }

    /**
     * 방 입장 로직 (UUID 기반)
     *
     * @param uuid        방 UUID
     * @param rawPassword 사용자 입력 비밀번호
     * @return 방 입장 응답 DTO
     */
    public RoomResponseDTO.RoomEnterResponse enterRoomByUuid(String uuid, String rawPassword) {
        Room room = roomRepository.findByUuid(uuid)
                .orElseThrow(() -> new RoomException(RoomErrorCode.ROOM_NOT_FOUND));

        if (!passwordEncoder.matches(rawPassword, room.getPassword())) {
            throw new RoomException(RoomErrorCode.INVALID_PASSWORD);
        }

        return RoomResponseDTO.RoomEnterResponse.builder()
                .roomId(room.getRoomId())
                .uuid(room.getUuid())
                .title(room.getTitle())
                .createdAt(room.getCreatedAt())
                .build();
    }

    /**
     * 현재 코드 세션 저장
     */
    @Transactional
    public CurrentSessionResponseDTO.CurrentSessionResponse saveCurrentSession(Long roomId, CurrentSessionRequestDTO.CurrentSessionRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.ROOM_NOT_FOUND));

        // 기존 CurrentSession이 존재하면 업데이트, 없으면 새로 생성
        CurrentSession currentSession = currentSessionRepository.findByRoom_RoomId(roomId)
                .orElse(new CurrentSession(room));

        currentSession.setCode(request.getCode());
        currentSessionRepository.save(currentSession);

        return CurrentSessionResponseDTO.CurrentSessionResponse.builder()
                .roomId(roomId)
                .code(currentSession.getCode())
                .updatedAt(currentSession.getUpdatedAt())
                .build();
    }

}

