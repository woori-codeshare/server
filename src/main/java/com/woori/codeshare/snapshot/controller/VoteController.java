package com.woori.codeshare.snapshot.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.snapshot.controller.dto.VoteRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.VoteResponseDTO;
import com.woori.codeshare.snapshot.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/")
@Tag(name = "Vote", description = "Vote 관련 API")
public class VoteController {

    private final VoteService voteService;

    /**
     * 투표 생성 API
     *
     * @param snapshotId 스냅샷 ID
     * @param request    투표 생성 요청 DTO
     * @return 생성된 투표 정보 응답 DTO
     */
    @PostMapping("/{snapshotId}/new")
    @Operation(summary = "투표 생성 API", description = "특정 스냅샷에 대한 투표를 생성합니다.")
    public ResponseEntity<ApiResponse<VoteResponseDTO.VoteCreateResponse>> createVote(
            @Parameter(description = "스냅샷 ID", required = true, example = "1")
            @PathVariable(name = "snapshotId") Long snapshotId,
            @RequestBody VoteRequestDTO.VoteCreateRequest request) {

        VoteResponseDTO.VoteCreateResponse responseDTO = voteService.createVote(snapshotId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }
}
