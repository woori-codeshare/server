package com.woori.codeshare.vote.domain;

import com.woori.codeshare.global.config.BaseDateTimeEntity;
import com.woori.codeshare.snapshot.domain.Snapshot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Vote extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshot snapshot;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteRecord> voteRecords;

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }
}
