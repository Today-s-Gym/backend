package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    EMPTY_MEMBER("MEMBER_001", "존재하지 않는 사용자입니다."),
    ;
    private final String errorCode;
    private final String message;
}