package com.woori.codeshare.snapshot.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;  // 투표 고유 ID (PK)

    @ManyToOne
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshot snapshot;  // 투표가 연결된 스냅샷 (FK)

    private String title;  // 투표 제목 (optional)

    private int positive;  // 긍정 투표 수
    private int negative;  // 부정 투표 수
}
