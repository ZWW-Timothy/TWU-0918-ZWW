package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer userId;

    @Column(name = "user")
    private String userName;

    @Column(name = "gender")
    private String userGender;

    @Column(name = "age")
    private int userAge;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "phone")
    private String userPhoneNum;

    @Column(name = "vote")
    private int userVoteNum;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.REMOVE)
    private List<RsEventEntity> rsEventEntity;
}
