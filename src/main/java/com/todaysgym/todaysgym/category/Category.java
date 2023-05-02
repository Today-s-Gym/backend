package com.todaysgym.todaysgym.category;

public enum Category {
    GYM(1L, "헬스"),
    CROSSFIT(2L, "크로스핏"),
    CLIMBING(3L, "클라이밍"),
    KARATE(4L, "가라데"),
    TAEKWONDO(5L, "태권도"),
    MUAYTHAI(6L, "무에타이"),
    JIUJITSU(7L, "주짓수"),
    JEETKUNEDO(8L, "절권도"),
    WUSHU(9L, "우슈"),
    KUDO(10L, "쿠도"),
    PROWRESTLING(11L, "프로레슬링"),
    KICKBOXING(12L, "킥복싱"),
    KENDO(13L, "검도"),
    ;

    private final Long id;
    private final String name;

    Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}