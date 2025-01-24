package com.woori.codeshare.comment.service;

import com.woori.codeshare.comment.controller.dto.CommentRequestDTO;
import com.woori.codeshare.comment.controller.dto.CommentResponseDTO;
import com.woori.codeshare.comment.domain.Comment;
import com.woori.codeshare.comment.exception.CommentErrorCode;
import com.woori.codeshare.comment.exception.CommentException;
import com.woori.codeshare.comment.repository.CommentRepository;
import com.woori.codeshare.snapshot.domain.Snapshot;
import com.woori.codeshare.snapshot.exception.SnapshotErrorCode;
import com.woori.codeshare.snapshot.exception.SnapshotException;
import com.woori.codeshare.snapshot.repository.SnapshotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SnapshotRepository snapshotRepository;

    /**
     * 댓글 등록 로직
     *
     * @param request 댓글 등록 요청 DTO
     * @return 등록된 댓글 응답 DTO
     */
    @Transactional
    public CommentResponseDTO.CommentCreateResponse addComment(CommentRequestDTO.CommentCreateRequest request) {
        Snapshot snapshot = snapshotRepository.findById(request.getSnapshotId())
                .orElseThrow(() -> new SnapshotException(SnapshotErrorCode.SNAPSHOT_NOT_FOUND));

        Comment comment = new Comment();
        comment.setSnapshot(snapshot);
        comment.setContent(request.getContent());
        comment.setChecked(false);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentCreateResponse.builder()
                .commentId(savedComment.getCommentId())
                .snapshotId(snapshot.getSnapshotId())
                .content(savedComment.getContent())
                .build();
    }

    /**
     * 질문 해결 여부 업데이트 로직
     *
     * @param request 해결 여부 업데이트 요청 DTO
     * @return 업데이트된 댓글 응답 DTO
     */
    @Transactional
    public CommentResponseDTO.CommentResolveResponse resolveComment(CommentRequestDTO.CommentResolveRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.setChecked(request.isChecked());
        Comment updatedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentResolveResponse.builder()
                .commentId(updatedComment.getCommentId())
                .isChecked(updatedComment.isChecked())
                .build();
    }

    /**
     * 질문 내용 수정 로직
     *
     * @param request 수정 요청 DTO
     * @return 수정된 댓글 응답 DTO
     */
    @Transactional
    public CommentResponseDTO.CommentUpdateResponse updateComment(CommentRequestDTO.CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentUpdateResponse.builder()
                .commentId(updatedComment.getCommentId())
                .content(updatedComment.getContent())
                .updatedAt(updatedComment.getUpdatedAt())
                .build();
    }
}
