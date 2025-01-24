package com.woori.codeshare.snapshot.repository;

import com.woori.codeshare.room.domain.Room;
import com.woori.codeshare.snapshot.domain.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {

    @Query("SELECT s FROM Snapshot s " +
            "LEFT JOIN FETCH s.comments c " +
            "WHERE s.room = :room AND s.createdAt >= :createdAt " +
            "ORDER BY s.createdAt ASC")
    List<Snapshot> findSnapshotsWithCommentsByRoomAndCreatedAtAfter(@Param("room") Room room,
                                                                    @Param("createdAt") LocalDateTime createdAt);
}
