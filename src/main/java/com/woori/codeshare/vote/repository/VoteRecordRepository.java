package com.woori.codeshare.vote.repository;

import com.woori.codeshare.vote.domain.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    @Query("SELECT COUNT(v) > 0 FROM VoteRecord v WHERE v.vote.id = :voteId")
    boolean existsByVoteId(@Param("voteId") Long voteId);

    @Query("SELECT v.voteType, COUNT(v) FROM VoteRecord v WHERE v.vote.id = :voteId GROUP BY v.voteType")
    List<Object[]> countVotesByVoteId(@Param("voteId") Long voteId);
}
