package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.api.UserControllerDatabase;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

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
class UserControllerDatabaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    @BeforeEach
    void should_reset_for_test() {
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
    void should_register_user() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("person3")
                .userGender("male")
                .userAge(40)
                .userEmail("p3@m.com")
                .userPhoneNum("12512345678")
                .userVoteNum(10)
                .build();
        String userJson = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/userDatabase/register")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<UserEntity> userList = userRepository.findAll();

        assertEquals(3, userList.size());
        assertEquals("person3", userList.get(2).getUserName());

    }

    @Test
    void should_get_one_user() throws Exception {
        mockMvc.perform(get("/userDatabase/1"))
                .andExpect(status().isOk());

        List<UserEntity> userList = userRepository.findAll();

        assertEquals(2, userList.size());
        assertEquals("person1", userList.get(0).getUserName());
    }

    @Test
    void should_delete_user() throws Exception {
        mockMvc.perform(get("/userDatabase/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/userDatabase/1"))
                .andExpect(status().isOk());

        List<UserEntity> userList = userRepository.findAll();
        List<RsEventEntity> rsEventList = rsEventRepository.findAll();

        assertEquals(1, userList.size());
        assertEquals("person2", userList.get(0).getUserName());
        assertEquals(1, rsEventList.size());
        assertEquals("事件2", rsEventList.get(0).getRsEventName());
    }
}
