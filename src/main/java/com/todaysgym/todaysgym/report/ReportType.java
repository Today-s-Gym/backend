package com.todaysgym.todaysgym.report;

public enum ReportType {
    MEMBER("member"),
    POST("post"),
    COMMENT("comment");

    private final String type;

    ReportType(String type) {
        this.type = type;
    }
}