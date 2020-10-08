package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

// @Service
public class RsEventService {

    private UserRepository userRepository;
    private RsEventRepository rsEventRepository;

    public RsEventService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    public ResponseEntity addOneRsEvent(RsEventDto rsEventDto) {
        if (!userRepository.existsById(rsEventDto.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .rsEventName(rsEventDto.getRsEventName())
                .rsEventKeyword(rsEventDto.getRsEventKeyword())
                .userId(rsEventDto.getUserId())
                .build();
        rsEventRepository.save(rsEventEntity);
        return ResponseEntity.created(null).build();
    }

    public ResponseEntity editOneRsEvent(Integer rsEventId, RsEventDto rsEventDto) {

        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);

        if (rsEventDto.getUserId() != rsEventEntity.get().getUserId()) {
            return ResponseEntity.badRequest().build();
        } else {
            if (rsEventDto.getRsEventName() != null) {
                rsEventDto.setRsEventName(rsEventDto.getRsEventName());
            }
            if (rsEventDto.getRsEventKeyword() != null) {
                rsEventDto.setRsEventKeyword(rsEventDto.getRsEventKeyword());
            }
            rsEventEntity.get().setUserId(rsEventDto.getUserId());
            rsEventRepository.save(rsEventEntity.get());
        }

        return ResponseEntity.ok().build();
    }
}
