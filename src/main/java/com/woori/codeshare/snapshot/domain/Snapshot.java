package com.woori.codeshare.snapshot.domain;

import com.woori.codeshare.room.domain.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Snapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapshotId;  // 스냅샷 고유 ID (PK)

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;  // 스냅샷이 속한 방 (FK)

    @Column(length = 500)
    private String description;  // 스냅샷 설명

    @Lob  // Large Object
    private String code;  // 스냅샷 코드 데이터

    private LocalDateTime createdAt;  // 스냅샷 생성 시각

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;  // 스냅샷에 포함된 댓글 목록

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;  // 스냅샷에 포함된 투표 목록

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // 생성 시각 자동 설정
    }
}
