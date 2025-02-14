package com.woori.codeshare.room.repository;

import com.woori.codeshare.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Room r WHERE r.title = :title")
    boolean checkDuplicateTitle(@Param("title") String title);

    Optional<Room> findByUuid(String uuid);
}
