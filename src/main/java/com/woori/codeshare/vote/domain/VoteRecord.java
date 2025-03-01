package com.woori.codeshare.vote.domain;

import com.woori.codeshare.global.config.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VoteRecord extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteRecordId;

    @Column(nullable = false, unique = true)
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;  // (POSITIVE, NEUTRAL, NEGATIVE)
}

