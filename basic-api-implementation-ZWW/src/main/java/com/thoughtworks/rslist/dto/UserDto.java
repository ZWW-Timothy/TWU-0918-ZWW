package com.thoughtworks.rslist.dto;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(max = 8)
    @JsonProperty("user_userName")
    private String userName;

    @NotEmpty
    @JsonProperty("user_userGender")
    private String userGender;

    @NotNull
    @Min(18)
    @Max(100)
    @JsonProperty("user_userAge")
    private Integer userAge;

    @Email
    @JsonProperty("user_userEmail")
    private String userEmail;

    @NotEmpty
    @Pattern(regexp = "1[0-9]{10}")
    @JsonProperty("user_userPhoneNum")
    private String userPhoneNum;

    private int userVoteNum = 10;

    public UserDto(String userName, String userGender, Integer userAge, String userEmail, String userPhoneNum) {
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userPhoneNum = userPhoneNum;
    }
}
