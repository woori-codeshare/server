package com.woori.codeshare.room.domain;

import com.woori.codeshare.global.config.BaseDateTimeEntity;
import com.woori.codeshare.snapshot.domain.Snapshot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Snapshot> snapshots;
}
