package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
public class VoteControllerDatabase {
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;
    private final VoteRepository voteRepository;

    public VoteControllerDatabase(UserRepository userRepository, RsEventRepository rsEventRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
        this.voteRepository = voteRepository;
    }

    @PostMapping("/vote/{rsEventId}")
    public ResponseEntity vote(@PathVariable int rsEventId, @RequestBody VoteDto voteDto) {

        if (userRepository.findById(voteDto.getUserId()).get().getUserVoteNum() >= voteDto.getVoteNum()) {

            VoteEntity voteEntity = VoteEntity.builder()
                    .rsEventEntity(rsEventRepository.findById(rsEventId).get())
                    .userEntity(userRepository.findById(voteDto.getUserId()).get())
                    .voteNum(voteDto.getVoteNum())
                    .voteTime(voteDto.getVoteTime())
                    .build();

            RsEventEntity rsEventEntity = rsEventRepository.findById(rsEventId).get();
            rsEventEntity.setRsEventVoteNum(voteDto.getVoteNum() + rsEventEntity.getRsEventVoteNum());
            rsEventRepository.save(rsEventEntity);
            voteRepository.save(voteEntity);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vote/recordById")
    public ResponseEntity<List<VoteDto>> getVoteRecord(@RequestParam int userId,
                                        @RequestParam int rsEventId,
                                        @RequestParam(defaultValue = "1") int pageIndex,
                                        @RequestParam(defaultValue = "5") int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        List<VoteEntity> voteRecord = voteRepository.findAllByUserIdAndRsEventId(userId, rsEventId, pageable);

        return ResponseEntity.ok(voteRecord.stream()
                .map(voteDto->VoteDto.builder()
                .rsEventId(voteDto.getRsEventEntity().getRsEventId())
                .userId(voteDto.getUserEntity().getUserId())
                .voteNum(voteDto.getVoteNum())
                .voteTime(voteDto.getVoteTime())
                .build())
                .collect(Collectors.toList()));
    }

    @GetMapping("/vote/recordByTime")
    public ResponseEntity<List<VoteDto>> getVoteRecordBetween(@RequestParam String startDateTime, @RequestParam(defaultValue = "5") String endDateTime) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDateTime startFormatter = LocalDateTime.parse(startDateTime, dateTimeFormatter);
        LocalDateTime endFormatter = LocalDateTime.parse(endDateTime, dateTimeFormatter);

        List<VoteEntity> voteRecordBetween = voteRepository.findAllByVoteTimeBetween(startFormatter, endFormatter);

        return ResponseEntity.ok(voteRecordBetween.stream()
                .map(voteDto->VoteDto.builder()
                        .rsEventId(voteDto.getRsEventEntity().getRsEventId())
                        .userId(voteDto.getUserEntity().getUserId())
                        .voteNum(voteDto.getVoteNum())
                        .voteTime(voteDto.getVoteTime())
                        .build())
                .collect(Collectors.toList()));
    }
}
