package com.todaysgym.todaysgym.user;

import lombok.Getter;

@Getter
public enum Category {
    Health(1),
    Climbing(2),
    Kudo(3),
    Taekwondo(4),
    Jujitsu(5),
    Muetai(6),
    Karate(7),
    KickBoxing(8),
    Gumdo(9),
    JeetKuneDo(10),
    Wusu(11),
    Wrestling(12),
    Crosspit(13);


    private final int code;

    private Category(int code) {
        this.code = code;
    }









}
