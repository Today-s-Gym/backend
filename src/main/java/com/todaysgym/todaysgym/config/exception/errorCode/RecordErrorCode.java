package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordErrorCode implements ErrorCode {
    EMPTY_RECORD("RECORD_001", "존재하지 않는 기록입니다."),
    ;
    private final String errorCode;
    private final String message;
}