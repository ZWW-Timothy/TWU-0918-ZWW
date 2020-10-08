package com.thoughtworks.rslist.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RsEventDto {

    @NotEmpty
    private String rsEventName;

    @NotEmpty
    private String rsEventKeyword;

    @NotNull
    private int userId;

    private Integer rsEventVoteNum;

    public String getRsEventName() {
        return rsEventName;
    }

    public String getRsEventKeyword() {
        return rsEventKeyword;
    }

    public void setRsEventName(String rsEventName) {
        this.rsEventName = rsEventName;
    }

    public void setRsEventKeyword(String rsEventKeyword) {
        this.rsEventKeyword = rsEventKeyword;
    }

    @JsonIgnore
    public int getUserId() {
        return userId;
    }
    @JsonProperty
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRsEventVoteNum() {
        return rsEventVoteNum;
    }
    public void setRsEventVoteNum(int rsEventVoteNum) {
        this.rsEventVoteNum = rsEventVoteNum;
    }
}
