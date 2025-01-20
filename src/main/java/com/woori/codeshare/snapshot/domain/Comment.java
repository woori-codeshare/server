package com.woori.codeshare.snapshot.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;  // 댓글 고유 ID (PK)

    @ManyToOne
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshot snapshot;  // 댓글이 연결된 스냅샷 (FK)

    @Lob
    private String content;  // 댓글 내용

    private boolean isChecked;  // 댓글 확인 여부 (해결됨 여부)

    private LocalDateTime createdAt;  // 댓글 생성 시각

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // 생성 시각 자동 설정
    }
}
