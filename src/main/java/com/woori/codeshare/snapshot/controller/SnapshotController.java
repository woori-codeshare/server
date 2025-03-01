package com.woori.codeshare.snapshot.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.snapshot.controller.dto.SnapshotRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.SnapshotResponseDTO;
import com.woori.codeshare.snapshot.service.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/snapshots/")
@Tag(name = "Snapshot", description = "Snapshot 관련 API")
public class SnapshotController {

    private final SnapshotService snapshotService;

    /**
     * 스냅샷 저장 API
     *
     * @param request 스냅샷 저장 요청 DTO
     * @return 스냅샷 저장 응답 DTO
     */
    @PostMapping("/{roomId}")
    @Operation(summary = "스냅샷 생성 API", description = "특정 방에 대한 코드 스냅샷을 저장합니다.")
    public ResponseEntity<ApiResponse<SnapshotResponseDTO.SnapshotCreateResponse>> saveSnapshot(
            @Parameter(description = "방 ID", required = true, example = "1")
            @PathVariable(name = "roomId") Long roomId,
            @RequestBody SnapshotRequestDTO.SnapshotCreateRequest request) {

        SnapshotResponseDTO.SnapshotCreateResponse responseDTO = snapshotService.saveSnapshot(roomId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 스냅샷 목록 조회 API
     *
     * @param uuid 방 UUID
     * @return 스냅샷 상세 목록 응답 DTO
     */
    @GetMapping("/{uuid}/")
    @Operation(summary = "전체 스냅샷 및 질문 목록 조회 API", description = "방 ID를 사용하여 해당 방의 스냅샷과 질문 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<SnapshotResponseDTO.SnapshotDetailResponse>>> getSnapshots(
            @Parameter(description = "방 UUID", required = true, example = "1")
            @PathVariable(name = "uuid") String uuid) {

        List<SnapshotResponseDTO.SnapshotDetailResponse> snapshots = snapshotService.getSnapshots(uuid);
        return ResponseEntity.ok(ApiResponse.of(snapshots));
    }
}
