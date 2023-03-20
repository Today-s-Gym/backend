package com.todaysgym.todaysgym.config.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    EMPTY_CATEGORY("CATEGORY_001", "존재하지 않는 카테고리입니다."),
    ;
    private final String errorCode;
    private final String message;
}
