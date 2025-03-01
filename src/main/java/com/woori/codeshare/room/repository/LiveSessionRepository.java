package com.woori.codeshare.room.repository;

import com.woori.codeshare.room.domain.LiveSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveSessionRepository extends JpaRepository<LiveSession, Long> {
    Optional<LiveSession> findByRoom_RoomId(Long roomId);
}
