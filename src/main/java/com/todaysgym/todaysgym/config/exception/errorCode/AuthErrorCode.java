package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMPTY_JWT("AUTH_001", "JWT를 입력해주세요."),
    INVALID_JWT("AUTH_002", "유효하지 않은 JWT입니다."),
    EXPIRED_MEMBER_JWT("AUTH_003", "만료된 JWT입니다."),
    ;
    private final String errorCode;
    private final String message;
}