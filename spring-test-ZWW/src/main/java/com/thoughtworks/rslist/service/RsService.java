package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.TradeDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.TradeRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RsService {
  final RsEventRepository rsEventRepository;
  final UserRepository userRepository;
  final VoteRepository voteRepository;
  final TradeRepository tradeRepository;
  final TradeRepository tradeRepositoryRecord;

  public RsService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository, TradeRepository tradeRepository, TradeRepository tradeRepositoryRecord) {
    this.rsEventRepository = rsEventRepository;
    this.userRepository = userRepository;
    this.voteRepository = voteRepository;
    this.tradeRepository = tradeRepository;
    this.tradeRepositoryRecord = tradeRepositoryRecord;
  }

  public void vote(Vote vote, int rsEventId) {
    Optional<RsEventDto> rsEventDto = rsEventRepository.findById(rsEventId);
    Optional<UserDto> userDto = userRepository.findById(vote.getUserId());
    if (!rsEventDto.isPresent()
        || !userDto.isPresent()
        || vote.getVoteNum() > userDto.get().getVoteNum()) {
      throw new RuntimeException();
    }
    VoteDto voteDto =
        VoteDto.builder()
            .localDateTime(vote.getTime())
            .num(vote.getVoteNum())
            .rsEvent(rsEventDto.get())
            .user(userDto.get())
            .build();
    voteRepository.save(voteDto);
    UserDto user = userDto.get();
    user.setVoteNum(user.getVoteNum() - vote.getVoteNum());
    userRepository.save(user);
    RsEventDto rsEvent = rsEventDto.get();
    rsEvent.setVoteNum(rsEvent.getVoteNum() + vote.getVoteNum());
    rsEventRepository.save(rsEvent);
  }

  public ResponseEntity buy(Trade trade, int id) {
    Optional<TradeDto> tradeDto = tradeRepository.findByRank(trade.getRank());
    Optional<RsEventDto> rsEventDto = rsEventRepository.findById(id);

    if (tradeDto.isPresent() && tradeDto.get().getAmount() > trade.getAmount()) {
      return ResponseEntity.badRequest().build();
    } else {
      TradeDto tradeDtoRecord = TradeDto.builder()
              .amount(trade.getAmount())
              .rank(trade.getRank())
              .rsEventId(id)
              .build();
      if (tradeDto.isPresent()) {
        rsEventRepository.deleteByRank(tradeDto.get().getRank());
        tradeRepository.deleteByRank(tradeDto.get().getRank());
      }
      rsEventDto.get().setRank(trade.getRank());
      rsEventRepository.save(rsEventDto.get());
      tradeRepository.save(tradeDtoRecord);
      tradeRepositoryRecord.save(tradeDtoRecord);
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity<List<RsEvent>> getRsEventListInOrder(Integer start, Integer end) {

    List<RsEventDto> rsEventDtoList = rsEventRepository.findAll();

    Collections.sort(rsEventDtoList, new Comparator<RsEventDto>() {
      public int compare(RsEventDto rs1, RsEventDto rs2) {
        return rs2.getVoteNum() - rs1.getVoteNum();
      }
    });

    rsEventDtoList.forEach(rs -> {
      if (rs.getRank() != Integer.MAX_VALUE && rs.getRank() != rsEventDtoList.indexOf(rs)) {
        RsEventDto temp = rs;
        rsEventDtoList.remove(rs);
        rsEventDtoList.add(temp.getRank(), temp);
      }
    });

    List<RsEvent> rsEvents =
            rsEventRepository.findAll().stream()
                    .map(
                            item ->
                                    RsEvent.builder()
                                            .eventName(item.getEventName())
                                            .keyword(item.getKeyword())
                                            .userId(item.getId())
                                            .voteNum(item.getVoteNum())
                                            .rank(item.getRank())
                                            .build())
                    .collect(Collectors.toList());

    if (start == null || end == null) {
      return ResponseEntity.ok(rsEvents);
    }
    return ResponseEntity.ok(rsEvents.subList(start - 1, end));
  }
}
