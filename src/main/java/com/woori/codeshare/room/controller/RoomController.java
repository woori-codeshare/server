package com.woori.codeshare.room.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.room.controller.dto.RoomRequestDTO;
import com.woori.codeshare.room.controller.dto.RoomResponseDTO;
import com.woori.codeshare.room.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Room", description = "Room 관련 API")
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 생성 API
     *
     * @param request 방 생성 요청 DTO
     * @return 방 생성 응답 DTO
     */
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<RoomResponseDTO.RoomCreateResponse>> createRoom(
            @RequestBody RoomRequestDTO.RoomCreateRequest request) {
        RoomResponseDTO.RoomCreateResponse responseDTO = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.of(responseDTO));
    }
}
