package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

import com.thoughtworks.rslist.service.RsEventService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RsControllerDatabase {

    private final RsEventService rsEventService;

    public RsControllerDatabase(RsEventService rsEventService) {
        this.rsEventService = rsEventService;
    }

    @PostMapping("/rsEvent/create")
    public ResponseEntity addOneRsEvent(@RequestBody @Valid RsEventDto rsEventDto) {
        return rsEventService.addOneRsEvent(rsEventDto);
    }

    @PutMapping("/rsEvent/{rsEventId}/edit")
    public ResponseEntity editOneRsEvent(@PathVariable Integer rsEventId, @RequestBody @Valid RsEventDto rsEventDto) {

        return rsEventService.editOneRsEvent(rsEventId, rsEventDto);
    }
}


//@RestController
//public class RsControllerDatabase {
//
//    private final UserRepository userRepository;
//    private final RsEventRepository rsEventRepository;
//
//    public RsControllerDatabase(UserRepository userRepository, RsEventRepository rsEventRepository) {
//        this.userRepository = userRepository;
//        this.rsEventRepository = rsEventRepository;
//    }
//
//    @PostMapping("/rsDatabase/add")
//    public ResponseEntity addOneRsEvent(@RequestBody @Valid RsEventDto rsEventDto) {
//        if (!userRepository.existsById(rsEventDto.getUserId())) {
//            return  ResponseEntity.badRequest().build();
//        }
//        RsEventEntity rsEventEntity = RsEventEntity.builder()
//                .rsEventName(rsEventDto.getRsEventName())
//                .rsEventKeyword(rsEventDto.getRsEventKeyword())
//                .userId(rsEventDto.getUserId())
//                .build();
//        rsEventRepository.save(rsEventEntity);
//        return ResponseEntity.created(null).build();
//    }
//
//    @PutMapping("/rsDatabase/edit/{rsEventId}")
//    public ResponseEntity editOneRsEvent(@PathVariable Integer rsEventId,  @RequestBody @Valid RsEventDto rsEventDto) {
//
//        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
//
//        if (rsEventDto.getUserId() != rsEventEntity.get().getUserId()) {
//            return  ResponseEntity.badRequest().build();
//        } else {
//            if (rsEventDto.getRsEventName() != null) {
//                rsEventDto.setRsEventName(rsEventDto.getRsEventName());
//            }
//            if (rsEventDto.getRsEventKeyword() != null) {
//                rsEventDto.setRsEventKeyword(rsEventDto.getRsEventKeyword());
//            }
//            rsEventEntity.get().setUserId(rsEventDto.getUserId());
//            rsEventRepository.save(rsEventEntity.get());
//        }
//
//        return ResponseEntity.ok().build();
//    }
//}
