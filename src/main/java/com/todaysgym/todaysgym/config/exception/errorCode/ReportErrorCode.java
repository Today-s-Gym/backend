package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements ErrorCode {
    REPORT_USER_DUPLICATE("REPORT_001", "이미 신고한 유저입니다.");

    private final String errorCode;
    private final String message;
}