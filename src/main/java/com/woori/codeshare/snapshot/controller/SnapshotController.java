package com.woori.codeshare.snapshot.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.snapshot.controller.dto.SnapshotRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.SnapshotResponseDTO;
import com.woori.codeshare.snapshot.service.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    @Operation(summary = "스냅샷 저장 API", description = "특정 방에 대한 코드 스냅샷을 저장합니다.")
    public ResponseEntity<ApiResponse<SnapshotResponseDTO.SnapshotCreateResponse>> saveSnapshot(
            @RequestBody SnapshotRequestDTO.SnapshotCreateRequest request) {

        SnapshotResponseDTO.SnapshotCreateResponse responseDTO = snapshotService.saveSnapshot(request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }
}
