package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerDatabaseTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    UserEntity userEntity;
    RsEventEntity rsEventEntity;



    @BeforeEach
    void should_reset_for_test() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity userEntity = UserEntity.builder()
                .userId(1)
                .userName("person1")
                .userGender("male")
                .userAge(30)
                .userEmail("p1@m.com")
                .userPhoneNum("12312345678")
                .userVoteNum(10)
                .build();
        userRepository.save(userEntity);

        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventId(1)
                .rsEventName("事件1")
                .rsEventKeyword("无")
                .userId(1)
                .build();
        rsEventRepository.save(rsEventEntity);

        VoteEntity voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
        voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
        voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
        voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
        voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
        voteEntity = VoteEntity.builder()
                .userEntity(userEntity)
                .rsEventEntity(rsEventEntity)
                .voteNum(1)
                .voteTime(LocalDateTime.now())
                .build();
        voteRepository.save(voteEntity);
    }

    @Test
    void should_vote_if_num_enough() throws Exception {

        VoteDto voteDto = VoteDto.builder()
                .rsEventId(1)
                .userId(1)
                .voteNum(1)
                .voteTime(null)
                .build();

       String voteJson = objectMapper.writeValueAsString(voteDto);

        mockMvc.perform(post("/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_vote_if_num_not_enough() throws Exception {

        VoteDto voteDto = VoteDto.builder()
                .rsEventId(1)
                .userId(1)
                .voteNum(11)
                .voteTime(null)
                .build();

        String voteJson = objectMapper.writeValueAsString(voteDto);

        mockMvc.perform(post("/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void should_get_vote_record_by_id_of_user_and_rs_event() throws Exception {
//
//        mockMvc.perform(get("/rsDatabase/vote/getRecord")
//                .param("userId", String.valueOf(userEntity.getUserId()))
//                .param("rsEventId", String.valueOf(rsEventEntity.getRsEventId())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(6)))
//                .andExpect(jsonPath("$[0].userId", is(userEntity.getUserId())))
//                .andExpect(jsonPath("$[0].rsEventId", is(userEntity.getUserId())))
//                .andExpect(jsonPath("$[0].voteNum", is(1)));
//    }

    @Test
    void should_get_vote_record_by_id_of_user_and_rs_event_in_page() throws Exception {

        mockMvc.perform(get("/vote/recordById")
                .param("userId", String.valueOf(userEntity.getUserId()))
                .param("rsEventId", String.valueOf(rsEventEntity.getRsEventId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId", is(userEntity.getUserId())))
                .andExpect(jsonPath("$[0].rsEventId", is(userEntity.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)));

        mockMvc.perform(get("/rsDatabase/vote/getRecord")
                .param("userId", String.valueOf(userEntity.getUserId()))
                .param("rsEventId", String.valueOf(rsEventEntity.getRsEventId()))
                .param("pageIndex","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].voteNum", is(1)));
    }

    @Test
    void should_get_vote_record_by_time_between_start_and_end() throws Exception {

        String startDateTime = "2020/01/01 00:00:00";
        String endDateTime = "2020/12/31 23:59:59";

        mockMvc.perform(get("/vote/recordByTime")
                .param("startTime", startDateTime)
                .param("endTime", endDateTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].userId", is(userEntity.getUserId())))
                .andExpect(jsonPath("$[0].rsEventId", is(userEntity.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)));
    }
}
