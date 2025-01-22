package com.woori.codeshare.room.service;


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
     * @param title       방 제목
     * @param rawPassword 사용자 입력 비밀번호
     * @return 생성된 방 정보를 포함한 DTO
     */
    public RoomResponseDTO.RoomCreateResponse createRoom(String title, String rawPassword) {
        String encryptedPassword = passwordEncoder.encode(rawPassword);  // 비밀번호 암호화
        String uuid = UUID.randomUUID().toString();  // 고유 식별자 생성

        Room room = new Room();
        room.setUuid(uuid);
        room.setTitle(title);
        room.setPassword(encryptedPassword);

        Room savedRoom = roomRepository.save(room);  // DB 저장

        // DTO 생성 및 반환
        return new RoomResponseDTO.RoomCreateResponse(savedRoom.getRoomId(), savedRoom.getTitle(), savedRoom.getUuid());
    }
}
