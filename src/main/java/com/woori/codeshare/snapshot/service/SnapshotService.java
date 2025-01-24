package com.woori.codeshare.snapshot.service;

import com.woori.codeshare.room.domain.Room;
import com.woori.codeshare.room.exception.RoomErrorCode;
import com.woori.codeshare.room.exception.RoomException;
import com.woori.codeshare.room.repository.RoomRepository;
import com.woori.codeshare.snapshot.controller.dto.SnapshotRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.SnapshotResponseDTO;
import com.woori.codeshare.snapshot.domain.Snapshot;
import com.woori.codeshare.snapshot.repository.SnapshotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final RoomRepository roomRepository;

    /**
     * 스냅샷 저장 로직
     *
     * @param request 스냅샷 저장 요청 DTO
     * @return 저장된 스냅샷 응답 DTO
     */
    @Transactional
    public SnapshotResponseDTO.SnapshotCreateResponse saveSnapshot(SnapshotRequestDTO.SnapshotCreateRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomException(RoomErrorCode.ROOM_NOT_FOUND));

        String snapshotTitle = request.getTitle();
        if (snapshotTitle == null || snapshotTitle.trim().isEmpty()) {
            snapshotTitle = getCurrentFormattedTime(); // 제목이 없을 경우 시간으로 설정
        }

        // Snapshot 엔티티 생성
        Snapshot snapshot = new Snapshot();
        snapshot.setRoom(room);
        snapshot.setTitle(snapshotTitle);
        snapshot.setDescription(request.getDescription());
        snapshot.setCode(request.getCode());

        // Snapshot 저장
        Snapshot savedSnapshot = snapshotRepository.save(snapshot);

        // Response DTO 생성
        return SnapshotResponseDTO.SnapshotCreateResponse.builder()
                .snapshotId(savedSnapshot.getSnapshotId())
                .roomId(room.getRoomId())
                .title(savedSnapshot.getTitle())
                .description(savedSnapshot.getDescription())
                .code(savedSnapshot.getCode())
                .createdAt(savedSnapshot.getCreatedAt())
                .build();
    }

    /**
     * 현재 시간을 "2025년 2월 17일, 15:00" 형식으로 반환
     *
     * @return 포맷된 시간 문자열
     */
    private String getCurrentFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일, HH:mm");
        return LocalDateTime.now().format(formatter);
    }
}
