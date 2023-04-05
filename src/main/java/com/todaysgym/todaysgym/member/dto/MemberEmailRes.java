package com.todaysgym.todaysgym.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberEmailRes {
    private String email;

    public MemberEmailRes(String email) {
        this.email = email;
    }
}
