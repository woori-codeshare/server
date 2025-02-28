package com.woori.codeshare.room.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.room.controller.dto.LiveSessionRequestDTO;
import com.woori.codeshare.room.controller.dto.LiveSessionResponseDTO;
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
     * @return 방 생성 응답 DTO (공유 URL 포함)
     */
    @Operation(summary = "방 생성 API", description = "방 제목과 비밀번호를 사용하여 방을 생성합니다.")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<RoomResponseDTO.RoomCreateResponse>> createRoom(
            @RequestBody RoomRequestDTO.RoomCreateRequest request) {
        RoomResponseDTO.RoomCreateResponse responseDTO = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.of(responseDTO));
    }

    /**
     * 방 입장 API (UUID 기반)
     *
     * @param uuid     방 UUID
     * @param password 비밀번호
     * @return 방 입장 결과
     */
    @PostMapping("/enter/{uuid}")
    @Operation(summary = "방 입장 API", description = "방 UUID와 비밀번호를 사용하여 방에 입장합니다.")
    public ResponseEntity<ApiResponse<RoomResponseDTO.RoomEnterResponse>> enterRoom(
            @Parameter(description = "방의 UUID", required = true, example = "4e1bf933-cc30-4b74-840e-c2afc9532704")
            @PathVariable(name = "uuid") String uuid,
            @Parameter(description = "방 비밀번호", required = true, example = "1234")
            @RequestParam(name = "password") String password) {
        RoomResponseDTO.RoomEnterResponse responseDTO = roomService.enterRoomByUuid(uuid, password);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.CONFIRM, responseDTO));
    }

    /**
     * 현재 코드 세션 저장 API
     */
    @PostMapping("/{roomId}/live-session/save")
    @Operation(summary = "현재 코드 세션 저장 API", description = "특정 방의 실시간 코드 내용을 저장합니다.")
    public ResponseEntity<ApiResponse<LiveSessionResponseDTO.LiveSessionResponse>> saveLiveSession(
            @PathVariable Long roomId,
            @RequestBody LiveSessionRequestDTO.LiveSessionRequest request) {

        LiveSessionResponseDTO.LiveSessionResponse response = roomService.saveLiveSession(roomId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, response));
    }

    /**
     * 현재 코드 세션 조회 API
     */
    @GetMapping("/{roomId}/live-session")
    @Operation(summary = "현재 코드 세션 조회 API", description = "특정 방의 실시간 코드 내용을 조회합니다.")
    public ResponseEntity<ApiResponse<LiveSessionResponseDTO.LiveSessionResponse>> getLiveSession(
            @PathVariable Long roomId) {

        LiveSessionResponseDTO.LiveSessionResponse response = roomService.getLiveSession(roomId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, response));
    }

}

