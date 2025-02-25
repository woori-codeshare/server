package com.woori.codeshare.vote.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.vote.controller.dto.VoteRequestDTO;
import com.woori.codeshare.vote.controller.dto.VoteResponseDTO;
import com.woori.codeshare.vote.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/")
@Tag(name = "Vote", description = "Vote 관련 API")
public class VoteController {

    private final VoteService voteService;

    /**
     * 투표 생성 API
     */
    @PostMapping("/{snapshotId}/new")
    @Operation(summary = "투표 생성 API", description = "특정 스냅샷에 대한 투표를 생성합니다.")
    public ResponseEntity<ApiResponse<VoteResponseDTO.VoteCreateResponse>> createVote(
            @Parameter(description = "스냅샷 ID", required = true, example = "1")
            @PathVariable(name = "snapshotId") Long snapshotId,
            @RequestBody @Valid VoteRequestDTO.VoteCreateRequest request) {

        VoteResponseDTO.VoteCreateResponse responseDTO = voteService.createVote(snapshotId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 투표 진행하기 API
     */
    @PostMapping("/{voteId}/cast")
    @Operation(summary = "투표 진행하기 API", description = "특정 투표에 대해 투표를 진행합니다.")
    public ResponseEntity<ApiResponse<VoteResponseDTO.VoteCastResponse>> castVote(
            @Parameter(description = "투표 ID", required = true, example = "10")
            @PathVariable(name = "voteId") Long voteId,
            @RequestBody @Valid VoteRequestDTO.VoteCastRequest request,
            HttpServletRequest httpRequest) {

        voteService.castVote(voteId, request, httpRequest);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS));
    }


    /**
     * 투표 제목 수정 API
     */
    @PatchMapping("/{voteId}/title")
    @Operation(summary = "투표 제목 수정 API", description = "특정 투표의 제목을 수정합니다.")
    public ResponseEntity<ApiResponse<VoteResponseDTO.VoteTitleUpdateResponse>> updateVoteTitle(
            @Parameter(description = "투표 ID", required = true, example = "1")
            @PathVariable(name = "voteId") Long voteId,
            @RequestBody @Validated VoteRequestDTO.VoteTitleUpdateRequest request) {

        VoteResponseDTO.VoteTitleUpdateResponse responseDTO = voteService.updateVoteTitle(voteId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 특정 투표 결과 조회 API
     */
    @GetMapping("/{voteId}/results")
    @Operation(summary = "특정 투표 결과 조회 API", description = "특정 투표의 결과를 조회합니다.")
    public ResponseEntity<ApiResponse<VoteResponseDTO.VoteResultResponse>> getVoteResults(
            @Parameter(description = "투표 ID", required = true, example = "1")
            @PathVariable(name = "voteId") Long voteId) {

        VoteResponseDTO.VoteResultResponse responseDTO = voteService.getVoteResults(voteId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 특정 스냅샷의 전체 투표 결과 조회 API
     */
    @GetMapping("/{snapshotId}/results/all")
    @Operation(summary = "특정 스냅샷의 전체 투표 결과 조회 API", description = "특정 스냅샷의 모든 투표 결과를 조회합니다.")
    public ResponseEntity<ApiResponse<List<VoteResponseDTO.VoteResultResponse>>> getAllVoteResultsBySnapshot(
            @Parameter(description = "스냅샷 ID", required = true, example = "1")
            @PathVariable(name = "snapshotId") Long snapshotId) {

        List<VoteResponseDTO.VoteResultResponse> responseList = voteService.getAllVoteResultsBySnapshot(snapshotId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseList));
    }

    /**
     * 특정 투표 삭제 API
     */
    @DeleteMapping("/{voteId}")
    @Operation(summary = "투표 삭제 API", description = "voteId에 따라 특정 투표를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteVote(
            @Parameter(description = "투표 ID", required = true, example = "1")
            @PathVariable(name = "voteId") Long voteId) {

        voteService.deleteVote(voteId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, null));
    }
}
