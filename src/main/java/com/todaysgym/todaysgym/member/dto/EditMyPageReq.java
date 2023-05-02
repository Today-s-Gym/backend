package com.todaysgym.todaysgym.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EditMyPageReq {
    private String newNickname;
    private String newIntroduce;

    public EditMyPageReq(String newNickname, String newIntroduce) {
        this.newNickname = newNickname;
        this.newIntroduce = newIntroduce;
    }
}
