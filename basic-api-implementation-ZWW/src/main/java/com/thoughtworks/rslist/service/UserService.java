package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

// @Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity userRegister(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .userGender(userDto.getUserGender())
                .userAge(userDto.getUserAge())
                .userEmail(userDto.getUserEmail())
                .userPhoneNum(userDto.getUserPhoneNum())
                .userVoteNum(userDto.getUserVoteNum())
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity userGet(int id) {
        userRepository.findById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity userDelete(int id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
