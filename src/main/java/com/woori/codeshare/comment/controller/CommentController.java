package com.woori.codeshare.comment.controller;

import com.woori.codeshare.comment.controller.dto.CommentRequestDTO;
import com.woori.codeshare.comment.controller.dto.CommentResponseDTO;
import com.woori.codeshare.comment.service.CommentService;
import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment", description = "Comment 관련 API")
public class CommentController {

    private final CommentService commentService;

    /**
     * 질문(댓글) 등록 API
     *
     * @param request 댓글 등록 요청 DTO
     * @return 댓글 등록 응답 DTO
     */
    @PostMapping("/{snapshotId}/new")
    @Operation(summary = "질문(댓글) 등록 API", description = "특정 스냅샷에 댓글(질문)을 등록합니다.")
    public ResponseEntity<ApiResponse<CommentResponseDTO.CommentCreateResponse>> addComment(
            @Parameter(description = "스냅샷 ID", required = true, example = "1")
            @PathVariable(name = "snapshotId") Long snapshotId,
            @RequestBody @Valid CommentRequestDTO.CommentCreateRequest request) {

        CommentResponseDTO.CommentCreateResponse responseDTO = commentService.addComment(snapshotId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 질문(댓글) 해결 여부 업데이트 API
     *
     * @param request 해결 여부 업데이트 요청 DTO
     * @return 해결 여부 업데이트 응답 DTO
     */
    @PatchMapping("/{commentId}/resolve")
    @Operation(summary = "질문 해결 여부 업데이트 API", description = "특정 댓글의 해결 여부를 업데이트합니다.")
    public ResponseEntity<ApiResponse<CommentResponseDTO.CommentResolveResponse>> resolveComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody @Valid CommentRequestDTO.CommentResolveRequest request) {

        CommentResponseDTO.CommentResolveResponse responseDTO = commentService.resolveComment(commentId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 질문(댓글) 수정 API
     *
     * @param request 수정 요청 DTO
     * @return 수정된 질문 응답 DTO
     */
    @PatchMapping("/{commentId}/update")
    @Operation(summary = "질문 수정 API", description = "댓글 ID를 사용하여 질문 내용을 수정합니다.")
    public ResponseEntity<ApiResponse<CommentResponseDTO.CommentUpdateResponse>> updateComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody @Valid CommentRequestDTO.CommentUpdateRequest request) {

        CommentResponseDTO.CommentUpdateResponse responseDTO = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }

    /**
     * 질문(댓글) 삭제 API
     *
     * @param commentId 댓글 ID
     * @return 성공 메시지
     */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "질문 삭제 API", description = "특정 댓글을 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, null));
    }

    /**
     * 질문(댓글) 조회 API
     *
     * @param snapshotId 스냅샷 ID
     * @return 댓글 목록 응답 DTO
     */
    @GetMapping("/{snapshotId}")
    @Operation(summary = "질문 조회 API", description = "특정 스냅샷의 댓글(질문) 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO.CommentListResponse>>> getComments(
            @Parameter(description = "스냅샷 ID", required = true, example = "1")
            @PathVariable(name = "snapshotId") Long snapshotId) {

        List<CommentResponseDTO.CommentListResponse> responseDTO = commentService.getComments(snapshotId);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }
}
