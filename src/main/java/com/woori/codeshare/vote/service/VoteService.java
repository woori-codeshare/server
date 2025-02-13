package com.woori.codeshare.vote.service;

import com.woori.codeshare.snapshot.domain.Snapshot;
import com.woori.codeshare.snapshot.exception.SnapshotErrorCode;
import com.woori.codeshare.snapshot.exception.SnapshotException;
import com.woori.codeshare.snapshot.repository.SnapshotRepository;
import com.woori.codeshare.vote.controller.dto.VoteRequestDTO;
import com.woori.codeshare.vote.controller.dto.VoteResponseDTO;
import com.woori.codeshare.vote.domain.Vote;
import com.woori.codeshare.vote.domain.VoteRecord;
import com.woori.codeshare.vote.exception.VoteErrorCode;
import com.woori.codeshare.vote.exception.VoteException;
import com.woori.codeshare.vote.repository.VoteRecordRepository;
import com.woori.codeshare.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final SnapshotRepository snapshotRepository;
    private final VoteRecordRepository voteRecordRepository;

    /**
     * 투표 생성 로직
     *
     * @param snapshotId 스냅샷 ID
     * @param request    투표 생성 요청 DTO
     * @return 생성된 투표 응답 DTO
     */
    @Transactional
    public VoteResponseDTO.VoteCreateResponse createVote(Long snapshotId, VoteRequestDTO.VoteCreateRequest request) {
        // 스냅샷 ID를 이용해 Snapshot 조회
        Snapshot snapshot = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new SnapshotException(SnapshotErrorCode.SNAPSHOT_NOT_FOUND));

        Vote vote = new Vote();
        vote.setSnapshot(snapshot);
        vote.setTitle(request.getTitle());

        Vote savedVote = voteRepository.save(vote);

        return VoteResponseDTO.VoteCreateResponse.builder()
                .voteId(savedVote.getVoteId())
                .snapshotId(snapshot.getSnapshotId())
                .title(savedVote.getTitle())
                .createdAt(savedVote.getCreatedAt())
                .build();
    }

    /**
     * 투표 진행 로직
     *
     * @param voteId  투표 ID
     * @param request 투표 진행 요청 DTO
     * @return 투표 결과 응답 DTO
     */
    @Transactional
    public VoteResponseDTO.VoteCastResponse castVote(Long voteId, VoteRequestDTO.VoteCastRequest request) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new VoteException(VoteErrorCode.VOTE_NOT_FOUND));

        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVote(vote);
        voteRecord.setVoteType(request.getVoteType());

        voteRecordRepository.save(voteRecord);

        return VoteResponseDTO.VoteCastResponse.builder()
                .voteId(vote.getVoteId())
                .voteType(request.getVoteType())
                .build();
    }

    /**
     * 투표 제목 수정 로직
     *
     * @param voteId  투표 ID
     * @param request 투표 제목 수정 요청 DTO
     * @return 투표 제목 수정 응답 DTO
     */
    @Transactional
    public VoteResponseDTO.VoteTitleUpdateResponse updateVoteTitle(Long voteId, VoteRequestDTO.VoteTitleUpdateRequest request) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 투표가 존재하지 않습니다."));

        vote.updateTitle(request.getTitle());
        Vote updatedVote = voteRepository.save(vote);

        return VoteResponseDTO.VoteTitleUpdateResponse.builder()
                .voteId(updatedVote.getVoteId())
                .title(updatedVote.getTitle())
                .build();
    }
}
