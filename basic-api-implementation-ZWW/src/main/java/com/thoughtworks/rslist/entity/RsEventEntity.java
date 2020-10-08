package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rs_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsEventEntity {

    @Id
    @GeneratedValue
    private Integer rsEventId;

    @Column(name = "event")
    private String rsEventName;

    @Column(name = "keyword")
    private String rsEventKeyword;

    private int userId;

    private Integer rsEventVoteNum;
}
