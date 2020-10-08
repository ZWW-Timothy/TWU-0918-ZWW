package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteEntity {

    @Id
    @GeneratedValue
    private Integer voteId;

    private Integer voteNum;

    private LocalDateTime voteTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "rsEventId")
    private RsEventEntity rsEventEntity;
}
