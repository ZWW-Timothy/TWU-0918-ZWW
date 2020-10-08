/*
package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEventDto;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.thoughtworks.rslist.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
class RsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void rs_event_list_reset_for_test() {
        RsController.rsEventList.clear();
        RsController.rsEventList = RsController.initRsEventList();
    }

    @Test
    void should_get_all_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))));
    }

    @Test
    void should_get_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件1")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")))
                .andExpect(jsonPath("$", not(hasKey("user"))));

        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件2")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")))
                .andExpect(jsonPath("$", not(hasKey("user"))));


        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件3")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")))
                .andExpect(jsonPath("$", not(hasKey("user"))));
    }

    @Test
    void should_not_get_one_rs_event_if_out_of_range() throws Exception {
        mockMvc.perform(get("/rs/5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    void should_get_rs_event_in_given_range() throws Exception {
        mockMvc.perform(get("/rs/range?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))));
    }

    @Test
    void should_not_get_rs_event_in_given_range_if_out_of_range() throws Exception {
        mockMvc.perform(get("/rs/range?start=1&end=5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    void should_add_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto addOneRsEvent = new RsEventDto("事件4", "无分类", new UserDto("person4", "female", 80, "p4@m.com","12612345678"));
        ObjectMapper objectMapper = new ObjectMapper();
        final String addOneRsEventJson = objectMapper.writeValueAsString(addOneRsEvent);

        mockMvc.perform(post("/rs/add")
                .content(addOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[3].rsEventName", is("事件4")))
                .andExpect(jsonPath("$[3].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_keyword_stay() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto editOneRsEvent = new RsEventDto("事件1-修改1", "", new UserDto("person1", "male", 20, "p1@m.com","12312345678"));
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(editOneRsEvent);

        mockMvc.perform(put("/rs/edit/1")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1-修改1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_name_stay() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto editOneRsEvent = new RsEventDto("", "无分类-修改1", new UserDto("person2", "female", 40, "p2@m.com","12412345678"));
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(editOneRsEvent);

        mockMvc.perform(put("/rs/edit/2")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类-修改1")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_both_change() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto editOneRsEvent = new RsEventDto("事件3-修改1", "无分类-修改1", new UserDto("person3", "male", 60, "p3@m.com","12512345678"));
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(editOneRsEvent);

        mockMvc.perform(put("/rs/edit/3")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3-修改1")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类-修改1")));
    }

    @Test
    void should_delete_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        mockMvc.perform(delete("/rs/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")));
    }
}
*/
