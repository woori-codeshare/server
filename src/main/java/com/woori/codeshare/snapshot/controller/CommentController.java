package com.woori.codeshare.snapshot.controller;

import com.woori.codeshare.global.response.ApiResponse;
import com.woori.codeshare.global.response.ResponseCode;
import com.woori.codeshare.snapshot.controller.dto.CommentRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.CommentResponseDTO;
import com.woori.codeshare.snapshot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment", description = "Comment 관련 API")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록 API
     *
     * @param request 댓글 등록 요청 DTO
     * @return 댓글 등록 응답 DTO
     */
    @PostMapping
    @Operation(summary = "댓글 등록 API", description = "특정 스냅샷에 댓글(질문)을 등록합니다.")
    public ResponseEntity<ApiResponse<CommentResponseDTO.CommentCreateResponse>> addComment(
            @RequestBody @Valid CommentRequestDTO.CommentCreateRequest request) {

        CommentResponseDTO.CommentCreateResponse responseDTO = commentService.addComment(request);
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS, responseDTO));
    }
}
