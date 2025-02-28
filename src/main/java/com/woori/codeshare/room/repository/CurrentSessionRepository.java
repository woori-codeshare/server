package com.woori.codeshare.room.repository;

import com.woori.codeshare.room.domain.CurrentSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrentSessionRepository extends JpaRepository<CurrentSession, Long> {
    Optional<CurrentSession> findByRoom_RoomId(Long roomId);
}
