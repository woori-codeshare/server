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

import java.util.List;
import java.util.stream.Collectors;

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
    public CommentResponseDTO.CommentCreateResponse addComment(Long snapshotId, CommentRequestDTO.CommentCreateRequest request) {
        Snapshot snapshot = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new SnapshotException(SnapshotErrorCode.SNAPSHOT_NOT_FOUND));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = new Comment();
        comment.setSnapshot(snapshot);
        comment.setContent(request.getContent());
        comment.setSolved(false);
        comment.setParentComment(parentComment);

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentCreateResponse.builder()
                .commentId(savedComment.getCommentId())
                .parentCommentId(parentComment != null ? parentComment.getCommentId() : null)
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
    public CommentResponseDTO.CommentResolveResponse resolveComment(Long commentId, CommentRequestDTO.CommentResolveRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.setSolved(request.isSolved());
        Comment updatedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentResolveResponse.builder()
                .commentId(updatedComment.getCommentId())
                .solved(updatedComment.isSolved())
                .build();
    }

    /**
     * 질문 내용 수정 로직
     *
     * @param request 수정 요청 DTO
     * @return 수정된 댓글 응답 DTO
     */
    @Transactional
    public CommentResponseDTO.CommentUpdateResponse updateComment(Long commentId, CommentRequestDTO.CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.setContent(request.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return CommentResponseDTO.CommentUpdateResponse.builder()
                .commentId(updatedComment.getCommentId())
                .content(updatedComment.getContent())
                .updatedAt(updatedComment.getUpdatedAt())
                .build();
    }

    /**
     * 질문 삭제 로직
     *
     * @param commentId 삭제할 댓글 ID
     */
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }

    /**
     * 질문 목록 조회 로직
     *
     * @param snapshotId 스냅샷 ID
     */
    @Transactional
    public List<CommentResponseDTO.CommentListResponse> getComments(Long snapshotId) {
        Snapshot snapshot = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new SnapshotException(SnapshotErrorCode.SNAPSHOT_NOT_FOUND));

        // snapshotId로 댓글 목록 조회
        List<Comment> comments = commentRepository.findBySnapshotId(snapshotId);

        // 댓글 목록 Response DTO로 변환 후 반환
        return comments.stream()
                .map(comment -> CommentResponseDTO.CommentListResponse.builder()
                        .commentId(comment.getCommentId())
                        .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null)
                        .content(comment.getContent())
                        .solved(comment.isSolved())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
