package com.woori.codeshare.snapshot.repository;

import com.woori.codeshare.snapshot.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
