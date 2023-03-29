package com.todaysgym.todaysgym.config.exception.errorCode;

import com.todaysgym.todaysgym.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements ErrorCode {
    REPORT_MEMBER_DUPLICATE("REPORT_001", "이미 신고한 사용자입니다."),
    REPORT_POST_DUPLICATE("REPORT_002", "이미 신고한 게시글입니다."),
    REPORT_COMMENT_DUPLICATE("REPORT_003", "이미 신고한 댓글입니다."),
    REPORT_MEMBER_SELF("REPORT_004", "자신을 신고할 수 없습니다."),
    REPORT_POST_SELF("REPORT_005", "자신의 게시글을 신고할 수 없습니다."),
    REPORT_COMMENT_SELF("REPORT_006", "자신의 댓글을 신고할 수 없습니다."),
    ;

    private final String errorCode;
    private final String message;
}