package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.RsEventEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RsEventRepository extends CrudRepository<RsEventEntity, Integer> {
    List<RsEventEntity> findAll();
    Optional<RsEventEntity> findById(Integer userId);
}
