package com.woori.codeshare.room.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.room.controller.dto.RoomRequestDTO;
import com.woori.codeshare.room.controller.dto.RoomResponseDTO;
import com.woori.codeshare.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @Operation(summary = "방 생성 API", description = "방 제목과 비밀번호를 사용하여 방에 입장합니다.")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<RoomResponseDTO.RoomCreateResponse>> createRoom(
            @RequestBody RoomRequestDTO.RoomCreateRequest request) {
        RoomResponseDTO.RoomCreateResponse responseDTO = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.of(responseDTO));
    }

    /**
     * 방 입장 API
     *
     * @param roomId   방 ID
     * @param password 비밀번호
     * @return 방 입장 결과
     */
    @PostMapping("/{roomId}/enter")
    @Operation(summary = "방 입장 API", description = "방 ID와 비밀번호를 사용하여 방에 입장합니다.")
    public ResponseEntity<ApiResponse<RoomResponseDTO.RoomEnterResponse>> enterRoom(
            @Parameter(description = "방의 고유 ID", required = true, example = "1")
            @PathVariable(name = "roomId") Long roomId,
            @Parameter(description = "방 비밀번호", required = true, example = "1234")
            @RequestParam(name = "password") String password) {
        RoomResponseDTO.RoomEnterResponse responseDTO = roomService.enterRoom(roomId, password);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.CONFIRM, responseDTO));
    }
}
