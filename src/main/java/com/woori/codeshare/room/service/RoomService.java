package com.woori.codeshare.room.service;


import com.woori.codeshare.room.controller.dto.RoomRequestDTO;
import com.woori.codeshare.room.controller.dto.RoomResponseDTO;
import com.woori.codeshare.room.domain.Room;
import com.woori.codeshare.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 방 생성 로직
     *
     * @param request Room 생성 요청 DTO
     * @return Room 생성 응답 DTO
     */
    public RoomResponseDTO.RoomCreateResponse createRoom(RoomRequestDTO.RoomCreateRequest request) {
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // 고유 식별자 생성
        String uuid = UUID.randomUUID().toString();

        // Room 엔티티 생성 및 저장
        Room room = new Room();
        room.setUuid(uuid);
        room.setTitle(request.getTitle());
        room.setPassword(encryptedPassword);
        Room savedRoom = roomRepository.save(room);

        // RoomCreateResponse 객체 생성
        return RoomResponseDTO.RoomCreateResponse.builder()
                .roomId(savedRoom.getRoomId())
                .uuid(savedRoom.getUuid())
                .title(savedRoom.getTitle())
                .build();
    }
}
