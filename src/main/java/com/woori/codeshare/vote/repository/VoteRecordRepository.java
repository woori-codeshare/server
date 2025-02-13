package com.woori.codeshare.vote.repository;

import com.woori.codeshare.vote.domain.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {
}
