package com.woori.codeshare.room.repository;

import com.woori.codeshare.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
