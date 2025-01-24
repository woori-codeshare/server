package com.woori.codeshare.snapshot.repository;

import com.woori.codeshare.snapshot.domain.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {
}
