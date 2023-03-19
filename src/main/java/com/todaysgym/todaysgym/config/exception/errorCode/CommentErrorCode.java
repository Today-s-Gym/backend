package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    EMPTY_COMMENT("COMMENT_001", "존재하지 않는 댓글입니다."),
    ;
    private final String errorCode;
    private final String message;
}
