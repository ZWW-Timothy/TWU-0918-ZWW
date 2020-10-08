/*
package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.thoughtworks.rslist.exception.IndexOutOfBoundsExceptionWhenGetOne;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {

    public static List<RsEventDto> rsEventList = initRsEventList();

    public static List<RsEventDto> initRsEventList() {
        List<RsEventDto> rsList;
        rsList = new ArrayList<>();
        rsList.add(new RsEventDto("事件1", "无分类", new UserDto("person1", "male", 20, "p1@m.com", "12312345678")));
        rsList.add(new RsEventDto("事件2", "无分类", new UserDto("person2", "female", 40, "p2@m.com", "12412345678")));
        rsList.add(new RsEventDto("事件3", "无分类", new UserDto("person3", "male", 60, "p3@m.com", "12512345678")));
        ;
        return rsList;
    }

    @GetMapping("/rs/list")
    public static ResponseEntity<List<RsEventDto>> getAllRsEvent() {
        return ResponseEntity.ok(rsEventList);
    }

    @GetMapping(("/rs/{index}"))
    public ResponseEntity<RsEventDto> getOneRsEvent(@PathVariable int index) {
        if (index < 1 || index > rsEventList.size()) {
            throw new IndexOutOfBoundsExceptionWhenGetOne();
        }
        return ResponseEntity.ok(rsEventList.get(index - 1));
    }

    @GetMapping(("/rs/range"))
    public ResponseEntity<List<RsEventDto>> getRsEventInGivenRange(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return ResponseEntity.ok(rsEventList);
        }
        return ResponseEntity.ok(rsEventList.subList(start - 1, end));
    }

    @PostMapping(("/rs/add"))
    public ResponseEntity addOneRsEvent(@RequestBody RsEventDto rsEvent) throws JsonProcessingException {
        rsEventList.add(rsEvent);
        HttpHeaders rsEventHeader = new HttpHeaders();
        rsEventHeader.set("index", String.valueOf(rsEventHeader.size() - 1));
        return new ResponseEntity(rsEventHeader, HttpStatus.CREATED);
    }

    @PutMapping(("/rs/edit/{index}"))
    public void editOneRsEvent(@PathVariable int index, @RequestBody RsEventDto rsEvent) {
        if (rsEvent.getRsEventName() != "") {
            rsEventList.get(index - 1).setRsEventName(rsEvent.getRsEventName());
        }
        if (rsEvent.getRsEventKeyword() != "") {
            rsEventList.get(index - 1).setRsEventKeyword(rsEvent.getRsEventKeyword());
        }
    }

    @DeleteMapping(("/rs/delete/{index}"))
    public void deleteOneRsEvent(@PathVariable int index) {
        rsEventList.remove(index - 1);
    }
}
*/
