package com.woori.codeshare.vote.repository;

import com.woori.codeshare.vote.domain.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    // 특정 voteId에 대한 투표 기록 존재 여부
    @Query("SELECT COUNT(v) > 0 FROM VoteRecord v WHERE v.vote.voteId = :voteId")
    boolean existsByVoteId(@Param("voteId") Long voteId);

    // 특정 voteId에 대한 투표 타입별 개수 조회
    @Query("SELECT v.voteType AS voteType, COUNT(v) AS count FROM VoteRecord v WHERE v.vote.voteId = :voteId GROUP BY v.voteType")
    List<Object[]> countVotesByVoteId(@Param("voteId") Long voteId);

    // 특정 snapshotId에 대한 모든 투표 결과 조회
    @Query("SELECT v.vote.voteId, v.voteType, COUNT(v) FROM VoteRecord v " +
            "WHERE v.vote.snapshot.snapshotId = :snapshotId " +
            "GROUP BY v.vote.voteId, v.voteType")
    List<Object[]> countVotesBySnapshotId(@Param("snapshotId") Long snapshotId);

    // 특정 voteId에 대한 중복 투표 여부 확인 (세션 ID 기반)
    @Query("SELECT COUNT(v) > 0 FROM VoteRecord v WHERE v.vote.voteId = :voteId AND v.sessionId = :sessionId")
    boolean existsByVoteIdAndSessionId(@Param("voteId") Long voteId, @Param("sessionId") String sessionId);
}


