package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerDatabaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    @BeforeEach
    public void init() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        UserEntity userEntity1 = UserEntity.builder()
                .userName("person1")
                .userGender("male")
                .userAge(30)
                .userEmail("p1@m.com")
                .userPhoneNum("12312345678")
                .userVoteNum(10)
                .build();
        userRepository.save(userEntity1);
        UserEntity userEntity2 = UserEntity.builder()
                .userName("person2")
                .userGender("female")
                .userAge(20)
                .userEmail("p2@m.com")
                .userPhoneNum("12412345678")
                .userVoteNum(10)
                .build();
        userRepository.save(userEntity2);
        RsEventEntity rsEventEntity1 = RsEventEntity.builder()
                .rsEventName("事件1")
                .rsEventKeyword("无")
                .userId(1)
                .build();
        rsEventRepository.save(rsEventEntity1);
        RsEventEntity rsEventEntity2 = RsEventEntity.builder()
                .rsEventName("事件2")
                .rsEventKeyword("无")
                .userId(2)
                .build();
        rsEventRepository.save(rsEventEntity2);
    }

    @Test
    void should_add_one_rs_event_when_user_exist() throws Exception {

//        UserEntity userEntity = UserEntity.builder()
//                .userName("person1")
//                .userGender("male")
//                .userAge(30)
//                .userEmail("p1@m.com")
//                .userPhoneNum("12312345678")
//                .userVoteNum(10)
//                .build();
//        userRepository.save(userEntity);

        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName("事件3")
                .rsEventKeyword("无")
                .userId(1)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(post("/rsEvent/create")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEventEntity> rsList = rsEventRepository.findAll();
        assertEquals(3, rsList.size());
        assertEquals("事件3", rsList.get(2).getRsEventName());
        assertEquals(1, rsList.get(2).getUserId());
    }

    @Test
    void should_add_one_rs_event_failed_when_user_not_exist() throws Exception {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName("事件3")
                .rsEventKeyword("无")
                .userId(3)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(post("/rsEvent/create")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        List<RsEventEntity> rsList = rsEventRepository.findAll();
        assertEquals(2, rsList.size());
    }

    @Test
    void should_edit_rs_event() throws Exception {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName("事件1-修改1")
                .rsEventKeyword("无-修改1")
                .userId(1)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(put("/rsEvent/1/edit")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> rsList = rsEventRepository.findAll();
        assertEquals(2, rsList.size());
        assertEquals("事件1-修改1", rsList.get(0).getRsEventName());
        assertEquals("无-修改1", rsList.get(0).getRsEventKeyword());
        assertEquals(1, rsList.get(0).getUserId());
    }

    @Test
    void should_edit_rs_event_name_when_get_name_only() throws Exception {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName("事件1-修改1")
                .userId(1)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(put("/rsEvent/1/edit")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> rsList = rsEventRepository.findAll();
        assertEquals(2, rsList.size());
        assertEquals("事件1-修改1", rsList.get(0).getRsEventName());
        assertEquals("无", rsList.get(0).getRsEventKeyword());
        assertEquals(1, rsList.get(0).getUserId());
    }

    @Test
    void should_edit_rs_event_keyword_when_get_keyword_only() throws Exception {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventKeyword("无-修改1")
                .userId(1)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(put("/rsEvent/1/edit")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> rsList = rsEventRepository.findAll();
        assertEquals(2, rsList.size());
        assertEquals("事件1", rsList.get(0).getRsEventName());
        assertEquals("无-修改1", rsList.get(0).getRsEventKeyword());
        assertEquals(1, rsList.get(0).getUserId());
    }

    @Test
    void should_edit_rs_event_failed_when_user_id_not_match() throws Exception {
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName("事件1-修改1")
                .rsEventKeyword("无-修改1")
                .userId(2)
                .build();
        String rsJson = objectMapper.writeValueAsString(rsEventEntity);

        mockMvc.perform(put("/rsEvent/1/edit")
                .content(rsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
