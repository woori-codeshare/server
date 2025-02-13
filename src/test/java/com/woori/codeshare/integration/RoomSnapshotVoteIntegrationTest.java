package com.woori.codeshare.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.woori.codeshare.room.controller.dto.RoomRequestDTO;
import com.woori.codeshare.snapshot.controller.dto.SnapshotRequestDTO;
import com.woori.codeshare.vote.controller.dto.VoteRequestDTO;
import com.woori.codeshare.vote.domain.VoteType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("방 생성 → 스냅샷 생성 → 투표 생성 → 투표 진행 테스트")
class RoomSnapshotVoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long createdRoomId;
    private Long createdSnapshotId;
    private Long createdVoteId;

    @Test
    @Order(1)
    @DisplayName("방 생성 테스트")
    void testCreateRoom() throws Exception {
        RoomRequestDTO.RoomCreateRequest roomRequest = new RoomRequestDTO.RoomCreateRequest("테스트 방", "secure1234");

        MvcResult result = mockMvc.perform(post("/api/v1/rooms/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_200"))
                .andReturn();

        // 응답에서 roomId 추출 및 Long 변환
        String jsonResponse = result.getResponse().getContentAsString();
        createdRoomId = Long.valueOf(JsonPath.read(jsonResponse, "$.data.roomId").toString());

        assertNotNull(createdRoomId);
    }

    @Test
    @Order(2)
    @DisplayName("스냅샷 생성 테스트")
    void testCreateSnapshot() throws Exception {
        assertNotNull(createdRoomId, "방 ID가 null입니다. 방 생성이 정상적으로 수행되었는지 확인하세요.");

        SnapshotRequestDTO.SnapshotCreateRequest snapshotRequest =
                new SnapshotRequestDTO.SnapshotCreateRequest(createdRoomId, "테스트 스냅샷", "설명", "public static void main(String[] args) {}");

        MvcResult result = mockMvc.perform(post("/api/v1/snapshots/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(snapshotRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_200"))
                .andReturn();

        // 응답에서 snapshotId 추출 및 Long 변환
        String jsonResponse = result.getResponse().getContentAsString();
        createdSnapshotId = Long.valueOf(JsonPath.read(jsonResponse, "$.data.snapshotId").toString());

        assertNotNull(createdSnapshotId);
    }

    @Test
    @Order(3)
    @DisplayName("투표 생성 테스트")
    void testCreateVote() throws Exception {
        assertNotNull(createdSnapshotId, "스냅샷 ID가 null입니다. 스냅샷 생성이 정상적으로 수행되었는지 확인하세요.");

        VoteRequestDTO.VoteCreateRequest voteRequest = new VoteRequestDTO.VoteCreateRequest("이해되었나요?");

        MvcResult result = mockMvc.perform(post("/api/v1/votes/" + createdSnapshotId + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_200"))
                .andReturn();

        // 응답에서 voteId 추출 및 Long 변환
        String jsonResponse = result.getResponse().getContentAsString();
        createdVoteId = Long.valueOf(JsonPath.read(jsonResponse, "$.data.voteId").toString());

        assertNotNull(createdVoteId);
    }

    @Test
    @Order(4)
    @DisplayName("투표 진행 테스트 (POSITIVE)")
    void testCastVote() throws Exception {
        assertNotNull(createdVoteId, "투표 ID가 null입니다. 투표 생성이 정상적으로 수행되었는지 확인하세요.");

        VoteRequestDTO.VoteCastRequest voteCastRequest = new VoteRequestDTO.VoteCastRequest(VoteType.POSITIVE);

        mockMvc.perform(post("/api/v1/votes/" + createdVoteId + "/cast")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteCastRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_200"));
    }

}
