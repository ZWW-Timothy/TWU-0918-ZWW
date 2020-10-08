package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.dto.UserDto;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void user_list_clear_for_test() {
        UserController.userList.clear();
    }

    @Test
    @Transactional
    void user_register_success() throws Exception {
        UserDto user = new UserDto("person", "male", 20, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //UserController.userList.add(user);
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].user_userName", is("person")))
                .andExpect(jsonPath("$[0].user_userGender", is("male")))
                .andExpect(jsonPath("$[0].user_userAge", is(20)))
                .andExpect(jsonPath("$[0].user_userEmail", is("p@m.com")))
                .andExpect(jsonPath("$[0].user_userPhoneNum", is("12312345678")));
    }

    @Test
    void user_all() throws Exception {
        UserDto user = new UserDto("person", "male", 20, "p@m.com","12312345678");

        UserController.userRegister(user);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].user_userName", is("person")))
                .andExpect(jsonPath("$[0].user_userGender", is("male")))
                .andExpect(jsonPath("$[0].user_userAge", is(20)))
                .andExpect(jsonPath("$[0].user_userEmail", is("p@m.com")))
                .andExpect(jsonPath("$[0].user_userPhoneNum", is("12312345678")));
    }

    @Test
    void user_name_not_empty() throws Exception {
        UserDto user = new UserDto("", "male", 20, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_name_length_max_8_character() throws Exception {UserDto user = new UserDto("person12345", "male", 20, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());}

    @Test
    void user_gender_not_empty() throws Exception {
        UserDto user = new UserDto("person", "", 20, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_age_not_null() throws Exception {
        UserDto user = new UserDto("person", "male", null, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_age_18_min() throws Exception {
        UserDto user = new UserDto("person", "male", 17, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_age_100_max() throws Exception {
        UserDto user = new UserDto("person", "male", 101, "p@m.com","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_email_valid() throws Exception {UserDto user = new UserDto("person", "male", 20, "@m","12312345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());}

    @Test
    void user_phone_number_not_empty() throws Exception {
        UserDto user = new UserDto("person", "male", 20, "p@m.com","");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_phone_number_valid_1_start() throws Exception {
        UserDto user = new UserDto("person", "male", 20, "p@m.com","23412345678");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void user_phone_number_valid_total_11_number() throws Exception {
        UserDto user = new UserDto("person", "male", 20, "p@m.com","123123456789");

        ObjectMapper objectMapper = new ObjectMapper();
        final String oneUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/register")
                .content(oneUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
