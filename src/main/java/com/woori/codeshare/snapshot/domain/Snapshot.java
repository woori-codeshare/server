package com.woori.codeshare.snapshot.domain;

import com.woori.codeshare.comment.domain.Comment;
import com.woori.codeshare.global.config.BaseDateTimeEntity;
import com.woori.codeshare.room.domain.Room;
import com.woori.codeshare.vote.domain.Vote;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Snapshot extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapshotId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column
    private String title;

    @Column(length = 500)
    private String description;

    @Lob  // Large Object
    private String code;

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;
}
