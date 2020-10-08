package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {
    List<VoteEntity> findAll();
    List<VoteEntity> findAllByUserIdAndRsEventId(int userEntityId, int rsEventEntityId, Pageable pageable);
    List<VoteEntity> findAllByVoteTimeBetween(LocalDateTime start, LocalDateTime end);
}
