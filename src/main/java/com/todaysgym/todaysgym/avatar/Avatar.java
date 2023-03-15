package com.todaysgym.todaysgym.avatar;

public enum Avatar {
    NONE(0, "none", -1, -1);
    private final int level;
    private final String imgUrl;
    private final int minRecordCount;
    private final int maxRecordCount;

    Avatar(int level, String imgUrl, int minRecordCount, int maxRecordCount) {
        this.level = level;
        this.imgUrl = imgUrl;
        this.minRecordCount = minRecordCount;
        this.maxRecordCount = maxRecordCount;
    }
}