package com.woori.codeshare.room.domain;

import com.woori.codeshare.snapshot.domain.Snapshot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;  // 방 고유 ID (PK)

    @Column(unique = true, nullable = false)
    private String uuid;  // 클라이언트용 방 고유 식별자

    @Column(nullable = false)
    private String title;  // 방 제목

    @Column(nullable = false)
    private String password;  // 암호화된 방 비밀번호

    private LocalDateTime createdAt;  // 방 생성 시각

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Snapshot> snapshots;  // 방이 포함하는 스냅샷 목록

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // 생성 시각 자동 설정
    }
}
