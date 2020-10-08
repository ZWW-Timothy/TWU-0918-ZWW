package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.UserDto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
public class UserController {

    public static List<UserDto> userList = new ArrayList<>();

    @PostMapping("/user/register")
    public static ResponseEntity<String> userRegister(@Valid @RequestBody UserDto user) {
        userList.add(user);
        HttpHeaders userHeader = new HttpHeaders();
        userHeader.set("index", String.valueOf(userHeader.size() - 1));
        return new ResponseEntity(userHeader, HttpStatus.CREATED);
    }

    @GetMapping("/user/list")
    public static ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.created(null).body(userList);
    }
}
