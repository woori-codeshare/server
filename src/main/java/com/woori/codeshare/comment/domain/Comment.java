package com.woori.codeshare.comment.domain;

import com.woori.codeshare.global.config.BaseDateTimeEntity;
import com.woori.codeshare.snapshot.domain.Snapshot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Comment extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshot snapshot;

    @Column(nullable = false)
    private String content;

    private boolean solved;
}
