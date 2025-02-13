package com.woori.codeshare.vote.service;

import com.woori.codeshare.snapshot.domain.Snapshot;
import com.woori.codeshare.snapshot.exception.SnapshotErrorCode;
import com.woori.codeshare.snapshot.exception.SnapshotException;
import com.woori.codeshare.snapshot.repository.SnapshotRepository;
import com.woori.codeshare.vote.controller.dto.VoteRequestDTO;
import com.woori.codeshare.vote.controller.dto.VoteResponseDTO;
import com.woori.codeshare.vote.domain.Vote;
import com.woori.codeshare.vote.domain.VoteRecord;
import com.woori.codeshare.vote.domain.VoteType;
import com.woori.codeshare.vote.exception.VoteErrorCode;
import com.woori.codeshare.vote.exception.VoteException;
import com.woori.codeshare.vote.repository.VoteRecordRepository;
import com.woori.codeshare.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new VoteException(VoteErrorCode.VOTE_NOT_FOUND));

        vote.updateTitle(request.getTitle());
        Vote updatedVote = voteRepository.save(vote);

        return VoteResponseDTO.VoteTitleUpdateResponse.builder()
                .voteId(updatedVote.getVoteId())
                .title(updatedVote.getTitle())
                .build();
    }

    /**
     * 특정 투표 결과 조회 로직
     *
     * @param voteId 투표 ID
     * @return 전체 투표 결과 응답 DTO
     */
    @Transactional
    public VoteResponseDTO.VoteResultResponse getVoteResults(Long voteId) {
        if (!voteRecordRepository.existsByVoteId(voteId)) {
            throw new VoteException(VoteErrorCode.VOTE_NOT_FOUND);
        }

        List<Object[]> results = voteRecordRepository.countVotesByVoteId(voteId);

        Map<String, Integer> voteCounts = new HashMap<>();
        for (VoteType type : VoteType.values()) {
            voteCounts.put(type.name(), 0);
        }

        for (Object[] row : results) {
            VoteType voteType = (VoteType) row[0];
            Long count = (Long) row[1];
            voteCounts.put(voteType.name(), count.intValue());
        }

        return VoteResponseDTO.VoteResultResponse.builder()
                .voteId(voteId)
                .voteCounts(voteCounts)
                .build();
    }

    /**
     * 특정 스냅샷의 모든 투표 결과 조회 로직
     *
     * @param snapshotId 스냅샷 ID
     * @return 스냅샷에 속한 모든 투표의 결과 리스트
     */
    @Transactional(readOnly = true)
    public List<VoteResponseDTO.VoteResultResponse> getAllVoteResultsBySnapshot(Long snapshotId) {
        List<Object[]> results = voteRecordRepository.countVotesBySnapshotId(snapshotId);

        // 결과를 그룹화하기 위한 맵: key = voteId, value = Map<voteType, count>
        Map<Long, Map<String, Integer>> snapshotVoteMap = new HashMap<>();

        for (Object[] row : results) {
            Long voteId = (Long) row[0];
            VoteType voteType = (VoteType) row[1];
            Long count = (Long) row[2];

            snapshotVoteMap.putIfAbsent(voteId, new HashMap<>());
            Map<String, Integer> voteCounts = snapshotVoteMap.get(voteId);

            for (VoteType type : VoteType.values()) {
                voteCounts.putIfAbsent(type.name(), 0);
            }

            voteCounts.put(voteType.name(), count.intValue());
        }

        List<VoteResponseDTO.VoteResultResponse> responseList = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Integer>> entry : snapshotVoteMap.entrySet()) {
            responseList.add(VoteResponseDTO.VoteResultResponse.builder()
                    .voteId(entry.getKey())
                    .voteCounts(entry.getValue())
                    .build());
        }

        return responseList;
    }

}
