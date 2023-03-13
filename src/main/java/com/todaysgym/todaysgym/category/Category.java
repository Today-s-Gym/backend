package com.todaysgym.todaysgym.category;

public enum Category {
    NONE(0L, "none");

    private final Long id;
    private final String name;

    Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}