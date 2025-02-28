package com.woori.codeshare.room.domain;

import com.woori.codeshare.global.config.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LiveSession extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Lob
    private String code;

    public LiveSession(Room room) {
        this.room = room;
        this.code = "";
    }
}
