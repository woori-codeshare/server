package com.woori.codeshare.snapshot.service;

import com.woori.codeshare.room.exception.RoomErrorCode;
import com.woori.codeshare.room.exception.RoomException;
import com.woori.codeshare.snapshot.controller.dto.VoteRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.VoteResponseDTO;
import com.woori.codeshare.snapshot.domain.Snapshot;
import com.woori.codeshare.snapshot.domain.Vote;
import com.woori.codeshare.snapshot.repository.SnapshotRepository;
import com.woori.codeshare.snapshot.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final SnapshotRepository snapshotRepository;

    /**
     * 투표 생성 로직
     *
     * @param snapshotId 스냅샷 ID
     * @param request    투표 생성 요청 DTO
     * @return 생성된 투표 응답 DTO
     */
    @Transactional
    public VoteResponseDTO.VoteCreateResponse createVote(Long snapshotId, VoteRequestDTO.VoteCreateRequest request) {
        // 스냅샷 조회
        Snapshot snapshot = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.ROOM_NOT_FOUND));

        // 투표 엔티티 생성
        Vote vote = new Vote();
        vote.setSnapshot(snapshot);
        vote.setTitle(request.getTitle());
        voteRepository.save(vote);

        // 응답 DTO 생성
        return VoteResponseDTO.VoteCreateResponse.builder()
                .voteId(vote.getVoteId())
                .snapshotId(snapshot.getSnapshotId())
                .title(vote.getTitle())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
