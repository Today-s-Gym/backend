package com.todaysgym.todaysgym.avatar;

public enum Avatar {
    BBURAKMA1(1, "https://todaysgym-bucket.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EB%B0%94%ED%83%80+1.png", 0, 3),
    BBURAKMA2(2, "https://todaysgym-bucket.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EB%B0%94%ED%83%80+2.png", 3, 10),
    BBURAKMA3(3, "https://todaysgym-bucket.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EB%B0%94%ED%83%80+3.png", 10, 20),
    BBURAKMA4(4, "https://todaysgym-bucket.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EB%B0%94%ED%83%80+4.png", 20, 35),
    BBURAKMA5(5, "https://todaysgym-bucket.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EB%B0%94%ED%83%80+5.png", 35, 60),
    ;
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